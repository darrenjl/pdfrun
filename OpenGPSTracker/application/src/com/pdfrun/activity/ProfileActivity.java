package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;

import com.pdfrun.R;
import com.pdfrun.R.layout;
import com.pdfrun.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class ProfileActivity extends Activity
{
   private Cursor tracksCursor;
   private TextView numRunsView;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile);
      numRunsView = (TextView)findViewById(R.id.numRuns);
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
      tracksCursor = managedQuery(Tracks.CONTENT_URI, new String[] { Tracks._ID, Tracks.NAME, Tracks.CREATION_TIME }, null, null, Tracks.CREATION_TIME + " DESC");
      int runCount = tracksCursor.getCount();
      numRunsView = (TextView)findViewById(R.id.numRuns);
      numRunsView.setText(Integer.toString(runCount));
   }

}
