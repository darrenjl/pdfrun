package com.patdivillyfitness.runcoach.activity;

import nl.sogeti.android.gpstracker.viewer.ApplicationPreferenceActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.patdivillyfitness.runcoach.R;

public class DashboardActivity extends Activity
{
   private boolean isGPSEnabled;
   private LocationManager locationManager;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
      setContentView(R.layout.dashboard_layout);
      /**
       * Creating all buttons instances
       */
      Button btn_record = (Button) findViewById(R.id.btn_record);

      Button btn_runs = (Button) findViewById(R.id.btn_runs);

      Button btn_profile = (Button) findViewById(R.id.btn_profile);

      Button btn_about = (Button) findViewById(R.id.btn_about);

      Button btn_academy = (Button) findViewById(R.id.btn_academy);

      Button btn_settings = (Button) findViewById(R.id.btn_settings);

      /**
       * Handling all button click events
       */
      btn_record.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               checkGPSAndGoToRecordingPage();
            }
         });

      btn_runs.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(getApplicationContext(), RunsListActivity.class);
               startActivity(i);
            }
         });

      btn_profile.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
               startActivity(i);
            }
         });

      btn_about.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(getApplicationContext(), InfoActivity.class);
               startActivity(i);
            }
         });

      btn_academy.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               Intent i = new Intent(getApplicationContext(), AcademyActivity.class);
               startActivity(i);
            }
         });

      btn_settings.setOnClickListener(new View.OnClickListener()
         {

            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(getApplicationContext(), ApplicationPreferenceActivity.class);
               startActivity(intent);
            }
         });
   }

   private void checkGPSAndGoToRecordingPage()
   {
      try
      {
         isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
      }
      catch (Exception ex)
      {
      }

      if (!isGPSEnabled)
      {
         alertGPSDisabled();
      }
      else
      {
         Intent i = new Intent(getApplicationContext(), RecordActivity.class);
         startActivity(i);
      }
   }

   private void alertGPSDisabled()
   {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(getString(R.string.gps_disabled_text)).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
         {
            public void onClick(DialogInterface dialog, int id)
            {

               //Sent user to GPS settings screen
               final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               startActivityForResult(intent, 100);

               dialog.dismiss();

            }
         }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener()
         {
            public void onClick(DialogInterface dialog, int id)
            {
               dialog.cancel();
            }
         });
      AlertDialog alert = builder.create();
      alert.show();
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent)
   {
      super.onActivityResult(requestCode, resultCode, intent);
      if (requestCode == 100)
      {
         checkGPSAndGoToRecordingPage();
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      //      getMenuInflater().inflate(R.menu.dashboard, menu);
      return true;
   }
   //
   //   @Override
   //   public boolean onOptionsItemSelected(MenuItem item)
   //   {
   //      switch (item.getItemId())
   //      {
   //         case R.id.action_info:
   //            Intent intent = new Intent(this, InfoActivity.class);
   //            startActivity(intent);
   //            break;
   //      }
   //      return super.onOptionsItemSelected(item);
   //   }
}
