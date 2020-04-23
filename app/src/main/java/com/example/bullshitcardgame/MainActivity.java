package com.example.bullshitcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        //Opens join server layout
        Button joinServer = MainActivity.this.findViewById(R.id.join_server);
        joinServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startJoinServerPage = new Intent(MainActivity.this, JoinServer.class);
                startActivity(startJoinServerPage);
            }
        });
        //Opens host game layout
        Button hostServer = MainActivity.this.findViewById(R.id.host_server);
        hostServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startHostServerPage = new Intent(MainActivity.this, HostServer.class);
                startActivity(startHostServerPage);
            }
        });
    }
}
