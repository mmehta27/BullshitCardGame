package com.example.bullshitcardgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ImageView ace = this.findViewById(R.id.ace);
        TextView threeCardNumber = this.findViewById(R.id.threeNumber);
        ImageView two = this.findViewById(R.id.two);
        ImageView three = this.findViewById(R.id.three);
        threeCardNumber.setText("Number of Cards: 2");
        ace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany();
            }
        });
        threeCardNumber.setVisibility(View.INVISIBLE);
        three.setVisibility(View.INVISIBLE);
    }

    private void howMany() {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(GameActivity.this);
        View promptCardNumber = getLayoutInflater().inflate(R.layout.number_of_cards, null, false);
        buildDialog.setView(promptCardNumber);
        final AlertDialog cardNumberPrompt = buildDialog.create();
        cardNumberPrompt.show();
        final EditText numberCards = promptCardNumber.findViewById(R.id.inputCardNumber);
        Button cancelButton = promptCardNumber.findViewById(R.id.cancel);
        Button submitButton = promptCardNumber.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, numberCards.getText(), Toast.LENGTH_SHORT).show();

                cardNumberPrompt.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                cardNumberPrompt.dismiss();
            }
        });
    }
}
