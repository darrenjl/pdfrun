package com.patdivillyfitness.runcoach.adapter;

import java.util.ArrayList;
import java.util.List;

import com.patdivillyfitness.runcoach.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  private final String[] values;

	  public MySimpleArrayAdapter(Context context, String[] values) {
	    super(context, android.R.layout.simple_list_item_1, values);
	    Log.d("P2", "MySimpleArrayAdapter constructor");
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  Log.d("P2", Integer.toString(position));
		  Log.d("P2", values[position]);
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.academyitem, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.option);
	    textView.setText(values[position]);
	    return rowView;
	  }
	  
	} 