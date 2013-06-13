package com.patdivillyfitness.runcoach.activity;

import nl.sogeti.android.gpstracker.actions.utils.StatisticsCalulator;
import nl.sogeti.android.gpstracker.actions.utils.StatisticsDelegate;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.UnitsI18n;

import com.actionbarsherlock.app.SherlockActivity;
import com.patdivillyfitness.runcoach.R;
import com.patdivillyfitness.runcoach.R.layout;
import com.patdivillyfitness.runcoach.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class ProfileActivity extends SherlockActivity implements StatisticsDelegate
{
   private Cursor tracksCursor;
   private TextView numRunsView;
   private TextView totalDistanceView;
   private TextView maxSpeedView;
   private UnitsI18n mUnits;
   private float totalDistance=0;
   private double maxSpeed=0;
   private long best1kmTime=0;
   private long best3kmTime=0;
   private long best5kmTime=0;
   private long best8kmTime=0;
   private long best10kmTime=0;
   private TextView km1TimeView;
   private TextView km3TimeView;
   private TextView km5TimeView;
   private TextView km8TimeView;
   private TextView km10TimeView;
   private TableRow km1TimeTableRow;
   private TableRow km3TimeTableRow;
   private TableRow km5TimeTableRow;
   private TableRow km8TimeTableRow;
   private TableRow km10TimeTableRow;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile);
      numRunsView = (TextView)findViewById(R.id.numRuns);
      totalDistanceView = (TextView)findViewById(R.id.totalDistance);
      maxSpeedView = (TextView)findViewById(R.id.maxSpeed);
      km1TimeView = (TextView) findViewById(R.id.km_1_time);
      km3TimeView = (TextView) findViewById(R.id.km_3_time);
      km5TimeView = (TextView) findViewById(R.id.km_5_time);
      km8TimeView = (TextView) findViewById(R.id.km_8_time);
      km10TimeView = (TextView) findViewById(R.id.km_10_time);
      km1TimeTableRow   = (TableRow) findViewById(R.id.km_1_table_row);
      km3TimeTableRow   = (TableRow) findViewById(R.id.km_3_table_row);
      km5TimeTableRow   = (TableRow) findViewById(R.id.km_5_table_row);
      km8TimeTableRow   = (TableRow) findViewById(R.id.km_8_table_row);
      km10TimeTableRow   = (TableRow) findViewById(R.id.km_10_table_row);
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
   
   private void updateUI()
   {      
      getRunStatistics();
   }
   
   private void getRunStatistics(){
      tracksCursor = managedQuery(Tracks.CONTENT_URI, new String[] { Tracks._ID, Tracks.NAME, Tracks.CREATION_TIME }, null, null, Tracks.CREATION_TIME + " DESC");
      numRunsView.setText(Integer.toString(tracksCursor.getCount()));
      tracksCursor.moveToFirst();
      while (tracksCursor.isAfterLast() == false) 
      {
         int id = tracksCursor.getInt(0);
          Log.d("PDFRun", "track id: "+ id);
          tracksCursor.moveToNext();
          StatisticsCalulator calculator = new StatisticsCalulator( this, mUnits, this );
          Uri trackUri = ContentUris.withAppendedId(Tracks.CONTENT_URI, id);
          calculator.execute(trackUri);
      }
   }

   @Override
   public void finishedCalculations(StatisticsCalulator calculated)
   {
      
      Log.d("runpdf", "finishedCalculations: distance " + calculated.getDistanceText());
      totalDistance+=calculated.getDistanceTraveled();
      totalDistanceView.setText(String.format( "%.2f %s", mUnits.conversionFromMeter( totalDistance ),mUnits.getDistanceUnit()));
      if(calculated.getMaxSpeed()>maxSpeed){
         maxSpeed=calculated.getMaxSpeed();
         maxSpeedView.setText(calculated.getMaxSpeedText());
      }
      if(calculated.getKm1Time()>0){
         if(calculated.getKm1Time()<best1kmTime||best1kmTime==0){
            best1kmTime=calculated.getKm1Time();
            km1TimeView.setText(calculated.getKm1TimeText());
         }
         km1TimeTableRow.setVisibility(View.VISIBLE);
      }
      if(calculated.getKm3Time()>0){
         if(calculated.getKm3Time()<best3kmTime||best3kmTime==0){
            best3kmTime=calculated.getKm3Time();
            km3TimeView.setText(calculated.getKm3TimeText());
         }
         km3TimeTableRow.setVisibility(View.VISIBLE);
      }
      if(calculated.getKm5Time()>0){
         if(calculated.getKm5Time()<best5kmTime||best5kmTime==0){
            best5kmTime=calculated.getKm5Time();
            km5TimeView.setText(calculated.getKm5TimeText());
         }
         km5TimeTableRow.setVisibility(View.VISIBLE);
      }
      if(calculated.getKm8Time()>0){
         if(calculated.getKm8Time()<best8kmTime||best8kmTime==0){
            best8kmTime=calculated.getKm8Time();
            km8TimeView.setText(calculated.getKm8TimeText());
         }
         km8TimeTableRow.setVisibility(View.VISIBLE);
      }
      if(calculated.getKm10Time()>0){
         if(calculated.getKm10Time()<best10kmTime||best10kmTime==0){
            best10kmTime=calculated.getKm10Time();
            km10TimeView.setText(calculated.getKm10TimeText());
         }
         km10TimeTableRow.setVisibility(View.VISIBLE);
      }
   }
}
