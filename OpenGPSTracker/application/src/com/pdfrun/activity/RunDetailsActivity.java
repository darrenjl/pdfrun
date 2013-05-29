/*------------------------------------------------------------------------------
 **    Author: Darren Lyons
 *     This file is a simplified version of the Statistics.java 
 *     file originally in this project. I have removed a lot of the code
 *     and also added some customisations.
 */
package com.pdfrun.activity;

import nl.sogeti.android.gpstracker.actions.utils.StatisticsCalulator;
import nl.sogeti.android.gpstracker.actions.utils.StatisticsDelegate;
import nl.sogeti.android.gpstracker.db.GPStracking.Tracks;
import nl.sogeti.android.gpstracker.util.UnitsI18n;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.pdfrun.R;

public class RunDetailsActivity extends Activity implements StatisticsDelegate
   {
      private static final String TRACKURI = "TRACKURI";
      private static final String TAG = "pdfrun";

      private Uri mTrackUri = null;
      private boolean calculating;
      private TextView avgSpeedView;
      private TextView distanceView;
      private TextView maxSpeedView;
      private TextView mAscensionView;
      private TextView mElapsedTimeView;

      private UnitsI18n mUnits;

      private final ContentObserver mTrackObserver = new ContentObserver( new Handler() )
      {
      
         @Override
         public void onChange( boolean selfUpdate )
         {
            if( !calculating )
            {
               RunDetailsActivity.this.drawTrackingStatistics();
            }
         }
      };
      

      /**
       * Called when the activity is first created.
       */
      @Override
      protected void onCreate( Bundle load )
      {
         super.onCreate( load );
         mUnits = new UnitsI18n( this, new UnitsI18n.UnitsChangeListener()
            {
               @Override
               public void onUnitsChange()
               {
                  drawTrackingStatistics();
               }
            } );
         setContentView( R.layout.activity_run_details );

         maxSpeedView = (TextView) findViewById( R.id.stat_maximumspeed );
         mAscensionView   = (TextView) findViewById( R.id.stat_ascension );
         mElapsedTimeView = (TextView) findViewById( R.id.stat_elapsedtime );
         avgSpeedView = (TextView) findViewById( R.id.stat_averagespeed );
         distanceView = (TextView) findViewById( R.id.stat_distance );

         if( load != null && load.containsKey( TRACKURI ) )
         {
            mTrackUri = Uri.withAppendedPath( Tracks.CONTENT_URI, load.getString( TRACKURI ) );
         }
         else
         {
            mTrackUri = this.getIntent().getData();
         }
      }

      @Override
      protected void onRestoreInstanceState( Bundle load )
      {
         if( load != null )
         {
            super.onRestoreInstanceState( load );
         }
         if( load != null && load.containsKey( TRACKURI ) )
         {
            mTrackUri = Uri.withAppendedPath( Tracks.CONTENT_URI, load.getString( TRACKURI ) );
         }
      }

      @Override
      protected void onSaveInstanceState( Bundle save )
      {
         super.onSaveInstanceState( save );
         save.putString( TRACKURI, mTrackUri.getLastPathSegment() );
      }

      /*
       * (non-Javadoc)
       * @see android.app.Activity#onPause()
       */
      @Override
      protected void onPause()
      {
         super.onPause();
         ContentResolver resolver = this.getContentResolver();
         resolver.unregisterContentObserver( this.mTrackObserver );
      }

      /*
       * (non-Javadoc)
       * @see android.app.Activity#onResume()
       */
      @Override
      protected void onResume()
      {
         super.onResume();
         drawTrackingStatistics();
         
         ContentResolver resolver = this.getContentResolver();
         resolver.registerContentObserver( mTrackUri, true, this.mTrackObserver );
      }
      

      private void drawTrackingStatistics()
      {
         calculating = true;
         StatisticsCalulator calculator = new StatisticsCalulator( this, mUnits, this );
         calculator.execute(mTrackUri);
      }

      @Override
      public void finishedCalculations(StatisticsCalulator calculated)
      {
         

         maxSpeedView.setText( calculated.getMaxSpeedText() );
         mElapsedTimeView.setText( calculated.getDurationText() );
         mAscensionView.setText( calculated.getAscensionText() );
         avgSpeedView.setText( calculated.getAvgSpeedText() );
         distanceView.setText( calculated.getDistanceText() );
         String titleFormat = getString( R.string.stat_title );
         setTitle( String.format( titleFormat, calculated.getTracknameText() ) );

         calculating = false;
      }
   }
