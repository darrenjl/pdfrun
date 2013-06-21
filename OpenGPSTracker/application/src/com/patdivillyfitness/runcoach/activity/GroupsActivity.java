package com.patdivillyfitness.runcoach.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.patdivillyfitness.runcoach.Constants;
import com.patdivillyfitness.runcoach.R;
import com.patdivillyfitness.runcoach.adapter.MySimpleArrayAdapter;

public class GroupsActivity extends SherlockListActivity
{
   private ArrayList<TypedArray> values;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_groups);
      initialiseData();
      MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
      setListAdapter(adapter);
   }

   @Override
   protected void onListItemClick(ListView l, View v, int position, long id)
   {
      Intent intent = new Intent(this, GroupDetailsActivity.class);
      intent.putExtra(Constants.BRONZE_GOAL, values.get(position).getInt(1,0));
      intent.putExtra(Constants.SILVER_GOAL, values.get(position).getInt(2,0));
      intent.putExtra(Constants.GOLD_GOAL, values.get(position).getInt(3,0));
      startActivity(intent);
   }

   private void initialiseData()
   {
      values = new ArrayList<TypedArray>();
      Resources res = getResources();
      values.add(res.obtainTypedArray(R.array.goals_1km));
      values.add(res.obtainTypedArray(R.array.goals_3km));
      values.add(res.obtainTypedArray(R.array.goals_5km));
      values.add(res.obtainTypedArray(R.array.goals_8km));
      values.add(res.obtainTypedArray(R.array.goals_10km));
   }
   
   @Override
   protected void onDestroy()
   {
      for (TypedArray arr:values)
         arr.recycle();
      super.onDestroy();
   }
}
