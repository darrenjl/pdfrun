package com.patdivillyfitness.runcoach.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
//    copyDBToSD();
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
   
   public void copyDBToSD(){
      File f=new File("/data/data/com.patdivillyfitness.runcoach/databases/GPSLOG.db");
      FileInputStream fis=null;
      FileOutputStream fos=null;

      try
      {
        fis=new FileInputStream(f);
        fos=new FileOutputStream("/mnt/sdcard/db_dump.db3");
        while(true)
        {
          int i=fis.read();
          if(i!=-1)
          {fos.write(i);}
          else
          {break;}
        }
        fos.flush();
        Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
      }
      catch(Exception e)
      {
        e.printStackTrace();
        Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
      }
      finally
      {
        try
        {
          fos.close();
          fis.close();
        }
        catch(IOException ioe)
        {}
      }
   }
}
