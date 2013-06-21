package com.patdivillyfitness.runcoach.activity;


import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.patdivillyfitness.runcoach.R;
import com.patdivillyfitness.runcoach.adapter.MySimpleArrayAdapter;

public class AcademyActivity extends SherlockListActivity
{
   private String[] values;
   
   private static final String TAG="PDFRun";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_academy);
      initialiseData();
      MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
      setListAdapter(adapter);
   }
   
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
      Log.d(TAG, "onListItemClick");
   }
   
   private void initialiseData(){
      values = new String[4];      
      values[0]="Getting Started";
      values[1]="Warm Up";
      values[2]="Training Plan";
      values[3]="Goals";
   }

}
