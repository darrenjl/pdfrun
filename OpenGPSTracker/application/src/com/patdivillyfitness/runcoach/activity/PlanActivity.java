package com.patdivillyfitness.runcoach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.patdivillyfitness.runcoach.PDFUtil;
import com.patdivillyfitness.runcoach.R;
import com.patdivillyfitness.runcoach.adapter.MySimpleArrayAdapter;


public class PlanActivity extends SherlockListActivity
{
   private String[] values;   
   private static final String TAG="PDFRun";

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_plan);
      initialiseData();
      MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
      setListAdapter(adapter);
      setupActionBar();
   }

   /**
    * Set up the {@link android.app.ActionBar}.
    */
   private void setupActionBar()
   {

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      switch (item.getItemId())
      {
         case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
      }
      return super.onOptionsItemSelected(item);
   }
   
   private void initialiseData(){
      values = new String[3];      
      values[0]=getString(R.string.wk1);
      values[1]=getString(R.string.wk2);
      values[2]=getString(R.string.wk3);
   }
   
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
      Intent intent;
      switch(position){
         case 0:
            intent = new Intent(this, PlanWk1Activity.class);
            break;
         case 1:
            intent = new Intent(this, PlanWk2Activity.class);
            break;
         case 2:
            intent = new Intent(this, PlanWk3Activity.class);
            break;
         default:
            intent = new Intent(this, DashboardActivity.class);         
      }            
      if(null!=intent)
         startActivity(intent);
   }

}
