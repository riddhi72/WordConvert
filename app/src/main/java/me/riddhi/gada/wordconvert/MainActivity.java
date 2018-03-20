package me.riddhi.gada.wordconvert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button easy, difficult;
    int choice = 0;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easy = (Button)findViewById(R.id.easy);
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

    }
}
