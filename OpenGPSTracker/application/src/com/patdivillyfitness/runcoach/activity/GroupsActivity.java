package com.patdivillyfitness.runcoach.activity;

import java.util.ArrayList;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.patdivillyfitness.runcoach.PDFUtil;
import com.patdivillyfitness.runcoach.R;

public class GroupsActivity extends SherlockActivity
{
   private ArrayList<TypedArray> values;
   private int[] goals;
   private TextView goal1KmBronze;
   private TextView goal1KmSilver;
   private TextView goal1KmGold;
   
   private TextView goal3KmBronze;
   private TextView goal3KmSilver;
   private TextView goal3KmGold;
   
   private TextView goal5KmBronze;
   private TextView goal5KmSilver;
   private TextView goal5KmGold;
   
   private TextView goal8KmBronze;
   private TextView goal8KmSilver;
   private TextView goal8KmGold;
   
   private TextView goal10KmBronze;
   private TextView goal10KmSilver;
   private TextView goal10KmGold;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_groups);
      
      goal1KmBronze = (TextView) findViewById(R.id.bronze_km_1_time);
      goal1KmSilver = (TextView) findViewById(R.id.silver_km_1_time);
      goal1KmGold = (TextView) findViewById(R.id.gold_km_1_time);
      
      goal3KmBronze = (TextView) findViewById(R.id.bronze_km_3_time);
      goal3KmSilver = (TextView) findViewById(R.id.silver_km_3_time);
      goal3KmGold = (TextView) findViewById(R.id.gold_km_3_time);
      
      goal5KmBronze = (TextView) findViewById(R.id.bronze_km_5_time);
      goal5KmSilver = (TextView) findViewById(R.id.silver_km_5_time);
      goal5KmGold = (TextView) findViewById(R.id.gold_km_5_time);
      
      goal8KmBronze = (TextView) findViewById(R.id.bronze_km_8_time);
      goal8KmSilver = (TextView) findViewById(R.id.silver_km_8_time);
      goal8KmGold = (TextView) findViewById(R.id.gold_km_8_time);
      
      goal10KmBronze = (TextView) findViewById(R.id.bronze_km_10_time);
      goal10KmSilver = (TextView) findViewById(R.id.silver_km_10_time);
      goal10KmGold = (TextView) findViewById(R.id.gold_km_10_time);
      Resources res = getResources();      
      goals=res.getIntArray(R.array.goals_1km);
      goal1KmBronze.setText(PDFUtil.getTimeText(goals[0]));
      goal1KmSilver.setText(PDFUtil.getTimeText(goals[1]));
      goal1KmGold.setText(PDFUtil.getTimeText(goals[2]));
      
      goals=res.getIntArray(R.array.goals_3km);
      goal3KmBronze.setText(PDFUtil.getTimeText(goals[0]));
      goal3KmSilver.setText(PDFUtil.getTimeText(goals[1]));
      goal3KmGold.setText(PDFUtil.getTimeText(goals[2]));
      
      goals=res.getIntArray(R.array.goals_5km);
      goal5KmBronze.setText(PDFUtil.getTimeText(goals[0]));
      goal5KmSilver.setText(PDFUtil.getTimeText(goals[1]));
      goal5KmGold.setText(PDFUtil.getTimeText(goals[2]));
      
      goals=res.getIntArray(R.array.goals_8km);
      goal8KmBronze.setText(PDFUtil.getTimeText(goals[0]));
      goal8KmSilver.setText(PDFUtil.getTimeText(goals[1]));
      goal8KmGold.setText(PDFUtil.getTimeText(goals[2]));
      
      goals=res.getIntArray(R.array.goals_10km);
      goal10KmBronze.setText(PDFUtil.getTimeText(goals[0]));
      goal10KmSilver.setText(PDFUtil.getTimeText(goals[1]));
      goal10KmGold.setText(PDFUtil.getTimeText(goals[2]));
   }
   

   }
