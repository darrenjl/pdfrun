package com.patdivillyfitness.runcoach.activity;


import android.content.Intent;
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
      Intent intent;
      switch(position){
         case 3:
            intent = new Intent(this, GroupsActivity.class);
            break;
         default:
            intent = new Intent(this, DashboardActivity.class);         
      }            
      startActivity(intent);
   }
   
   private void initialiseData(){
      values = new String[4];      
      values[0]="Getting Started";
      values[1]="Warm Up";
      values[2]="Training Plan";
      values[3]="Goals";
   }

}
