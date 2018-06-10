package carter.tictactoe;
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

/**
 * Opens the main menu of the app
 */
public class MainMenu extends AppCompatActivity {
    private MediaPlayer music;

    /**
     * Creates the main page
     * @param savedInstanceState state passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        music= MediaPlayer.create(MainMenu.this,R.raw.background_music);
        music.setLooping(true);
        music.start();
        Button next = findViewById(R.id.start);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Game.class);
                SeekBar level = findViewById(R.id.difficulty);
                switch(level.getProgress()) {
                    case 0:
                        myIntent.putExtra("difficulty","Easy");
                        break;
                    case 1:
                        myIntent.putExtra("difficulty","Medium");
                        break;
                    case 2:
                        myIntent.putExtra("difficulty","Hard");
                        break;
                }
                startActivityForResult(myIntent, 0);
            }

        });
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
     * Decides what happens when the app is opened again.
     */
    @Override
    protected void onResume() {
        super.onResume();
        music.start();
    }

    /**
     * Creates the toolbar that leads to the about page
     * @param menu toolbar
     * @return true when everything works correctly
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    /**
     * Decides what happens when an option is selected on the toolbar (opens about page)
     * @param item contains the id of the element tapped
     * @return true when finished
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent myIntent = new Intent(getBaseContext(),About.class);
            startActivityForResult(myIntent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
