package com.patdivillyfitness.runcoach.activity;

import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.Constants;
import nl.sogeti.android.gpstracker.util.Pair;
import nl.sogeti.android.gpstracker.viewer.TrackList;

import com.patdivillyfitness.runcoach.R;
import com.patdivillyfitness.runcoach.R.layout;
import com.patdivillyfitness.runcoach.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
}
