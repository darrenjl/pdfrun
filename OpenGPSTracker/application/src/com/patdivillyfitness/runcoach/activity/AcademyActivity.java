package com.patdivillyfitness.runcoach.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.patdivillyfitness.runcoach.activity.WarmUpActivity;
import com.patdivillyfitness.runcoach.PDFUtil;
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
         case 1:
            boolean connected = PDFUtil.isConnected(AcademyActivity.this);

            // check for Internet status
            if (!connected) {
                showAlertDialog(AcademyActivity.this, "Internet Connection",
                        "This feature requires that you have internet connectivity enabled.", true);
                intent = null;
            } else {
               intent = new Intent(this, WarmUpActivity.class);
            }
            
            break;
         case 3:
            intent = new Intent(this, GroupsActivity.class);
            break;
         default:
            intent = new Intent(this, DashboardActivity.class);         
      }            
      if(null!=intent)
         startActivity(intent);
   }
   
   private void initialiseData(){
      values = new String[4];      
      values[0]="Getting Started";
      values[1]="Warm Up";
      values[2]="Training Plan";
      values[3]="Goals";
   }
   
   public void showAlertDialog(Context context, String title, String message, Boolean status) {
      AlertDialog alertDialog = new AlertDialog.Builder(context).create();
      alertDialog.setTitle(title);
      alertDialog.setMessage(message);     
      alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
          }
      });

      // Showing Alert Message
      alertDialog.show();
  }

}
