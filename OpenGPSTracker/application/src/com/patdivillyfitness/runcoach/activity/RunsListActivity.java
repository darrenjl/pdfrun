package com.patdivillyfitness.runcoach.activity;

import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.viewer.TrackList;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.patdivillyfitness.runcoach.R;

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
   
   @Override
   protected Dialog onCreateDialog(int id)
   {
   Dialog dialog = null;
   Builder builder = null;
   switch (id)
   {
      case DIALOG_RENAME:
         LayoutInflater factory = LayoutInflater.from(this);
         View view = factory.inflate(R.layout.namedialog, null);
         mTrackNameView = (EditText) view.findViewById(R.id.nameField);
         builder = new AlertDialog.Builder(this).setTitle(R.string.dialog_routename_title).setMessage(R.string.dialog_routename_message)
               .setPositiveButton(R.string.btn_okay, mRenameOnClickListener).setNegativeButton(R.string.btn_cancel, null).setView(view);
         dialog = builder.create();
         dialog.setOnShowListener(new OnShowListener()
            {
               @Override
               public void onShow(DialogInterface dialog)
               {
                  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                  imm.showSoftInput(mTrackNameView, InputMethodManager.SHOW_IMPLICIT);
               }
            });
         return dialog;
   }
   return super.onCreateDialog(id);
   }
   
   @Override
   protected void displayCursor(Cursor tracksCursor)
   {
      String[] fromColumns = new String[] { Tracks.NAME, Tracks.CREATION_TIME, Tracks._ID };
      int[] toItems = new int[] { R.id.listitem_name, R.id.listitem_from, R.id.bcSyncedCheckBox };
      SimpleCursorAdapter trackAdapter = new SimpleCursorAdapter(this, R.layout.runitem, tracksCursor, fromColumns, toItems);
      setListAdapter(trackAdapter);
   }
}
