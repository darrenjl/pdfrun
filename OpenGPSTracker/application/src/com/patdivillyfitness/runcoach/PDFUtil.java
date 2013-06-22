package com.patdivillyfitness.runcoach;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class PDFUtil
{
   private static final String TAG="PDFRun";
   public static String getTimeText(long time)
   {
      long s = time / 1000;
      String duration = "";
      if (s/3600>0)
         duration = String.format("%d:%02d:%02d", s/3600, (s%3600)/60, (s%60));
      else
         duration = String.format("%02d:%02d", (s%3600)/60, (s%60));

      return duration;
   }
   
   public static boolean isConnected(Context context){
      ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) 
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) 
                for (int i = 0; i < info.length; i++) 
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
  }
   
   public static void copyFile(InputStream fromFile, OutputStream toFile) throws IOException {
      // transfer bytes from the inputfile to the outputfile
      byte[] buffer = new byte[1024];
      int length;

      try {
          while ((length = fromFile.read(buffer)) > 0) {
              toFile.write(buffer, 0, length);
          }
      }
      // Close the streams
      finally {
          try {
              if (toFile != null) {
                  try {
                      toFile.flush();
                  } finally {
                      toFile.close();
                  }
          }
          } finally {
              if (fromFile != null) {
                  fromFile.close();
              }
          }
      }
  }
   
   public static int getLevelDrawable(long time, int[] goals){
      Log.d(TAG, "getLevelDrawable");
      if(time<goals[2])
         return R.drawable.medal_gold;
      else if (time<goals[1])
         return R.drawable.medal_silver;
      else if (time<goals[0])
         return R.drawable.medal_bronze;
      return R.drawable.ic_launcher;
   }
}
