package com.patdivillyfitness.runcoach;

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
}
