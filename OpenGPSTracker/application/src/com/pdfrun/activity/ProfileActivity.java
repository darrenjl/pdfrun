package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.actions.utils.StatisticsCalulator;
import nl.sogeti.android.gpstracker.actions.utils.StatisticsDelegate;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.UnitsI18n;

import com.pdfrun.R;
import com.pdfrun.R.layout;
import com.pdfrun.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ProfileActivity extends Activity implements StatisticsDelegate
{
   private Cursor tracksCursor;
   private TextView numRunsView;
   private UnitsI18n mUnits;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile);
      numRunsView = (TextView)findViewById(R.id.numRuns);
      mUnits = new UnitsI18n( this, new UnitsI18n.UnitsChangeListener()
      {
         @Override
         public void onUnitsChange()
         {
            Log.d("pdfrun", "units changed");
         }
      } );
   }
   
   @Override
   protected void onResume()
   {
      super.onResume();
      updateUI();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.profile, menu);
      return true;
   }
   
   private void updateUI()
   {
      
      numRunsView.setText(Integer.toString(getNumRuns()));
      getTotalDistance();
   }
   
   private int getNumRuns(){
      tracksCursor = managedQuery(Tracks.CONTENT_URI, new String[] { Tracks._ID, Tracks.NAME, Tracks.CREATION_TIME }, null, null, Tracks.CREATION_TIME + " DESC");
      int numRows = tracksCursor.getCount();
      tracksCursor.close();
      return numRows;
   }
   
   private float getTotalDistance(){
      tracksCursor = managedQuery(Tracks.CONTENT_URI, new String[] { Tracks._ID, Tracks.NAME, Tracks.CREATION_TIME }, null, null, Tracks.CREATION_TIME + " DESC");
      tracksCursor.moveToFirst();
      while (tracksCursor.isAfterLast() == false) 
      {
         int id = tracksCursor.getInt(0);
          Log.d("pdfrun", "track id: "+ id);
          tracksCursor.moveToNext();
          StatisticsCalulator calculator = new StatisticsCalulator( this, mUnits, this );
          Uri trackUri = ContentUris.withAppendedId(Tracks.CONTENT_URI, id);
          calculator.execute(trackUri);
      }
//      Uri trackUri = ContentUris.withAppendedId(Tracks.CONTENT_URI, mTrackId);
//      StatisticsCalulator calculator = new StatisticsCalulator( this, mUnits, this );
//      calculator.execute(mTrackUri);
      return -1;
   }

   @Override
   public void finishedCalculations(StatisticsCalulator calculated)
   {
      
      Log.d("runpdf", "finishedCalculations: distance " + calculated.getDistanceText());
   }
}
