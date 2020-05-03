 express = require('express'),
 http = require('http'),
 app = express(),
 server = http.createServer(app),
 io = require('socket.io').listen(server);
 
 
app.get('/', function(req, res) {
    res.send('Ooof, you have found our server; download the Bullshit app to play');
});

server.listen(4200, ()=>{
    console.log('Bullshit server is live on 4200');
});

//set up list of cards to derive deck from

var cardsList = new Array();
for (var i = 1; i <= 13; i++) {
  cardsList.push(i);
  cardsList.push(i);
  cardsList.push(i);
  cardsList.push(i);
}



// partition four random decks amongst four players

var decks = new Array();

for (var i = 0; i < 4; i++) {
  decks.push(new Array());
  for (var j = 0; j < 13; j++) {
    var temp= Math.floor(cardsList.length * Math.random());
    decks[i].push(cardsList[temp]);
    cardsList.splice(temp, 1);
    }
}

console.log(decks);


//ID of person to play next
var IdAccum = 0;
//last set of deposited cards
var lastClaim = "";
//total central discarded deck
var discards = new Array();

var cardToPlay = 1;


//return false if last claim was false
function isBS() {
   for (var i = parseInt(lastClaim.charAt(0)) - 1; i >= 0; i--) {
      if (parseInt(lastClaim.substring(1)) != discards[discards.length - parseInt(lastClaim.charAt(0)) + i]) {
          return false;
      }
      return true;
   }
}

io.on('connection', function (socket) {
  //Identify each player with a unique ID
  var playerID = IdAccum;
  console.log(IdAccum);
  
  io.sockets.emit('newPlayer', IdAccum);
  socket.emit('ID', playerID);
  IdAccum++;
 
  // Four players ready, game can begin
  if (IdAccum === 4) {
   io.sockets.emit('startGame');
  }
  IdAccum = IdAccum % 4;
  // give the player the decks, all but his will be hidden by app
  socket.emit('decks', decks);
  socket.on('disconnect', function() {
      io.sockets.emit('playerExit', playerID);
      IdAccum--;
      if (IdAccum === -1) {
         IdAccum = 0;
      }
      IdAccum = IdAccum % 4;
      console.log('player left');
  });
 
  //when cards are sent to server, log claim and actual cards individually
  socket.on('update', function(sentCards) {
       console.log('update');
       console.log(IdAccum);
       console.log(sentCards);
       lastClaim = sentCards.length + "" + cardToPlay;
       console.log(lastClaim);
       //tell all other players about his claim
       io.sockets.emit('claim', lastClaim, playerID);
       for (var i = 0; i < sentCards.length; i++) {
        
         discards.push(sentCards[i]);
       }
       //update state of game
        cardToPlay ++;
        IdAccum = playerID + 1;
        IdAccum = IdAccum % 4;
        
        //let next player know its his turn to play
        io.sockets.emit('callPlayer', IdAccum);
       
    });
  //When a BS claim is made...
  socket.on('bs', function() {
       console.log('BS');
       if (isBS() === true) {
           //is claim is true, tell all players that perpetrater gets his cards back
           if (IdAccum != 0) {
           io.sockets.emit('bs', (IdAccum - 1) % 4, discards, playerID);
           } else {
            io.sockets.emit('bs', 3, discards, playerID);
           }
           discards = new Array();
           
       } else {
           //else tell everyone the claim was false, an proclaimer gets all the central cards
           io.sockets.emit('U Fd up M8', discards, playerID);
          discards = new Array();
       }
    });
 
});



