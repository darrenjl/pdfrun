package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.viewer.ApplicationPreferenceActivity;
import nl.sogeti.android.gpstracker.viewer.TrackList;

import com.pdfrun.R;
import com.pdfrun.R.id;
import com.pdfrun.R.layout;
import com.pdfrun.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity
{

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.dashboard_layout);
      /**
       * Creating all buttons instances
       * */
      Button btn_record = (Button) findViewById(R.id.btn_record);

      Button btn_runs = (Button) findViewById(R.id.btn_runs);

      Button btn_profile = (Button) findViewById(R.id.btn_profile);

      Button btn_groups = (Button) findViewById(R.id.btn_groups);

      Button btn_academy = (Button) findViewById(R.id.btn_academy);

      Button btn_settings = (Button) findViewById(R.id.btn_settings);
       
      /**
       * Handling all button click events
       * */
      btn_record.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(), RecordActivity.class);
              startActivity(i);
          }
      });
       
      btn_runs.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(), TrackList.class);
              startActivity(i);
          }
      });
       
      btn_profile.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
              startActivity(i);
          }
      });
       
      btn_groups.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(), GroupsActivity.class);
              startActivity(i);
          }
      });
       
      btn_academy.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent i = new Intent(getApplicationContext(), AcademyActivity.class);
              startActivity(i);
          }
      });
       
      btn_settings.setOnClickListener(new View.OnClickListener() {
           
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(), ApplicationPreferenceActivity.class);
              startActivity(intent);
          }
      });
  }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.dashboard, menu);
      return true;
   }

}
