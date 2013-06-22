package com.patdivillyfitness.runcoach;

import com.patdivillyfitness.runcoach.activity.WarmUpActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

public class AppLaunchChecker {
   private final static String APP_TITLE = Constants.APP_NAME;
   private final static String APP_PNAME = Constants.APP_PACKAGE;
   
   private final static int DAYS_UNTIL_PROMPT = 5;
   private final static int LAUNCHES_UNTIL_PROMPT = 4;
   
   public static boolean checkFirstOrRateLaunch(Context mContext) {
       SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
       if (prefs.getBoolean("dontshowagain", false)) { return true; }
       
       SharedPreferences.Editor editor = prefs.edit();
       
       // Increment launch counter
       long launch_count = prefs.getLong("launch_count", 0) + 1;
       editor.putLong("launch_count", launch_count);

       // Get date of first launch
       Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
       if (date_firstLaunch == 0) {
           date_firstLaunch = System.currentTimeMillis();
           editor.putLong("date_firstlaunch", date_firstLaunch);
       }
       
       // Wait at least n days before opening
       if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
           if (System.currentTimeMillis() >= date_firstLaunch + 
                   (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
               showRateDialog(mContext, editor);
           }
       }
       
       editor.commit();
       if (launch_count==1)
          return true;
       else
          return false;
   }   
   
   public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor)
   {

      AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

      alertDialog.setTitle("Rate " + APP_TITLE);

      alertDialog.setMessage("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");

      alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Rate", new DialogInterface.OnClickListener()
         {

            public void onClick(DialogInterface dialog, int id)
            {
               //              mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
               Toast.makeText(mContext, "Google Play will be launched, once the app is on the market", Toast.LENGTH_LONG).show();
               dialog.dismiss();
            }
         });

      alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Remind", new DialogInterface.OnClickListener()
         {

            public void onClick(DialogInterface dialog, int id)
            {

               dialog.dismiss();

            }
         });

      alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener()
         {

            public void onClick(DialogInterface dialog, int id)
            {

               if (editor != null)
               {
                  editor.putBoolean("dontshowagain", true);
                  editor.commit();
               }
               dialog.dismiss();

            }
         });

      alertDialog.show();
   }
}
