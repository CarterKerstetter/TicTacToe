/**
 * author: Carter Kerstetter
 */

package carter.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Game extends AppCompatActivity implements Runnable{
    TicTacToeModel game;
    Thread otherPlayer;
    PlayerAI ai;
    Mark mark;
    int i;
    TextView o_notification;
    TextView x_notification;
    String difficulty;

    ImageView[] x_list;
    ImageView x_0;
    ImageView x_1;
    ImageView x_2;
    ImageView x_3;
    ImageView x_4;
    ImageView x_5;
    ImageView x_6;
    ImageView x_7;
    ImageView x_8;

    ImageView[] o_list;
    ImageView o_0;
    ImageView o_1;
    ImageView o_2;
    ImageView o_3;
    ImageView o_4;
    ImageView o_5;
    ImageView o_6;
    ImageView o_7;
    ImageView o_8;

    Button[] b_list;
    Button b_0;
    Button b_1;
    Button b_2;
    Button b_3;
    Button b_4;
    Button b_5;
    Button b_6;
    Button b_7;
    Button b_8;

    Button b_new_game;
    Button b_main_menu;

    View h_1;
    View h_2;
    View v_1;
    View v_2;

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        difficulty = intent.getStringExtra("difficulty");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);
        music= MediaPlayer.create(Game.this,R.raw.in_game_music);
        music.setLooping(true);
        music.start();
        //set up the title
        this.setTitle("TicTacToe: " + difficulty);
        h_1 = (View) findViewById(R.id.horizontal1);
        h_2 = (View) findViewById(R.id.horizontal2);
        v_1 = (View) findViewById(R.id.vertical1);;
        v_2 = (View) findViewById(R.id.vertical2);
        h_1.bringToFront();
        h_2.bringToFront();
        v_1.bringToFront();
        v_2.bringToFront();
        setUpImageViews();
        mark = Mark.BLANK;
        game = new TicTacToeModel();
        startNewGame();
    }

    private void startNewGame() {
        if(mark == Mark.X) {
            mark = Mark.O;
            o_notification.setVisibility(View.VISIBLE);
            x_notification.setVisibility(View.INVISIBLE);
        }
        else if (mark == Mark.O){
            mark = Mark.X;
            x_notification.setVisibility(View.VISIBLE);
            o_notification.setVisibility(View.INVISIBLE);
        }
        else {
            Random random = new Random();
            int startMark = Math.abs(random.nextInt())%2;
            o_notification = (TextView) findViewById(R.id.o_notification);
            x_notification = (TextView) findViewById(R.id.x_notification);
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
        while(!game.gameCompleted()) {
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
                    System.out.println(e);
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
                        x_list[row*3+col].setClickable(false);
                        break;
                    case O:
                        x_list[row*3+col].setVisibility(View.INVISIBLE);
                        o_list[row*3+col].setVisibility(View.VISIBLE);
                        x_list[row*3+col].setClickable(false);
                        break;
                    case BLANK:
                        x_list[row*3+col].setVisibility(View.INVISIBLE);
                        o_list[row*3+col].setVisibility(View.INVISIBLE);
                        x_list[row*3+col].setClickable(true);
                        break;
                }
            }
        }
        if(game.gameCompleted()) {
            Mark winner = game.getWinner();
            if(winner==mark) {
                Toast.makeText(getApplicationContext(), "You win!",
                        Toast.LENGTH_LONG).show();
            }
            else if(winner==Mark.BLANK) {
                Toast.makeText(getApplicationContext(), "It is a tie!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "You lost!",
                        Toast.LENGTH_LONG).show();
            }
            b_new_game.setVisibility(View.VISIBLE);
        }
    }

    private void setUpImageViews() {
        b_0 = (Button) findViewById(R.id.b_0);
        b_1 = (Button) findViewById(R.id.b_1);
        b_2 = (Button) findViewById(R.id.b_2);
        b_3 = (Button) findViewById(R.id.b_3);
        b_4 = (Button) findViewById(R.id.b_4);
        b_5 = (Button) findViewById(R.id.b_5);
        b_6 = (Button) findViewById(R.id.b_6);
        b_7 = (Button) findViewById(R.id.b_7);
        b_8 = (Button) findViewById(R.id.b_8);
        b_list = new Button[9];
        b_list[0] = b_0;
        b_list[1] = b_1;
        b_list[2] = b_2;
        b_list[3] = b_3;
        b_list[4] = b_4;
        b_list[5] = b_5;
        b_list[6] = b_6;
        b_list[7] = b_7;
        b_list[8] = b_8;

        x_0 = (ImageView) findViewById(R.id.x_0);
        x_1 = (ImageView) findViewById(R.id.x_1);
        x_2 = (ImageView) findViewById(R.id.x_2);
        x_3 = (ImageView) findViewById(R.id.x_3);
        x_4 = (ImageView) findViewById(R.id.x_4);
        x_5 = (ImageView) findViewById(R.id.x_5);
        x_6 = (ImageView) findViewById(R.id.x_6);
        x_7 = (ImageView) findViewById(R.id.x_7);
        x_8 = (ImageView) findViewById(R.id.x_8);
        x_list = new ImageView[9];
        x_list[0] = x_0;
        x_list[1] = x_1;
        x_list[2] = x_2;
        x_list[3] = x_3;
        x_list[4] = x_4;
        x_list[5] = x_5;
        x_list[6] = x_6;
        x_list[7] = x_7;
        x_list[8] = x_8;

        o_0 = (ImageView) findViewById(R.id.o_0);
        o_1 = (ImageView) findViewById(R.id.o_1);
        o_2 = (ImageView) findViewById(R.id.o_2);
        o_3 = (ImageView) findViewById(R.id.o_3);
        o_4 = (ImageView) findViewById(R.id.o_4);
        o_5 = (ImageView) findViewById(R.id.o_5);
        o_6 = (ImageView) findViewById(R.id.o_6);
        o_7 = (ImageView) findViewById(R.id.o_7);
        o_8 = (ImageView) findViewById(R.id.o_8);
        o_list = new ImageView[9];
        o_list[0] = o_0;
        o_list[1] = o_1;
        o_list[2] = o_2;
        o_list[3] = o_3;
        o_list[4] = o_4;
        o_list[5] = o_5;
        o_list[6] = o_6;
        o_list[7] = o_7;
        o_list[8] = o_8;

        b_main_menu = (Button) findViewById(R.id.b_main_menu);
        b_new_game = (Button) findViewById(R.id.b_new_game);

        b_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 0;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 1;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 0;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 1;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int row = 2;
                int col = 2;
                Coordinates coordinates = new Coordinates(row, col);
                Move move = new Move(coordinates,mark);
                game.makeMove(move);
            }
        });
        b_main_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ai.stopRunning();
                if(otherPlayer.getState()== Thread.State.WAITING) {
                    synchronized (game) {
                        game.notifyAll();
                    }
                }
                //game.notifyAll();
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
}
