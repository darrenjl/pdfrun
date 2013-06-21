package com.patdivillyfitness.runcoach;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PDFUtil
{
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
}
