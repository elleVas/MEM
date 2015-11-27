package com.android.lele_phobia.mem;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class SplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private MediaPlayer mp1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {



        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        // Create a media player for sounds.
        mp1 = MediaPlayer.create (this, R.raw.intro);
        mp1.start ();
        Log.d("Splash", "onCreate - created MediaPlayer");

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    protected void onDestroy ()
    {


        if (mp1 != null) {
            if (mp1.isPlaying ()) mp1.stop ();
            mp1.release ();
            mp1 = null;
            Log.d("Splash", "LauncherActivity.onDestroy - released MediaPlayer");
        }

        super.onDestroy();
    }
}

