/**
 * author: Carter Kerstetter
 */

package carter.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;

public class About extends AppCompatActivity {
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about);
        music= MediaPlayer.create(About.this,R.raw.about_music);
        music.setLooping(true);
        music.start();
    }

    @Override
    protected void onPause() {
        if (music.isPlaying()) {
            music.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }
}
