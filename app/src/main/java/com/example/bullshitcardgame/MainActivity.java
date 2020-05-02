package com.example.bullshitcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Opens game description layout
        Button howToPlay = MainActivity.this.findViewById(R.id.description);
        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startDescriptionPage = new Intent(MainActivity.this, HowToPlay.class);
                startActivity(startDescriptionPage);
            }
        });
        //Opens game layout
        Button playGame = MainActivity.this.findViewById(R.id.play_game);
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGame);
            }
        });
    }
}
