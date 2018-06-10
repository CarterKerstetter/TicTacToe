package carter.tictactoe;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Game extends AppCompatActivity implements Runnable{
    private Thread otherPlayer;
    private PlayerAI ai;
    private TextView o_notification;
    private TextView x_notification;
    private String difficulty;
    private Button b_new_game;
    private MediaPlayer music;
    private ImageView[] x_list = new ImageView[TicTacToeModel.BOARD_SPACES];
    private ImageView[] o_list = new ImageView[TicTacToeModel.BOARD_SPACES];
    private Button[] b_list = new Button[TicTacToeModel.BOARD_SPACES];
    private ImageView[] win_list = new ImageView[TicTacToeModel.WIN_PATHS];
    private final TicTacToeModel game = new TicTacToeModel();
    private Mark mark = Mark.BLANK;
    private boolean exit = false;
    public final String WIN_TEXT = "You win!";
    public final String LOSE_TEXT = "You lost!";
    public final String TIE_TEXT = "It is a tie!";

    /**
     * Creates page for the tic tac toe game to be played on.
     * @param savedInstanceState state passed in
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        difficulty = intent.getStringExtra("difficulty");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        music= MediaPlayer.create(Game.this,R.raw.in_game_music);
        music.setLooping(true);
        music.start();
        //set up the title
        this.setTitle("TicTacToe: " + difficulty);
        o_notification = findViewById(R.id.o_notification);
        x_notification = findViewById(R.id.x_notification);
        setUpMarkViews();
        setUpMainButtons();
        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

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

    private void newMark() {
        if(mark == Mark.X) {
            mark = Mark.O;
            o_notification.setVisibility(View.VISIBLE);
            x_notification.setVisibility(View.INVISIBLE);
        }
        else if (mark == Mark.O) {
            mark = Mark.X;
            x_notification.setVisibility(View.VISIBLE);
            o_notification.setVisibility(View.INVISIBLE);
        }
        else {
            Random random = new Random();
            int startMark = Math.abs(random.nextInt())%2;
            if(startMark == 0) {
                mark = Mark.X;
                x_notification.setVisibility(View.VISIBLE);
                o_notification.setVisibility(View.INVISIBLE);
            }
            else {
                mark = Mark.O;
                o_notification.setVisibility(View.VISIBLE);
                x_notification.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setAI() {
        switch (difficulty) {
            case "Easy":
                if(mark == Mark.X) {
                    ai = new PlayerAI(Difficulty.EASY, Mark.O, game);
                }
                else {
                    ai = new PlayerAI(Difficulty.EASY, Mark.X, game);
                }
                break;
            case "Medium":
                if(mark == Mark.X) {
                    ai = new PlayerAI(Difficulty.MEDIUM, Mark.O, game);
                }
                else {
                    ai = new PlayerAI(Difficulty.MEDIUM, Mark.X, game);
                }
                break;
            case "Hard":
                if(mark == Mark.X) {
                    ai = new PlayerAI(Difficulty.HARD, Mark.O, game);
                }
                else {
                    ai = new PlayerAI(Difficulty.HARD, Mark.X, game);
                }
                break;
            default:
                if(mark == Mark.X) {
                    ai = new PlayerAI(Difficulty.MEDIUM, Mark.O, game);
                }
                else {
                    ai = new PlayerAI(Difficulty.MEDIUM, Mark.X, game);
                }
        }
    }

    private void startNewGame() {
        for(ImageView line : win_list) {
            line.setVisibility(View.INVISIBLE);
        }
        newMark();
        setAI();
        game.newGame();
        Thread thisPlayer = new Thread(this);
        thisPlayer.start();
        otherPlayer = new Thread(ai);
        otherPlayer.start();
        updateBoard();
        b_new_game.setVisibility(View.INVISIBLE);
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

    public void run() {
        while(!exit && !game.gameCompleted()) {
            synchronized (game) {
                try {
                    game.wait();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateBoard();
                        }
                    });
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateBoard() {
        Mark[][] board = game.getBoard();
        for(int row=0;row<TicTacToeModel.BOARD_SIZE;row++) {
            for(int col=0;col<TicTacToeModel.BOARD_SIZE;col++) {
                switch(board[row][col]) {
                    case X:
                        x_list[row*3+col].setVisibility(View.VISIBLE);
                        o_list[row*3+col].setVisibility(View.INVISIBLE);
                        break;
                    case O:
                        x_list[row*3+col].setVisibility(View.INVISIBLE);
                        o_list[row*3+col].setVisibility(View.VISIBLE);
                        break;
                    case BLANK:
                        x_list[row*3+col].setVisibility(View.INVISIBLE);
                        o_list[row*3+col].setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }
        if(game.gameCompleted()) {
            Mark winner = game.getWinner();
            if(winner==mark) {
                Toast.makeText(getApplicationContext(), WIN_TEXT,
                        Toast.LENGTH_LONG).show();
            }
            else if(winner==Mark.BLANK) {
                Toast.makeText(getApplicationContext(), TIE_TEXT,
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), LOSE_TEXT,
                        Toast.LENGTH_LONG).show();
            }
            int winPath = game.getWinPath();
            if(winPath != TicTacToeModel.WIN_PATHS) {
                win_list[game.getWinPath()].setVisibility(View.VISIBLE);
            }
            b_new_game.setVisibility(View.VISIBLE);
        }
    }

    private void setUpMainButtons() {
        b_new_game =  findViewById(R.id.b_new_game);
        Button b_main_menu = findViewById(R.id.b_main_menu);
        b_main_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                exit = true;
                if(otherPlayer.getState()== Thread.State.WAITING) {
                    ai.stopRunning();
                    synchronized (game) {
                        game.notifyAll();
                    }
                }
                Intent myIntent = new Intent(view.getContext(), MainMenu.class);
                startActivityForResult(myIntent, 0);
            }
        });
        b_new_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startNewGame();
            }
        });
    }

    private void setUpMarkViews() {
        b_list[0] = findViewById(R.id.b_0);
        b_list[1] = findViewById(R.id.b_1);
        b_list[2] = findViewById(R.id.b_2);
        b_list[3] = findViewById(R.id.b_3);
        b_list[4] = findViewById(R.id.b_4);
        b_list[5] = findViewById(R.id.b_5);
        b_list[6] = findViewById(R.id.b_6);
        b_list[7] = findViewById(R.id.b_7);
        b_list[8] = findViewById(R.id.b_8);
        x_list[0] = findViewById(R.id.x_0);
        x_list[1] = findViewById(R.id.x_1);
        x_list[2] = findViewById(R.id.x_2);
        x_list[3] = findViewById(R.id.x_3);
        x_list[4] = findViewById(R.id.x_4);
        x_list[5] = findViewById(R.id.x_5);
        x_list[6] = findViewById(R.id.x_6);
        x_list[7] = findViewById(R.id.x_7);
        x_list[8] = findViewById(R.id.x_8);
        o_list[0] = findViewById(R.id.o_0);
        o_list[1] = findViewById(R.id.o_1);
        o_list[2] = findViewById(R.id.o_2);
        o_list[3] = findViewById(R.id.o_3);
        o_list[4] = findViewById(R.id.o_4);
        o_list[5] = findViewById(R.id.o_5);
        o_list[6] = findViewById(R.id.o_6);
        o_list[7] = findViewById(R.id.o_7);
        o_list[8] = findViewById(R.id.o_8);
        win_list[0] = findViewById(R.id.win_0);
        win_list[1] = findViewById(R.id.win_1);
        win_list[2] = findViewById(R.id.win_2);
        win_list[3] = findViewById(R.id.win_3);
        win_list[4] = findViewById(R.id.win_4);
        win_list[5] = findViewById(R.id.win_5);
        win_list[6] = findViewById(R.id.win_6);
        win_list[7] = findViewById(R.id.win_7);
        b_list[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_list[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
    }
}
