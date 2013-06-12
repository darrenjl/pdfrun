package com.patdivillyfitness.runcoach.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.patdivillyfitness.runcoach.R;

public class AcademyActivity extends Activity
{

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_academy);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.academy, menu);
      return true;
   }

}
