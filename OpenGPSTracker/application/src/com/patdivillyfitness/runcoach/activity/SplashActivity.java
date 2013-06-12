package com.patdivillyfitness.runcoach.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.patdivillyfitness.runcoach.R;

public class SplashActivity extends Activity {

   private static String TAG = SplashActivity.class.getName();
   private static long SLEEP_TIME = 3;    

   @Override
   protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.requestWindowFeature(Window.FEATURE_NO_TITLE);   
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);   

    setContentView(R.layout.activity_splash);

    IntentLauncher launcher = new IntentLauncher();
    launcher.start();
  }
   
   private class IntentLauncher extends Thread {
    @Override
    public void run() {
       try {
          Thread.sleep(SLEEP_TIME*1000);
       } catch (Exception e) {
          Log.e(TAG, e.getMessage());
       }
       Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
       SplashActivity.this.startActivity(intent);
       SplashActivity.this.finish();
    }
  }
}
