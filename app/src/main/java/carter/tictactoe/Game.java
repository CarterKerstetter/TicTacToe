package carter.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity {
    //TicTacToeModel game;
    Mark mark;
    int i;
    TextView o_notification;
    TextView x_notification;

    ImageView x_0;
    ImageView x_1;
    ImageView x_2;
    ImageView x_3;
    ImageView x_4;
    ImageView x_5;
    ImageView x_6;
    ImageView x_7;
    ImageView x_8;

    ImageView o_0;
    ImageView o_1;
    ImageView o_2;
    ImageView o_3;
    ImageView o_4;
    ImageView o_5;
    ImageView o_6;
    ImageView o_7;
    ImageView o_8;

    Button b_0;
    Button b_1;
    Button b_2;
    Button b_3;
    Button b_4;
    Button b_5;
    Button b_6;
    Button b_7;
    Button b_8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");
        setContentView(R.layout.activity_game);
        this.setTitle("TicTacToe: " + difficulty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Random random = new Random();
        int startMark = Math.abs(random.nextInt())%2;
        o_notification = (TextView) findViewById(R.id.o_notification);
        x_notification = (TextView) findViewById(R.id.x_notification);
        if(startMark == 0) {
            mark = Mark.X;
            x_notification.setVisibility(View.VISIBLE);
        }
        else {
            mark = Mark.O;
            o_notification.setVisibility(View.VISIBLE);
        }
        /**
        game = new TicTacToeModel();
        PlayerAI ai;
        switch (difficulty) {
            case "Easy":
                ai = new PlayerAI(Difficulty.EASY,mark,game);
                break;
            case "Medium":
                ai = new PlayerAI(Difficulty.MEDIUM,mark,game);
                break;
            case "Hard":
                ai = new PlayerAI(Difficulty.HARD,mark,game);
                break;
            default:
                ai = new PlayerAI(Difficulty.MEDIUM,mark,game);
        }
        Thread otherPlayer = new Thread(ai);
        otherPlayer.start();

         **/

        b_0 = (Button) findViewById(R.id.b_0);
        b_1 = (Button) findViewById(R.id.b_1);
        b_2 = (Button) findViewById(R.id.b_2);
        b_3 = (Button) findViewById(R.id.b_3);
        b_4 = (Button) findViewById(R.id.b_4);
        b_5 = (Button) findViewById(R.id.b_5);
        b_6 = (Button) findViewById(R.id.b_6);
        b_7 = (Button) findViewById(R.id.b_7);
        b_8 = (Button) findViewById(R.id.b_8);


        x_0 = (ImageView) findViewById(R.id.x_0);
        x_1 = (ImageView) findViewById(R.id.x_1);
        x_2 = (ImageView) findViewById(R.id.x_2);
        x_3 = (ImageView) findViewById(R.id.x_3);
        x_4 = (ImageView) findViewById(R.id.x_4);
        x_5 = (ImageView) findViewById(R.id.x_5);
        x_6 = (ImageView) findViewById(R.id.x_6);
        x_7 = (ImageView) findViewById(R.id.x_7);
        x_8 = (ImageView) findViewById(R.id.x_8);

        o_0 = (ImageView) findViewById(R.id.o_0);
        o_1 = (ImageView) findViewById(R.id.o_1);
        o_2 = (ImageView) findViewById(R.id.o_2);
        o_3 = (ImageView) findViewById(R.id.o_3);
        o_4 = (ImageView) findViewById(R.id.o_4);
        o_5 = (ImageView) findViewById(R.id.o_5);
        o_6 = (ImageView) findViewById(R.id.o_6);
        o_7 = (ImageView) findViewById(R.id.o_7);
        o_8 = (ImageView) findViewById(R.id.o_8);

        b_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_0.setVisibility(View.VISIBLE);
                }
                else {
                    o_0.setVisibility(View.VISIBLE);
                }
            }
        });

        b_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_1.setVisibility(View.VISIBLE);
                }
                else {
                    o_1.setVisibility(View.VISIBLE);
                }
            }
        });

        b_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_2.setVisibility(View.VISIBLE);
                }
                else {
                    o_2.setVisibility(View.VISIBLE);
                }
            }
        });

        b_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_3.setVisibility(View.VISIBLE);
                }
                else {
                    o_3.setVisibility(View.VISIBLE);
                }
            }
        });

        b_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_4.setVisibility(View.VISIBLE);
                }
                else {
                    o_4.setVisibility(View.VISIBLE);
                }
            }
        });

        b_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_5.setVisibility(View.VISIBLE);
                }
                else {
                    o_5.setVisibility(View.VISIBLE);
                }
            }
        });

        b_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_6.setVisibility(View.VISIBLE);
                }
                else {
                    o_6.setVisibility(View.VISIBLE);
                }
            }
        });

        b_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_7.setVisibility(View.VISIBLE);
                }
                else {
                    o_7.setVisibility(View.VISIBLE);
                }
            }
        });

        b_8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mark == Mark.X) {
                    x_8.setVisibility(View.VISIBLE);
                }
                else {
                    o_8.setVisibility(View.VISIBLE);
                }
            }
        });


        /**
        for(i=0;i<9;i++) {
            x_list[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    x_list[i].setVisibility(View.VISIBLE);
                }
            });
            o_list[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    o_list[i].setVisibility(View.VISIBLE);
                }
            });

        }
         **/
        //setUpImageViews();


    }


    private void setUpImageViews() {
        return;
    }
}
