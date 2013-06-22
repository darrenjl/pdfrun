package com.patdivillyfitness.runcoach.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
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
               if (errorReason.isUserRecoverableError()) {
                  errorReason.getErrorDialog(WarmUpActivity.this, RECOVERY_DIALOG_REQUEST).show();
                } else {
                  String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
                  Toast.makeText(WarmUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

         });
   }

}
