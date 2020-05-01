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

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;


public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //declare socket connection variable
        private Socket mSocket;
        //Create ImageView variable and TextView variable for each card
        final ImageView ace = this.findViewById(R.id.ace);
        final TextView aceCardNumber = this.findViewById(R.id.aceNumber);
        final ImageView two = this.findViewById(R.id.two);
        final TextView twoCardNumber = this.findViewById(R.id.twoNumber);
        final ImageView three = this.findViewById(R.id.three);
        final TextView threeCardNumber = this.findViewById(R.id.threeNumber);
        final ImageView four = this.findViewById(R.id.four);
        final TextView fourCardNumber = this.findViewById(R.id.fourNumber);
        final ImageView five = this.findViewById(R.id.five);
        final TextView fiveCardNumber = this.findViewById(R.id.fiveNumber);
        final ImageView six = this.findViewById(R.id.six);
        final TextView sixCardNumber = this.findViewById(R.id.sixNumber);
        final ImageView seven = this.findViewById(R.id.seven);
        final TextView sevenCardNumber = this.findViewById(R.id.sevenNumber);
        final ImageView eight = this.findViewById(R.id.eight);
        final TextView eightCardNumber = this.findViewById(R.id.eightNumber);
        final ImageView nine = this.findViewById(R.id.nine);
        final TextView nineCardNumber = this.findViewById(R.id.nineNumber);
        final ImageView ten = this.findViewById(R.id.ten);
        final TextView tenCardNumber = this.findViewById(R.id.tenNumber);
        final ImageView jack = this.findViewById(R.id.jack);
        final TextView jackCardNumber = this.findViewById(R.id.jackNumber);
        final ImageView queen = this.findViewById(R.id.queen);
        final TextView queenCardNumber = this.findViewById(R.id.queenNumber);
        final ImageView king = this.findViewById(R.id.king);
        final TextView kingCardNumber = this.findViewById(R.id.kingNumber);
        //Test how to change text of cards
        setText(ace, aceCardNumber, 4);
        setText(two, twoCardNumber, 4);
        setText(three, threeCardNumber, 4);
        setText(four, fourCardNumber, 4);
        setText(five, fiveCardNumber, 4);
        setText(six, sixCardNumber, 4);
        setText(seven, sevenCardNumber, 4);
        setText(eight, eightCardNumber, 4);
        setText(nine, nineCardNumber, 4);
        setText(ten, tenCardNumber, 4);
        setText(jack, jackCardNumber, 4);
        setText(queen, queenCardNumber, 4);
        setText(king, kingCardNumber, 4);
        
        //connect socket to server
        mSocket = IO.socket( "http://3.19.228.205:420");
        
        //set socket event listeners
        mSocket.on("newPlayer", addPlayer);
        mSocket.on("ID", setID);
        mSocket.on("startGame", startGame);
        mSocket.on("decks", setDecks);
        mSocket.on("claim", updateGame);
        mSocket.on("callPlayer", shouldPlay);
        mSocket.on("bs", onTrueBS);
        mSocket.on("U Fd up M8", onFalseBS);
        
        //Made all cards clickable
        ace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(ace, aceCardNumber);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(two, twoCardNumber);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(three, threeCardNumber);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(four, fourCardNumber);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(five, fiveCardNumber);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(six, sixCardNumber);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(seven, sevenCardNumber);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(eight, eightCardNumber);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(nine, nineCardNumber);
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(ten, tenCardNumber);
            }
        });
        jack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(jack, jackCardNumber);
            }
        });
        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(queen, queenCardNumber);
            }
        });
        king.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(king, kingCardNumber);
            }
        });
    }

    private void howMany(final ImageView card, final TextView cardNumber) {
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
                int currentNumberCards = Integer.parseInt(cardNumber.getText().toString().substring(cardNumber.getText().toString().length() - 1));
                int input = Integer.parseInt(numberCards.getText().toString());
                if (currentNumberCards - input == 0) {
                    card.setVisibility(View.INVISIBLE);
                    cardNumber.setVisibility(View.INVISIBLE);
                } else if (currentNumberCards - input < 0) {
                    Toast.makeText(GameActivity.this, "Can't play that many cards!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GameActivity.this, numberCards.getText(), Toast.LENGTH_SHORT).show();
                    cardNumber.setText("Number of Cards: " + (currentNumberCards - input));
                }
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

    private void setText(final ImageView card, final TextView cardNumber, int numberOfCards) {
        if (numberOfCards == 0) {
            card.setVisibility(View.INVISIBLE);
            cardNumber.setVisibility(View.INVISIBLE);
        } else {
            cardNumber.setText("Number of Cards: " + numberOfCards);
        }
    }
    
    //Delineate responses to socket messages
    
     private Emitter.Listener addPlayer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int totalPlayers = (int) args[0];
            
            //Notify UI that a new player has joined the game, totalPlayers = num of player connected, game will start on 4
        }
    };
    
    private Emitter.Listener setID = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int playerID = (int) args[0];
            
            //@playerID - this local player's global ID
            //set local player ID to playerID, ranges on 0 - 3, used when updated server
        }
    };
    
    private Emitter.Listener startGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            
            //Notify UI that game is ready to start; four players have joined server
        }
    };
    
    private Emitter.Listener setDecks = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONArray decks = (JSONArray) args[0];
            
            //@decks - 2d (4 by 13, respectivly), JSONArray type (the one we did NOT use in class). First dimension consists of 4 length 13 JSONArrays
            // ordered by the server - identified playerID in the prior listeners. So the first length 13 array belongs to the player
            // with playerID 0. These represent that player's cards, with each element bearing a String type value of form "num" + "letter".
            // As an example, three of spades is "3s", Ace of heatrs is "1h", and king of diamonds is "13d". clubs maps to "c".
            
            //use this info to update UI
            
        }
    };
    
    private Emitter.Listener updateGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String claim = (String) args[0];
            int IDOfClaim = (int) args[1];
            
            //@claim - String of form "num" + "num", representing move made by last player. Ex: Given player exclaimed "2 Queens",
            // claim = "212". Similarly, 4 Aces would be "41"
            
            //@IDOfClaim - player ID of subject who played, ignore if ID matches local ID as player already trivially knows his play
            
            //use this info to notify player of change
        }
    };
    
    private Emitter.Listener shouldPlay = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int IDtoPlay = (int) args[0];
            //@IDtoPlayer- ID of next player to play; if such matches local ID, notify player to cast cards and play
        }
    };
    
    private Emitter.Listener onTrueBS = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int unluckyPlayer = (int) args[0];
            JSONArray cardsToRecieve = (JSONArray) args[1];
            int callerID = (int) args[2];
            //@unluckyPlayer - player to get deck of cards resultant to call
            //@carsToRecieve - JSONArray of card IDs to be taken by unluckyPlayer. Ex: ["1d". "6s", "11d"] for a stack of 
            // Ace of diamonds, six of spades and jack of diamonds
            //@callerID - player ID of subject who got justly called on BS
            
            //use info to update UI gamestate, notify the player that BS call was successful
        }
    };
    
    private Emitter.Listener onFalseBS = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONArray cardsToReceive = (int) args[0];
            int callerID = (int) args[1];
            // both params the same as above
            
            //use this info to return quantity of cards to player who flsely called BS (callerID)
        }
    };
    
}
