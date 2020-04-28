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



