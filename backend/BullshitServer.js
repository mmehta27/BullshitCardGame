 express = require('express'),
 http = require('http'),
 app = express(),
 server = http.createServer(app),
 io = require('socket.io').listen(server);
 
 
app.get('/', function(req, res) {
res.send('Ooof, you have found our server; download the Bullshit app to play')
})

server.listen(420,()=>{
console.log('Bullshit server is live on port 420')
});

//set up list of cards to derive deck from

var cardsList = new Array();
for (var i = 2; i <= 14; i++) {
  cardsList.push(i + "h");
  cardsList.push(i + "c");
  cardsList.push(i + "s");
  cardsList.push(i + "d");
}

// partition four random decks amongst four players

var decks = new Array();

for (var i = 0; i < 4; i++) {
  decks.push(new Array());
  for (int j = 0; j <= 13; j++) {
    int temp= Math.floor(cardsList.length * Math.random());
    decks[i].push(cardsList[temp]);
    cardsList.splice(temp, 1);
    }
}


//ID of person to play next
var IdAccum = 0;
//last set of deposited cards
var lastClaim = new Array();
//total central discarded deck
var discards = new Array();


//return false if last claim was false
function isBS() {
   for (var i = lastClaim.length - 1; i >= 0; i--) {
      if (lastClaim[i] != discards[discards.length - lastClaim.length + i]) {
          return false;
      }
      return true;
   }
}

io.on('connection', function (socket) {
  //Identify each player with a unique ID
  var playerID = IdAccum;
  IdAccum ++;
 
  // Four players ready, game can begin
  if (IdAccum == 4) {
   io.sockets.emit('startGame');
  }
  IdAccum = IdAccum % 4;
  // give the player the decks, all but his will be hidden by app
  socket.emit('decks', decks);
 
  //when cards are sent to server, log claim and actual cards individually
  socket.on('update', function(sentCards, claim) {
       //tell all other players about his claim
       io.sockets.emit('claim', claim, playerID);
       for (var i = 0; i < sentCards.length; i++) {
        
        //update state of game
        
        discards.push(sentCards[i]);
        lastClaim = claim;
        IdAccum = playerID + 1;
        IdAccum = IdAccum % 4;
        
        //let next player know its his turn to play
        io.sockets.emit('callPlayer', IdAccum);
       }
    });
  //When a BS claim is made...
  socket.on('bs') {
       if (isBS() === true) {
           //is claim is true, tell all players that perpetrater gets his cards back
           io.sockets.emit('bs', (IdAccum - 1) % 4, discards);
           discards = new Array();
           
       } else {
           //else tell everyone the claim was false, an proclaimer gets all the central cards
           io.sockets.emit('U Fd up M8', discards, playerID);
       }
    });
 
});


