package com.patdivillyfitness.runcoach.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.patdivillyfitness.runcoach.R;

public class GroupsActivity extends SherlockActivity
{
   private TextView txtDisplay;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_groups);
      txtDisplay = (TextView) findViewById(R.id.response);

      RequestQueue queue = Volley.newRequestQueue(this);
      String url = "http://pat-divilly-fitness.appspot.com/api/groups/";

      JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

         @Override
         public void onResponse(JSONObject response) {
            // TODO Auto-generated method stub
            txtDisplay.setText("Response => "+response.toString());
            findViewById(R.id.progressBar1).setVisibility(View.GONE);
         }
      }, new Response.ErrorListener() {

         @Override
         public void onErrorResponse(VolleyError error) {
            // TODO Auto-generated method stub

         }
      });

      queue.add(jsObjRequest);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getSupportMenuInflater().inflate(R.menu.groups, menu);
      return true;
   }

}
