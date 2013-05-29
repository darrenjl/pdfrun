package com.pdfrun.activity;

import com.pdfrun.R;
import com.pdfrun.R.layout;
import com.pdfrun.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RunDetailsActivity extends Activity
{

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_run_details);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.run_details, menu);
      return true;
   }

}
