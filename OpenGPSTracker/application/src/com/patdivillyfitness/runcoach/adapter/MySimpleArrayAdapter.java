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

public class MySimpleArrayAdapter extends ArrayAdapter<TypedArray> {
	  private final Context context;
	  private final List<TypedArray> values;

	  public MySimpleArrayAdapter(Context context, List<TypedArray> values) {
	    super(context, android.R.layout.simple_list_item_1, values);
	    Log.d("P2", "MySimpleArrayAdapter constructor");
	    this.context = context;
	    this.values = values;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  Log.d("P2", Integer.toString(position));
		  Log.d("P2", values.get(position).getString(0));
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.groupitem, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.goal_name);
	    textView.setText(values.get(position).getString(0));
	    return rowView;
	  }
	} 