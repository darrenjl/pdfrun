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
   private TextView totalDistanceView;
   private TextView maxSpeedView;
   private UnitsI18n mUnits;
   private float totalDistance=0;
   private double maxSpeed=0;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile);
      numRunsView = (TextView)findViewById(R.id.numRuns);
      totalDistanceView = (TextView)findViewById(R.id.totalDistance);
      maxSpeedView = (TextView)findViewById(R.id.maxSpeed);
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
   
   private void getTotalDistance(){
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
   }
}
