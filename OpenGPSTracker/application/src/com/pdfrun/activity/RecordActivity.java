package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.actions.ControlTracking;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.viewer.LoggerMap;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pdfrun.R;

public class RecordActivity extends LoggerMap
{
   private boolean isGPSEnabled;
   private LocationManager locationManager;

   @Override
   protected void onCreate(Bundle load)
   {
      this.layout = R.layout.activity_record;
      locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
      super.onCreate(load);
   }

   public void trackRun(View view)
   {
      checkGPSAndOpenControls();
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent)
   {
      super.onActivityResult(requestCode, resultCode, intent);
      if (requestCode == MENU_TRACKING)
      {
         if (resultCode == RESULT_OK)
         {
            if (intent.getBooleanExtra(com.pdfrun.Constants.TRACKING_STOPPED, false))
            {
               Log.d("PDFRun", "Stopped tracking run");
               Uri trackUri = ContentUris.withAppendedId(Tracks.CONTENT_URI, mTrackId);
               Intent detailsIntent = new Intent(this, RunDetailsActivity.class);
               detailsIntent.setData(trackUri);
               startActivity(detailsIntent);
            }
         } 
      } else if(requestCode==100){
         checkGPSAndOpenControls();
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      return true;
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu)
   {
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      return true;
   }

   @Override
   protected void updateTitleBar()
   {
      ContentResolver resolver = this.getContentResolver();
      Cursor trackCursor = null;
      try
      {
         trackCursor = resolver.query(ContentUris.withAppendedId(Tracks.CONTENT_URI, this.mTrackId), new String[] { Tracks.NAME }, null, null, null);
         if (trackCursor != null && trackCursor.moveToLast())
         {
            String trackName = trackCursor.getString(0);
            this.setTitle(trackName);
         }
      }
      finally
      {
         if (trackCursor != null)
         {
            trackCursor.close();
         }
      }
   }

   @Override
   public boolean onKeyDown(int keycode, KeyEvent e)
   {
      switch (keycode)
      {
         case KeyEvent.KEYCODE_MENU:
            checkGPSAndOpenControls();
            return true;
      }

      return super.onKeyDown(keycode, e);
   }

   private void checkGPSAndOpenControls()
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
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage("Your GPS module is disabled. Would you like to enable it ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
               public void onClick(DialogInterface dialog, int id)
               {

                  //Sent user to GPS settings screen
                  final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                  startActivityForResult(intent, 100);

                  dialog.dismiss();

               }
            }).setNegativeButton("No", new DialogInterface.OnClickListener()
            {
               public void onClick(DialogInterface dialog, int id)
               {
                  dialog.cancel();
               }
            });
         AlertDialog alert = builder.create();
         alert.show();
      } else {
         Intent intent = new Intent(this, ControlTracking.class);
         startActivityForResult(intent, MENU_TRACKING);
      }
   }

}
