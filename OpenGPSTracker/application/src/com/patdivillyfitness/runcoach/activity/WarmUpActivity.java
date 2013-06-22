package com.patdivillyfitness.runcoach.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.patdivillyfitness.runcoach.DeveloperKey;
import com.patdivillyfitness.runcoach.R;

public class WarmUpActivity extends SherlockFragmentActivity
{

   static private final String DEVELOPER_KEY = DeveloperKey.DEVELOPER_KEY;
   static private final String VIDEO = "BQRhZdRgAs4";
   private static final int RECOVERY_DIALOG_REQUEST = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_warm_up);

      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

      YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
      fragmentTransaction.add(R.id.fragment, fragment);
      fragmentTransaction.commit();

      fragment.initialize(DEVELOPER_KEY, new OnInitializedListener()
         {

            @Override
            public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1, boolean arg2)
            {
               if (!arg2)
               {
                  arg1.loadVideo(VIDEO);
               }
            }

            @Override
            public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason)
            {
               if (errorReason.isUserRecoverableError())
               {
                  errorReason.getErrorDialog(WarmUpActivity.this, RECOVERY_DIALOG_REQUEST).show();
               }
               else
               {
                  String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
                  Toast.makeText(WarmUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
               }
            }

         });
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

}
