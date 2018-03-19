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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    String user_input;
    int difficulty;
    TextView level;
    ArrayList<String> words = new ArrayList<>();
    HashMap<String, String> pairs = new HashMap<>();
    TextView source, destination, previous;
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
        reset_game = (Button)findViewById(R.id.reset);
        check_word = (Button)findViewById(R.id.check);
        user_word = (EditText)findViewById(R.id.input);
        previous  =(TextView)findViewById(R.id.prev);

        check_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous.setText("Hello");
            }
        });

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

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("choice", 0);
        level = (TextView)findViewById(R.id.level);
        if(difficulty==1)
            level.setText("Beginner");
        else if(difficulty==2)
            level.setText("Amateur");
        else
            level.setText("Legend");

        reset_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
            }
        });

        gameStart();
    }

    public void gameStart() {

        ArrayList<String> k = new ArrayList<String>(pairs.keySet());
        String original = k.get(random.nextInt(k.size()));
        String changed = pairs.get(original);
        source.setText(original);
        destination.setText(changed);

        user_input = user_word.getText().toString();

    }
}
