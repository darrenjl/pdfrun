/*------------------------------------------------------------------------------
 **    Author: Darren Lyons
 *     This file is a simplified version of the Statistics.java 
 *     file originally in this project. I have removed a lot of the code
 *     and also added some customisations.
 */
package com.patdivillyfitness.runcoach.activity;

import nl.sogeti.android.gpstracker.actions.utils.StatisticsCalulator;
import nl.sogeti.android.gpstracker.actions.utils.StatisticsDelegate;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.UnitsI18n;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.patdivillyfitness.runcoach.Constants;
import com.patdivillyfitness.runcoach.PDFUtil;
import com.patdivillyfitness.runcoach.R;

public class RunDetailsActivity extends SherlockActivity implements StatisticsDelegate
{
   private static final String TRACKURI = "TRACKURI";
   private static final String TAG = "pdfrun";

   private Uri mTrackUri = null;
   private boolean calculating;
   private TextView avgSpeedView;
   private TextView distanceView;
   private TextView maxSpeedView;
   private TextView mAscensionView;
   private TextView mElapsedTimeView;
   private TextView km1TimeView;
   private TextView km3TimeView;
   private TextView km5TimeView;
   private TextView km8TimeView;
   private TextView km10TimeView;
   private TextView dateView;
   private TextView timeView;
   private ImageView km1ImageView;
   private ImageView km3ImageView;
   private ImageView km5ImageView;
   private ImageView km8ImageView;
   private ImageView km10ImageView;
   private TableRow km1TimeTableRow;
   private TableRow km3TimeTableRow;
   private TableRow km5TimeTableRow;
   private TableRow km8TimeTableRow;
   private TableRow km10TimeTableRow;
   private LinearLayout fromRecordingLayout;
   private Resources res;
   private UnitsI18n mUnits;

   private final ContentObserver mTrackObserver = new ContentObserver(new Handler())
      {

         @Override
         public void onChange(boolean selfUpdate)
         {
            if (!calculating)
            {
               RunDetailsActivity.this.drawTrackingStatistics();
            }
         }
      };

   /**
    * Called when the activity is first created.
    */
   @Override
   protected void onCreate(Bundle load)
   {
      super.onCreate(load);
      mUnits = new UnitsI18n(this, new UnitsI18n.UnitsChangeListener()
         {
            @Override
            public void onUnitsChange()
            {
               drawTrackingStatistics();
            }
         });
      setContentView(R.layout.activity_run_details);

      maxSpeedView = (TextView) findViewById(R.id.stat_maximumspeed);
      mAscensionView = (TextView) findViewById(R.id.stat_ascension);
      mElapsedTimeView = (TextView) findViewById(R.id.stat_elapsedtime);
      avgSpeedView = (TextView) findViewById(R.id.stat_averagespeed);
      distanceView = (TextView) findViewById(R.id.stat_distance);
      km1TimeView = (TextView) findViewById(R.id.km_1_time);
      km3TimeView = (TextView) findViewById(R.id.km_3_time);
      km5TimeView = (TextView) findViewById(R.id.km_5_time);
      km8TimeView = (TextView) findViewById(R.id.km_8_time);
      km10TimeView = (TextView) findViewById(R.id.km_10_time);
      dateView = (TextView) findViewById(R.id.stat_date);
      timeView = (TextView) findViewById(R.id.stat_time);
      km1ImageView = (ImageView) findViewById(R.id.image_1km);
      km3ImageView = (ImageView) findViewById(R.id.image_3km);
      km5ImageView = (ImageView) findViewById(R.id.image_5km);
      km8ImageView = (ImageView) findViewById(R.id.image_8km);
      km10ImageView = (ImageView) findViewById(R.id.image_10km);
      km1TimeTableRow = (TableRow) findViewById(R.id.km_1_table_row);
      km3TimeTableRow = (TableRow) findViewById(R.id.km_3_table_row);
      km5TimeTableRow = (TableRow) findViewById(R.id.km_5_table_row);
      km8TimeTableRow = (TableRow) findViewById(R.id.km_8_table_row);
      km10TimeTableRow = (TableRow) findViewById(R.id.km_10_table_row);
      fromRecordingLayout = (LinearLayout) findViewById(R.id.fromRecording);
      if (load != null && load.containsKey(TRACKURI))
      {
         mTrackUri = Uri.withAppendedPath(Tracks.CONTENT_URI, load.getString(TRACKURI));
      }
      else
      {
         mTrackUri = this.getIntent().getData();
      }
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      res = getResources();
   }

