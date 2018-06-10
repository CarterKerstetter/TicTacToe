package carter.tictactoe;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Class used to open the about page.
 */
public class About extends AppCompatActivity {
    private MediaPlayer music;

    /**
     * Function used to create the about page
     * @param savedInstanceState saved instance state passed in by previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about);
        music= MediaPlayer.create(About.this,R.raw.about_music);
        music.setLooping(true);
        music.start();
    }

    /**
     * Decides what happens when the app is closed
     */
    @Override
    protected void onPause() {
        if (music.isPlaying()) {
            music.pause();
        }
        super.onPause();
    }

    /**
     * Decides what happens when the app is openned again.
     */
    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }
}
