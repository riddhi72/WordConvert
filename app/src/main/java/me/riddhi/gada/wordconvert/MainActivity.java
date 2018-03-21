package me.riddhi.gada.wordconvert;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button easy, difficult, instructions;
    AlertDialog.Builder builder;
    final String inst = "The aim of the game is to convert the given source word into the destination word given beside it, changing one letter at a time. At the EASY level you are given 15 turns to change the word, while at HARD difficulty level, there is a time constraint too.";

    int choice = 0;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        easy = (Button)findViewById(R.id.easy);
        instructions = (Button)findViewById(R.id.help);
        difficult = (Button)findViewById(R.id.difficult);
        i = new Intent(this, GameActivity.class);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 1;
                i.putExtra("choice", choice);
                startActivity(i);
            }
        });

        difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice=2;
                i.putExtra("choice", choice);
                startActivity(i);
            }
        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("test","in button");
                builder.setMessage(inst).setTitle("Instructions");
                builder.show();
            }
        });

    }
}
