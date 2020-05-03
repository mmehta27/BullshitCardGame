package com.example.bullshitcardgame;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {
    //declare socket connection variable
    private Socket mSocket;
    private Vibrator vibrate;
    private ImageView ace;
    private TextView aceCardNumber;
    private ImageView two;
    private TextView twoCardNumber;
    private ImageView three;
    private TextView threeCardNumber;
    private ImageView four;
    private TextView fourCardNumber;
    private ImageView five;
    private TextView fiveCardNumber;
    private ImageView six;
    private TextView sixCardNumber;
    private ImageView seven;
    private TextView sevenCardNumber;
    private ImageView eight;
    private TextView eightCardNumber;
    private ImageView nine;
    private TextView nineCardNumber;
    private ImageView ten;
    private TextView tenCardNumber;
    private ImageView jack;
    private TextView jackCardNumber;
    private ImageView queen;
    private TextView queenCardNumber;
    private ImageView king;
    private TextView kingCardNumber;
    private TextView gameLog;
    private Button submitCards;
    private ImageView callBS;
    private TextView playerTurn;
    private List<Integer> cardsToSubmit = new ArrayList<>();
    private int playerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Create ImageView variable and TextView variable for each card
        ace = this.findViewById(R.id.ace);
        aceCardNumber = this.findViewById(R.id.aceNumber);
        two = this.findViewById(R.id.two);
        twoCardNumber = this.findViewById(R.id.twoNumber);
        three = this.findViewById(R.id.three);
        threeCardNumber = this.findViewById(R.id.threeNumber);
        four = this.findViewById(R.id.four);
        fourCardNumber = this.findViewById(R.id.fourNumber);
        five = this.findViewById(R.id.five);
        fiveCardNumber = this.findViewById(R.id.fiveNumber);
        six = this.findViewById(R.id.six);
        sixCardNumber = this.findViewById(R.id.sixNumber);
        seven = this.findViewById(R.id.seven);
        sevenCardNumber = this.findViewById(R.id.sevenNumber);
        eight = this.findViewById(R.id.eight);
        eightCardNumber = this.findViewById(R.id.eightNumber);
        nine = this.findViewById(R.id.nine);
        nineCardNumber = this.findViewById(R.id.nineNumber);
        ten = this.findViewById(R.id.ten);
        tenCardNumber = this.findViewById(R.id.tenNumber);
        jack = this.findViewById(R.id.jack);
        jackCardNumber = this.findViewById(R.id.jackNumber);
        queen = this.findViewById(R.id.queen);
        queenCardNumber = this.findViewById(R.id.queenNumber);
        king = this.findViewById(R.id.king);
        kingCardNumber = this.findViewById(R.id.kingNumber);

        //Set number of cards
        setText(ace, aceCardNumber, 0);
        setText(two, twoCardNumber, 0);
        setText(three, threeCardNumber, 0);
        setText(four, fourCardNumber, 0);
        setText(five, fiveCardNumber, 0);
        setText(six, sixCardNumber, 0);
        setText(seven, sevenCardNumber, 0);
        setText(eight, eightCardNumber, 0);
        setText(nine, nineCardNumber, 0);
        setText(ten, tenCardNumber, 0);
        setText(jack, jackCardNumber, 0);
        setText(queen, queenCardNumber, 0);
        setText(king, kingCardNumber, 0);

        //Make gameLog scrollable
        gameLog = this.findViewById(R.id.gameLog);
        gameLog.setMovementMethod(new ScrollingMovementMethod());
        gameLog.setText("");

        //Set up vibrating feature
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Call BS button
        callBS = GameActivity.this.findViewById(R.id.deckCards);

        //Create submit cards button and playerTurns
        submitCards = this.findViewById(R.id.submitCards);
        submitCards.setVisibility(View.INVISIBLE);
        playerTurn = this.findViewById(R.id.playerTurn);
        playerTurn.setText("Player null turn");
        //connect socket to server
        try {
            mSocket = IO.socket("http://3.19.228.205:4200");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

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
                howMany(ace, aceCardNumber, 1);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(two, twoCardNumber, 2);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(three, threeCardNumber, 2);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(four, fourCardNumber, 4);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(five, fiveCardNumber, 5);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(six, sixCardNumber, 6);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(seven, sevenCardNumber, 7);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(eight, eightCardNumber, 8);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(nine, nineCardNumber, 9);
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(ten, tenCardNumber, 10);
            }
        });
        jack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(jack, jackCardNumber, 11);
            }
        });
        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(queen, queenCardNumber, 12);
            }
        });
        king.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howMany(king, kingCardNumber, 13);
            }
        });
        callBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call server to check BS
                mSocket.emit("bs");
                Toast.makeText(GameActivity.this, "BS!", Toast.LENGTH_SHORT).show();
            }
        });
        submitCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call server to show cards user played. --> send cardsToSubmit List
                mSocket.emit("update", cardsToSubmit);
            }
        });
    }

    private void howMany(final ImageView card, final TextView cardNumber, final int cardType) {
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
                //String cardsSubmit = numberCards.getText().toString() + cardType;
                int currentNumberCards = Integer.parseInt(cardNumber.getText().toString().substring(cardNumber.getText().toString().length() - 1));
                int input = Integer.parseInt(numberCards.getText().toString());
                if (currentNumberCards - input == 0) {
                    //Toast.makeText(GameActivity.this, cardsSubmit, Toast.LENGTH_SHORT).show();
                    card.setVisibility(View.INVISIBLE);
                    cardNumber.setVisibility(View.INVISIBLE);
                    //cardsToSubmit.add(cardsSubmit);
                    for (int index = 0; index < input; index++) {
                        cardsToSubmit.add(cardType);
                    }
                } else if (currentNumberCards - input < 0) {
                    Toast.makeText(GameActivity.this, "Can't play that many cards!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(GameActivity.this, cardsSubmit, Toast.LENGTH_SHORT).show();
                    cardNumber.setText("Number of Cards: " + (currentNumberCards - input));
                    //cardsToSubmit.add(cardsSubmit);
                    for (int index = 0; index < input; index++) {
                        cardsToSubmit.add(cardType);
                    }
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

    private int numberOfCards(final TextView cardNumber) {
        if (cardNumber.getVisibility() == View.VISIBLE) {
            int currentNumberCards = Integer.parseInt(cardNumber.getText().toString().substring(cardNumber.getText().toString().length() - 2));
            Toast.makeText(GameActivity.this, "Number of Cards: " + currentNumberCards, Toast.LENGTH_LONG).show();
            return currentNumberCards;
        } else {
            return 0;
        }
    }

    private void setText(final ImageView card, final TextView cardNumber, final int numberOfCards) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (numberOfCards <= 0) {
                    card.setVisibility(View.INVISIBLE);
                    //cardNumber.setText("Number of Cards: " + 0);
                    cardNumber.setVisibility(View.INVISIBLE);
                } else {
                    int currentNumberCards = 0;
                    if (card.getVisibility() == View.INVISIBLE) {
                        currentNumberCards = numberOfCards;
                    } else {
                        currentNumberCards = Integer.parseInt(cardNumber.getText().toString().substring(cardNumber.getText().toString().length() - 1)) + numberOfCards;
                    }
                    card.setVisibility(View.VISIBLE);
                    cardNumber.setVisibility(View.VISIBLE);
                    cardNumber.setText("Number of Cards: " + currentNumberCards);
                }
            }
        });
    }

    private void updateGameLogGame(final int id, final boolean plural, final String cardNumber, final String card) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (plural == true) {
                    gameLog.append("Player " + id + " played: " + cardNumber + " " + card + "'s !\n");
                } else {
                    gameLog.append("Player " + id + " played: " + cardNumber + " " + card + "!\n");
                }
            }
        });
    }
    private void updatePlayerTurn(final boolean whoseTurn, final int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (whoseTurn == true) {
                    playerTurn.setText("Player " + playerID + "'s turn!");
                    submitCards.setVisibility(View.VISIBLE);
                    vibrate.vibrate(400);
                } else {
                    playerTurn.setText("Player " + id + "'s turn!");
                    submitCards.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    //Delineate responses to socket messages
    
     private Emitter.Listener addPlayer = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int totalPlayers = (int) args[0];
            gameLog.append("A new player has joined!\n");
            //Notify UI that a new player has joined the game, totalPlayers = num of player connected, game will start on 4
        }
    };
    
    private Emitter.Listener setID = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            playerID = (int) args[0];
            gameLog.append("You are Player " + playerID + "\n");
            //@playerID - this local player's global ID
            //set local player ID to playerID, ranges on 0 - 3, used when updated server
        }
    };
    
    private Emitter.Listener startGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            gameLog.append("The game will start!\n");
            vibrate.vibrate(400);
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
            // As an example, three of spades is "3s", Ace of hearts is "1h", and king of diamonds is "13d". clubs maps to "c".
            //use this info to update UI
            try {
                JSONArray playersDeck = decks.getJSONArray(playerID);
                int length = playersDeck.length();
                for (int index = 0; index < playersDeck.length(); index++) {
                    String cardName = playersDeck.get(index).toString();
                    int cardNumber = Integer.parseInt(cardName);
                    switch (cardNumber) {
                        case 1:
                            setText(ace, aceCardNumber, 1);
                            break;
                        case 2:
                            setText(two, twoCardNumber, 1);
                            break;
                        case 3:
                            setText(three, threeCardNumber, 1);
                            break;
                        case 4:
                            setText(four, fourCardNumber, 1);
                            break;
                        case 5:
                            setText(five, fiveCardNumber, 1);
                            break;
                        case 6:
                            setText(six, sixCardNumber, 1);
                            break;
                        case 7:
                            setText(seven, sevenCardNumber, 1);
                            break;
                        case 8:
                            setText(eight, eightCardNumber, 1);
                            break;
                        case 9:
                            setText(nine, nineCardNumber, 1);
                            break;
                        case 10:
                            setText(ten, tenCardNumber, 1);
                            break;
                        case 11:
                            setText(jack, jackCardNumber, 1);
                            break;
                        case 12:
                            setText(queen, queenCardNumber, 1);
                            break;
                        case 13:
                            setText(king, kingCardNumber, 1);
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    
    private Emitter.Listener updateGame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String claim = (String) args[0];
            int IDOfClaim = (int) args[1];
            //gameLog.append("Player " + IDOfClaim + " played: " + claim.substring(0, 1) + " " + 1 + "!\n");
            //@claim - String of form "num" + "num", representing move made by last player. Ex: Given player exclaimed "2 Queens",
            // claim = "212". Similarly, 4 Aces would be "41"
            //use this info to notify player of change
            try {
                String cardType = claim.substring(1);
                switch (cardType) {
                    case "1":
                        cardType = "Ace";
                        break;
                    case "2":
                        cardType = "Two";
                        break;
                    case "3":
                        cardType = "Three";
                        break;
                    case "4":
                        cardType = "Four";
                        break;
                    case "5":
                        cardType = "Five";
                        break;
                    case "6":
                        cardType = "Six";
                        break;
                    case "7":
                        cardType = "Seven";
                        break;
                    case "8":
                        cardType = "Eight";
                        break;
                    case "9":
                        cardType = "Nine";
                        break;
                    case "10":
                        cardType = "Ten";
                        break;
                    case "11":
                        cardType = "Jack";
                        break;
                    case "12":
                        cardType = "Queen";
                        break;
                    case "13":
                        cardType = "King";
                        break;
                    default:
                        cardType = "Card Not Found";
                }
                if (Integer.parseInt(claim.substring(0, 1)) <= 1) {
                    updateGameLogGame(IDOfClaim, false, claim.substring(0, 1), cardType);
                } else {
                    updateGameLogGame(IDOfClaim, false, claim.substring(0, 1), cardType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    private Emitter.Listener shouldPlay = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int IDtoPlay = (int) args[0];
            //@IDtoPlayer- ID of next player to play; if such matches local ID, notify player to cast cards and play
            try {
                if (IDtoPlay == playerID) {
                    updatePlayerTurn(true, playerID);
                } else {
                    updatePlayerTurn(false, IDtoPlay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    private Emitter.Listener onTrueBS = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            int unluckyPlayer = (int) args[0];
            JSONArray cardsToReceive = (JSONArray) args[1];
            int callerID = (int) args[2];
            //@unluckyPlayer - player to get deck of cards resultant to call
            //@cardsToReceive - JSONArray of card IDs to be taken by unluckyPlayer. Ex: ["1d". "6s", "11d"] for a stack of
            // Ace of diamonds, six of spades and jack of diamonds
            //@callerID - player ID of subject who got justly called on BS
            //use info to update UI gamestate, notify the player that BS call was successful
            try {
                if (unluckyPlayer == playerID) {
                    vibrate.vibrate(400);
                    gameLog.append("Player " + callerID + " correctly called Player " + unluckyPlayer + "'s BS!\n");
                        for (int index = 0; index < cardsToReceive.length(); index++) {
                            String cardReceived = cardsToReceive.get(index).toString();
                            //String cardNumber = cardReceived.substring(0, cardReceived.length() - 1);
                            switch (cardReceived) {
                                case "1":
                                    setText(ace, aceCardNumber, 1);
                                    break;
                                case "2":
                                    setText(two, twoCardNumber, 1);
                                    break;
                                case "3":
                                    setText(three, threeCardNumber, 1);
                                    break;
                                case "4":
                                    setText(four, fourCardNumber, 1);
                                    break;
                                case "5":
                                    setText(five, fiveCardNumber, 1);
                                    break;
                                case "6":
                                    setText(six, sixCardNumber, 1);
                                    break;
                                case "7":
                                    setText(seven, sevenCardNumber, 1);
                                    break;
                                case "8":
                                    setText(eight, eightCardNumber, 1);
                                    break;
                                case "9":
                                    setText(nine, nineCardNumber, 1);
                                    break;
                                case "10":
                                    setText(ten, tenCardNumber, 1);
                                    break;
                                case "11":
                                    setText(jack, jackCardNumber, 1);
                                    break;
                                case "12":
                                    setText(queen, queenCardNumber, 1);
                                    break;
                                case "13":
                                    setText(king, kingCardNumber, 1);
                                    break;
                            }
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
    private Emitter.Listener onFalseBS = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONArray cardsToReceive = (JSONArray) args[0];
            int callerID = (int) args[1];
            // both params the same as above
            
            //use this info to return quantity of cards to player who falsely called BS (callerID)
            try {
                if (callerID == playerID) {
                    vibrate.vibrate(400);
                    gameLog.append("Player " + callerID + " incorrectly called BS!\n");
                    for (int index = 0; index < cardsToReceive.length(); index++) {
                        String cardReceived = cardsToReceive.get(index).toString();
                        //String cardNumber = cardReceived.substring(0, cardReceived.length() - 1);
                        switch (cardReceived) {
                            case "1":
                                setText(ace, aceCardNumber, 1);
                                break;
                            case "2":
                                setText(two, twoCardNumber, 1);
                                break;
                            case "3":
                                setText(three, threeCardNumber, 1);
                                break;
                            case "4":
                                setText(four, fourCardNumber, 1);
                                break;
                            case "5":
                                setText(five, fiveCardNumber, 1);
                                break;
                            case "6":
                                setText(six, sixCardNumber, 1);
                                break;
                            case "7":
                                setText(seven, sevenCardNumber, 1);
                                break;
                            case "8":
                                setText(eight, eightCardNumber, 1);
                                break;
                            case "9":
                                setText(nine, nineCardNumber, 1);
                                break;
                            case "10":
                                setText(ten, tenCardNumber, 1);
                                break;
                            case "11":
                                setText(jack, jackCardNumber, 1);
                                break;
                            case "12":
                                setText(queen, queenCardNumber, 1);
                                break;
                            case "13":
                                setText(king, kingCardNumber, 1);
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    
}
