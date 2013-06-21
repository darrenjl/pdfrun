package com.patdivillyfitness.runcoach.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.patdivillyfitness.runcoach.Constants;
import com.patdivillyfitness.runcoach.R;

public class GroupDetailsActivity extends Activity
{
   private static final String TAG="PDFRun";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_group_details);
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
         String[] module = (String[]) extras.get("module");
         Log.d(TAG,String.valueOf((extras.getInt(Constants.BRONZE_GOAL, 0))));
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.group_details, menu);
      return true;
   }

}
