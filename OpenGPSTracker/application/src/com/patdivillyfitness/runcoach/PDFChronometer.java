package com.patdivillyfitness.runcoach;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

public class PDFChronometer extends Chronometer {

      public long msElapsed;
      public boolean isRunning = false;

      public PDFChronometer(Context context) {
          super(context);
      }

      public PDFChronometer(Context context, AttributeSet attrs)
      {
          super(context, attrs);
      }

      public PDFChronometer(Context context, AttributeSet attrs, int defStyle)
      {
          super(context, attrs, defStyle);    
      }

      public long getMsElapsed() {
          return msElapsed;
      }

      public void setMsElapsed(long ms) {
          setBase(getBase() - ms);
          msElapsed  = ms;
      }

      @Override
      public void start() {
          super.start();
          setBase(SystemClock.elapsedRealtime() - msElapsed);
          isRunning = true;
      }

      @Override
      public void stop() {
          super.stop();
          if(isRunning) {
              msElapsed = (long)(SystemClock.elapsedRealtime() - this.getBase());
          }
          isRunning = false;
      }
  }