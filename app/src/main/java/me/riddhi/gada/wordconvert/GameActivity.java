package me.riddhi.gada.wordconvert;

import android.content.Intent;
import android.content.res.AssetManager;
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

    int difficulty,turn=1;
    ArrayList<String> words = new ArrayList<>();
    HashMap<String, String> pairs = new HashMap<>();
    TextView source, destination, previous, countdown;
    Random random = new Random();
    Button reset_game, check_word;
    EditText user_word;

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
        user_word = (EditText)findViewById(R.id.input);
        previous  =(TextView)findViewById(R.id.prev);

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

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("choice", 0);
        if(difficulty==1)
            countdown.setText("");
        else
            countdown.setText("Time Starts in 3s");

        reset_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_word.setEnabled(true);
                user_word.setFocusable(true);
                gameStart();
            }
        });

        gameStart();
    }

    public void gameStart() {

        user_word.setText("");
        previous.setText("");
        turn=1;
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
        if (entered == "")
            Toast.makeText(this, "No input given", Toast.LENGTH_SHORT).show();
        else
            if(isWord(entered))
            {
                if (turn == 1)
                {
                    if (letterChange(entered,source.getText().toString()))
                        previous.setText("");
                    else
                        Toast.makeText(this, "You can change only 1 letter!", Toast.LENGTH_SHORT).show();
                    turn = 0;
                }
                else
                {
                    if (letterChange(entered, previous.getText().toString()))
                        previous.setText(entered);
                    else
                        Toast.makeText(this, "You can change only 1 letter!", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(this, "Not a valid word", Toast.LENGTH_SHORT).show();
        if(previous.getText().toString().equalsIgnoreCase(destination.getText().toString()))
        {
            Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show();
            check_word.setEnabled(false);
            user_word.setFocusable(false);
        }
        user_word.setText("");
    }
}