   @Override
   protected void onRestoreInstanceState(Bundle load)
   {
      if (load != null)
      {
         super.onRestoreInstanceState(load);
      }
      if (load != null && load.containsKey(TRACKURI))
      {
         mTrackUri = Uri.withAppendedPath(Tracks.CONTENT_URI, load.getString(TRACKURI));
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle save)
   {
      super.onSaveInstanceState(save);
      save.putString(TRACKURI, mTrackUri.getLastPathSegment());
   }

   /*
    * (non-Javadoc)
    * @see android.app.Activity#onPause()
    */
   @Override
   protected void onPause()
   {
      super.onPause();
      ContentResolver resolver = this.getContentResolver();
      resolver.unregisterContentObserver(this.mTrackObserver);
   }

   /*
    * (non-Javadoc)
    * @see android.app.Activity#onResume()
    */
   @Override
   protected void onResume()
   {
      super.onResume();
      drawTrackingStatistics();
      if (this.getIntent().getBooleanExtra(Constants.FROM_RECORDING_EXTRA, false))
         fromRecordingLayout.setVisibility(View.VISIBLE);
      ContentResolver resolver = this.getContentResolver();
      resolver.registerContentObserver(mTrackUri, true, this.mTrackObserver);
   }

   public void goToDashboard(View view)
   {
      Intent i = new Intent(this, DashboardActivity.class);
      startActivity(i);
   }

   public void goToProfile(View view)
   {
      Intent i = new Intent(this, ProfileActivity.class);
      startActivity(i);
   }

   private void drawTrackingStatistics()
   {
      calculating = true;
      StatisticsCalulator calculator = new StatisticsCalulator(this, mUnits, this);
      calculator.execute(mTrackUri);
   }

   @Override
   public void finishedCalculations(StatisticsCalulator calculated)
   {

      maxSpeedView.setText(calculated.getMaxSpeedText());
      mElapsedTimeView.setText(calculated.getDurationText());
      mAscensionView.setText(calculated.getAscensionText());
      avgSpeedView.setText(calculated.getAvgSpeedText());
      distanceView.setText(calculated.getDistanceText());
      String titleFormat = getString(R.string.stat_title);
      setTitle(String.format(titleFormat, calculated.getTracknameText()));
      if (calculated.getKm1Time() > 0)
      {
         km1TimeView.setText(calculated.getKm1TimeText());
         km1TimeTableRow.setVisibility(View.VISIBLE);
         km1ImageView.setImageResource(PDFUtil.getLevelDrawable(calculated.getKm1Time(), res.getIntArray(R.array.goals_1km)));
      }
      if (calculated.getKm3Time() > 0)
      {
         km3TimeView.setText(calculated.getKm3TimeText());
         km3TimeTableRow.setVisibility(View.VISIBLE);
         km3ImageView.setImageResource(PDFUtil.getLevelDrawable(calculated.getKm3Time(), res.getIntArray(R.array.goals_3km)));
      }
      if (calculated.getKm5Time() > 0)
      {
         km5TimeView.setText(calculated.getKm5TimeText());
         km5TimeTableRow.setVisibility(View.VISIBLE);
         km5ImageView.setImageResource(PDFUtil.getLevelDrawable(calculated.getKm5Time(), res.getIntArray(R.array.goals_5km)));
      }
      if (calculated.getKm8Time() > 0)
      {
         km8TimeView.setText(calculated.getKm8TimeText());
         km8TimeTableRow.setVisibility(View.VISIBLE);
         km8ImageView.setImageResource(PDFUtil.getLevelDrawable(calculated.getKm8Time(), res.getIntArray(R.array.goals_8km)));
      }
      if (calculated.getKm10Time() > 0)
      {
         km10TimeView.setText(calculated.getKm10TimeText());
         km10TimeTableRow.setVisibility(View.VISIBLE);
         km10ImageView.setImageResource(PDFUtil.getLevelDrawable(calculated.getKm10Time(), res.getIntArray(R.array.goals_10km)));
      }
      calculating = false;
      String date = android.text.format.DateFormat.format("E, MMMM dd, yyyy", calculated.getCreationTime()).toString();
      dateView.setText(date);
      String time = android.text.format.DateFormat.format("h:mmaa", calculated.getCreationTime()).toString();
      timeView.setText(time);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      MenuInflater inflater = getSupportMenuInflater();
      inflater.inflate(R.menu.run_details, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      switch (item.getItemId())
      {
         case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
         case R.id.action_delete:
            Log.d(TAG, "delete");
            showConfirmDeletedDialog();
            return true;
      }
      return super.onOptionsItemSelected(item);
   }

   private void showConfirmDeletedDialog()
   {

      AlertDialog alertDialog = new AlertDialog.Builder(this).create();

      alertDialog.setTitle(R.string.confirm_delete);

      alertDialog.setMessage(getString(R.string.confirm_delete_text));

      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, RunDetailsActivity.this.getString(R.string.yes), new DialogInterface.OnClickListener()
         {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
               getContentResolver().delete(mTrackUri, null, null);
               NavUtils.navigateUpFromSameTask(RunDetailsActivity.this);
            }
         });

      alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, RunDetailsActivity.this.getString(R.string.no), new DialogInterface.OnClickListener()
         {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

               dialog.dismiss();

            }
         });

      alertDialog.show();
   }
}
