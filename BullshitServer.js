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



var IdAccum = 0;
var lastClaim = new Array();
var discards = new Array();

function isBS() {
   for (var i = lastClaim.length - 1; i >= 0; i--) {
      if (lastClaim[i] != discards[discards.length - lastClaim.length + i]) {
          return false;
      }
      return true;
   }
}

io.on('connection', function (socket) {
  var playerID = IdAccum;
  IdAccum ++;
  if (IdAccum == 3) {
   io.sockets.emit('startGame');
  }
  IdAccum = IdAccum % 4;
  socket.emit('decks', decks);
  socket.on('update', function(sentCards, claim) {
       io.sockets.emit('claim', claim, playerID);
       for (var i = 0; i < sentCards.length; i++) {
        discards.push(sentCards[i]);
        lastClaim = claim;
        IdAccum = playerID + 1;
        IdAccum = IdAccum % 4;
        io.sockets.emit('callPlayer', IdAccum);
       }
    });
  socket.on('bs') {
       if (isBS() === true) {
           io.sockets.emit('bs', (IdAccum - 1) % 4, discards);
           discards = new Array();
           
       } else {
           io.sockets.emit('U Fd up M8', discards, playerID);
       }
    });
 
});



