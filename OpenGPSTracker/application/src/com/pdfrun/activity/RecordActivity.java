package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.actions.ControlTracking;
import nl.sogeti.android.gpstracker.actions.InsertNote;
import nl.sogeti.android.gpstracker.actions.ShareTrack;
import nl.sogeti.android.gpstracker.actions.Statistics;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.Constants;
import nl.sogeti.android.gpstracker.viewer.ApplicationPreferenceActivity;
import nl.sogeti.android.gpstracker.viewer.LoggerMap;
import nl.sogeti.android.gpstracker.viewer.TrackList;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pdfrun.R;

public class RecordActivity extends LoggerMap
{
   protected static final int MENU_TRACKING_PARENT = 15;
   @Override
   protected void onCreate(Bundle load)
   {
      this.layout=R.layout.activity_record;
      super.onCreate(load);
   }
   
   public void trackRun(View view){
      Intent intent = new Intent(this, ControlTracking.class);
      startActivityForResult(intent,MENU_TRACKING_PARENT);
   }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent)
   {
      super.onActivityResult(requestCode, resultCode, intent);
      Uri trackUri;
      long trackId;
       if (requestCode==MENU_TRACKING_PARENT){
            if (resultCode == RESULT_OK)
            {
               trackUri = intent.getData();
               trackId = Long.parseLong(trackUri.getLastPathSegment());
               if (mLoggerServiceManager.getLoggingState() == Constants.STOPPED)
               {
                  Log.d("PDFRun", "Stopped tracking run");
               }
            }
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

}
