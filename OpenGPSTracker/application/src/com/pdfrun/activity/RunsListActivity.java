package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.Constants;
import nl.sogeti.android.gpstracker.util.Pair;
import nl.sogeti.android.gpstracker.viewer.TrackList;

import com.pdfrun.R;
import com.pdfrun.R.layout;
import com.pdfrun.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class RunsListActivity extends TrackList
{
   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      return true;
   }

   @Override
   protected void onListItemClick(ListView listView, View view, int position, long id)
   {
      //super.onListItemClick(listView, view, position, id);

      //Object item = listView.getItemAtPosition(position);

      Intent intent = new Intent(this, RunDetailsActivity.class);
      Uri trackUri = ContentUris.withAppendedId(Tracks.CONTENT_URI, id);
      intent.setData(trackUri);
      startActivity(intent);
   }
}
