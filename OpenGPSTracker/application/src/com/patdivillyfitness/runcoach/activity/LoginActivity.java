package com.patdivillyfitness.runcoach.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.patdivillyfitness.runcoach.R;

public class LoginActivity extends FragmentActivity
{
   private static final int SPLASH = 0;
   private static final int FRAGMENT_COUNT = SPLASH + 1;
   private boolean isResumed = false;
   private static final int REAUTH_ACTIVITY_CODE = 100;

   private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      //getHash();
      uiHelper = new UiLifecycleHelper(this, callback);
      uiHelper.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);

      FragmentManager fm = getSupportFragmentManager();
      fragments[SPLASH] = fm.findFragmentById(R.id.loginFragment);

      FragmentTransaction transaction = fm.beginTransaction();
      for (int i = 0; i < fragments.length; i++)
      {
         transaction.hide(fragments[i]);
      }
      transaction.commit();
   }

   private void showFragment(int fragmentIndex, boolean addToBackStack)
   {
      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction transaction = fm.beginTransaction();
      for (int i = 0; i < fragments.length; i++)
      {
         if (i == fragmentIndex)
         {
            transaction.show(fragments[i]);
         }
         else
         {
            transaction.hide(fragments[i]);
         }
      }
      if (addToBackStack)
      {
         transaction.addToBackStack(null);
      }
      transaction.commit();
   }

   @Override
   public void onResume()
   {
      super.onResume();
      uiHelper.onResume();
      isResumed = true;
   }

   @Override
   public void onPause()
   {
      super.onPause();

      uiHelper.onPause();
      isResumed = false;
   }

   private void onSessionStateChange(Session session, SessionState state, Exception exception)
   {
      // Only make changes if the activity is visible
      if (isResumed)
      {
         FragmentManager manager = getSupportFragmentManager();
         // Get the number of entries in the back stack
         int backStackSize = manager.getBackStackEntryCount();
         // Clear the back stack
         for (int i = 0; i < backStackSize; i++)
         {
            manager.popBackStack();
         }
         if (state.isOpened())
         {
            // If the session state is open:
            // Show the authenticated fragment
            Log.d("PDFRun", "Session state changed - Logged in");
            makeMeRequest(session);
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            
//            session.closeAndClearTokenInformation();
         }
         else if (state.isClosed())
         {
            // If the session state is closed:
            // Show the login fragment
            showFragment(SPLASH, false);
            LoginButton authButton = (LoginButton) findViewById(R.id.login_button);
            authButton.setOnErrorListener(new OnErrorListener() {
             
             @Override
             public void onError(FacebookException error) {
              Log.i("PDFRun", "Error " + error.getMessage());
             }
            });
            // set permission list, Don't foeget to add email
            authButton.setReadPermissions(Arrays.asList("basic_info","email"));
         }
      }
   }

   @Override
   protected void onResumeFragments()
   {
      super.onResumeFragments();
      Session session = Session.getActiveSession();

      if (session != null && session.isOpened())
      {
         // if the session is already open,
         // try to show the selection fragment
         Log.d("PDFRun", "fragments resumed - Logged in");
         makeMeRequest(session);
         Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
         startActivity(intent);         
//         session.closeAndClearTokenInformation();
      }
      else
      {
         // otherwise present the splash screen
         // and ask the person to login.
         showFragment(SPLASH, false);
         LoginButton authButton = (LoginButton) findViewById(R.id.login_button);
         authButton.setOnErrorListener(new OnErrorListener() {
          
          @Override
          public void onError(FacebookException error) {
           Log.i("PDFRun", "Error " + error.getMessage());
          }
         });
         // set permission list, Don't foeget to add email
         authButton.setReadPermissions(Arrays.asList("basic_info","email"));
      }
   }

   private UiLifecycleHelper uiHelper;
   private Session.StatusCallback callback = new Session.StatusCallback()
      {
         @Override
         public void call(Session session, SessionState state, Exception exception)
         {
            onSessionStateChange(session, state, exception);
         }
      };

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      super.onActivityResult(requestCode, resultCode, data);
      uiHelper.onActivityResult(requestCode, resultCode, data);
   }

   @Override
   public void onDestroy()
   {
      super.onDestroy();
      uiHelper.onDestroy();
   }

   @Override
   protected void onSaveInstanceState(Bundle outState)
   {
      super.onSaveInstanceState(outState);
      uiHelper.onSaveInstanceState(outState);
   }

   public void skipLogin(View view)
   {
      Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
      startActivity(intent);
   }
   
   private void getHash(){
      try {
         PackageInfo info = getPackageManager().getPackageInfo(
                 "com.patdivillyfitness.runcoach", PackageManager.GET_SIGNATURES);
         for (Signature signature : info.signatures) {
             MessageDigest md = MessageDigest.getInstance("SHA");
             md.update(signature.toByteArray());
             Log.e("MY KEY HASH:",
                     Base64.encodeToString(md.digest(), Base64.DEFAULT));
         }
     } catch (NameNotFoundException e) {

     } catch (NoSuchAlgorithmException e) {}
   }
   
   private void makeMeRequest(final Session session) {
      // Make an API call to get user data and define a 
      // new callback to handle the response.
      Request request = Request.newMeRequest(session, 
              new Request.GraphUserCallback() {
          @Override
          public void onCompleted(GraphUser user, Response response) {
              // If the response is successful
              if (session == Session.getActiveSession()) {
                  if (user != null) {
                      // Set the id for the ProfilePictureView
                      // view that in turn displays the profile picture.
//                      profilePictureView.setProfileId(user.getId());
//                      // Set the Textview's text to the user's name.
//                      userNameView.setText(user.getName());
                      Log.d("PDFRun", "User email: "+user.getProperty("email") );
                  }
              }
              if (response.getError() != null) {
                  // Handle errors, will do so later.
              }
          }
      });
      Bundle params = request.getParameters();
      params.putString("fields", "email,name");
      request.setParameters(params);
      request.executeAsync();
  } 
}
