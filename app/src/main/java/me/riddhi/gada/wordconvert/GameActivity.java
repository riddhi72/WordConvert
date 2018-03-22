package me.riddhi.gada.wordconvert;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity {

    int difficulty,turn;
    ArrayList<String> words = new ArrayList<>();
    HashMap<String, String> pairs = new HashMap<>();
    TextView source, destination, previous, countdown, noOfTurns;
    Random random = new Random();
    Button reset_game, check_word, go_back;
    EditText user_word;
    CountDownTimer myTimer;
    final long defaultTime = 60000;
    long timeLeft = defaultTime;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AssetManager assetManager = getAssets();
        source = (TextView)findViewById(R.id.source);
        destination = (TextView)findViewById(R.id.dest);
        countdown = (TextView) findViewById(R.id.countdown);
        reset_game = (Button)findViewById(R.id.reset);
        check_word = (Button)findViewById(R.id.check);
        go_back = (Button)findViewById(R.id.back);
        user_word = (EditText)findViewById(R.id.input);
        previous  =(TextView)findViewById(R.id.prev);
        noOfTurns  =(TextView)findViewById(R.id.turns);
        turn=0;

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("choice", 0);

        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null)
            {
                String word = line.trim();
                words.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = assetManager.open("pairs.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null)
            {
                String word_pair[] = line.split(" ");
                pairs.put(word_pair[0], word_pair[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        check_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerCheck();
            }
        });

        reset_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_word.setEnabled(true);
                turn=0;
                if(difficulty==2)
                    myTimer.cancel();
                gameStart();
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gameStart();
    }

    public void gameStart() {
        if (difficulty == 2)
        {
            timeLeft = defaultTime;
            startTimer();
        }
        turn++;
        noOfTurns.setText("Turn: " + Integer.toString(turn));
        user_word.setEnabled(true);
        user_word.setText("");
        previous.setText("");
        ArrayList<String> k = new ArrayList<String>(pairs.keySet());
        String original = k.get(random.nextInt(k.size()));
        String changed = pairs.get(original);
        source.setText(original);
        destination.setText(changed);
    }

    public boolean isWord(String word)
    {
        if(words.contains(word))
            return true;
        return false;
    }

    public boolean letterChange(String a, String b)
    {
        int i,count=0;
        if(a.length()!=b.length())
            return false;
        for(i=0;i<a.length();i++){
            if(a.charAt(i) != b.charAt(i))
                count++;
        }
        if(count==1)
            return true;
        else
            return false;
    }

    public void playerCheck()
    {
        String entered = user_word.getText().toString();
        //Log.d("Turn: ", Integer.toString(turn));
        if(isWord(entered)) {
            if (!letterChange(entered, previous.getText().toString()))
            {
                if (turn == 1)
                {
                    previous.setText(entered);
                    checkWin();
                }
                else
                    Toast.makeText(this, "You can change only 1 letter at a time", Toast.LENGTH_SHORT).show();
            }
            else {
                previous.setText(entered);
                checkWin();
            }
        }
        else
            Toast.makeText(this, "Not a valid word", Toast.LENGTH_SHORT).show();
        if(turn==15)
        {
            Toast.makeText(this, "YOU RAN OUT OF MOVES!", Toast.LENGTH_SHORT).show();
            check_word.setEnabled(false);
            user_word.setEnabled(false);
        }
        user_word.setText("");
        noOfTurns.setText("Turn: " + Integer.toString(++turn));
    }

    void startTimer(){
        myTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                int mins = (int) timeLeft/1000/60 ;
                int secs = (int) (timeLeft/1000) %60 ;
                String timeFormat = String.format("%02d:%02d",mins,secs);
                countdown.setText(timeFormat);
            }

            @Override
            public void onFinish() {
                countdown.setText("You Lose!");
                Toast.makeText(GameActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                check_word.setEnabled(false);
            }
        }.start();
    }

    public void checkWin()
    {
        if(previous.getText().toString().equalsIgnoreCase(destination.getText().toString()))
        {
            check_word.setEnabled(false);
            user_word.setEnabled(false);
            if(difficulty==2)
                myTimer.cancel();
            Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }
}