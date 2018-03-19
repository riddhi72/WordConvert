package me.riddhi.gada.wordconvert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button beginner, amateur, pro;
    int choice = 0;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beginner = (Button)findViewById(R.id.beginner);
        amateur = (Button)findViewById(R.id.amateur);
        pro = (Button)findViewById(R.id.legend);
        i = new Intent(this, GameActivity.class);

        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = 1;
                i.putExtra("choice", choice);
                startActivity(i);
            }
        });

        amateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice=2;
                i.putExtra("choice", choice);
                startActivity(i);
            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice=3;
                i.putExtra("choice", choice);
                startActivity(i);
            }
        });
    }
}
