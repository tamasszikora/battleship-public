var gridWidth = 10;
var gridSize = 100;
var cubeFaces = ["front", "back", "left", "right", "top", "bottom"];

$(document).ready(function() {
	$("a").on("click", function(event){
		event.preventDefault();
		if ($(this).data("form") == "set-player-ready-form") {
			$.ajax({
				url : "/battleship/remote/setPlayerReady",
			}).done(function(msg) {
				checkBattleStatus();
			});
		} else {
			document.getElementById($(this).data("form")).submit();
		}
	});
	
	placeCubesFilledWithJSONDataToPlayerGrid();
	placeCubesFilledWithJSONDataToPlaceableShips();
});

function checkBattleStatus() {
	battleStatusInterval = setInterval(function() {
		$.ajax({
			url : "/battleship/getBattleStatus",
		}).done(function(msg) {
			if (msg == "YOUR_TURN" || msg == "NOT_YOUR_TURN") {
				document.getElementById("start-battle-remote-form").submit();
				clearInterval(battleStatusInterval);
			}
		});
	}, 1000);
}

function allowDrop(event) {
	event.preventDefault();
};

function drag(event) {
	event.dataTransfer.setData("ShipName", event.target.id);
};

function drop(event) {
	event.preventDefault();
	var shipName = event.dataTransfer.getData("ShipName");
	var cell = $(event.target).get(0);
	if (cell.getAttribute("id") != "player-grid") {
		var position = $(cell).data("position");
		$.ajax({
			url : "/battleship/placeShip?name=" + shipName + "&position=" + position
		}).done(function() {
			document.getElementById("player-grid").innerHTML = "";
			document.getElementById("placeable-ships-grid").innerHTML = "";
			placeCubesFilledWithJSONDataToPlayerGrid();
			placeCubesFilledWithJSONDataToPlaceableShips();
		});
	} else {
		alert("Please place your ship on a cube, thank you!");
	}
};

function placeCubesFilledWithJSONDataToPlayerGrid() {
	$.ajax({
		url : "/battleship/loadPlayerGridJSON",
		accept: "application/json"
	}).done(function(msg) {
		var parsedMsg = JSON.parse(msg);
		var positions = parsedMsg.positions;
		document.getElementById("player-grid-parent").style.width = (gridWidth * 30 + 480 + 12) + "px";
		var scene = document.getElementById("player-grid");
		scene.innerHTML = "";
		for(var i = 0; i < gridSize; i++) {
			var cube = document.createElement("div");
			cube.setAttribute("id", "position-" + i);
			cube.setAttribute("data-position", i);
			cube.className = "cube " + positions[i].className;
			addFacesToCube(cube, i);
			scene.appendChild(cube);
		}
	});
}

function placeCubesFilledWithJSONDataToPlaceableShips() {
	$.ajax({
		url : "/battleship/loadPlaceableShipsJSON",
		accept: "application/json"
	}).done(function(msg) {
		var parsedMsg = JSON.parse(msg);
		var ships = parsedMsg.ships;
		var shipsList = document.getElementById("placeable-ships-grid");
		shipsList.style.width = "480px";
		shipsList.innerHTML = "";
		for(var i = 0; i < ships.length; i++) {
			var shipName = document.createElement("div");
			shipName.innerHTML = ships[i].id;
			var ship = document.createElement("div");
			ship.appendChild(shipName);
			ship.setAttribute("id", ships[i].id);
			ship.setAttribute("draggable", "true");
			ship.setAttribute("ondragstart", "drag(event)");
			ship.className = "ship-div";
			for(var j = 0; j < 16; j++) {
				var cube = document.createElement("div");
				var cubeClass = "water";
				if(ships[i].shapeMatrix[j] == 1) {
					cubeClass = "ship";
				}
				cube.className = "cube " + cubeClass;
				addFacesToCube(cube, null);
				ship.appendChild(cube);
			}
			shipsList.appendChild(ship);
		}
	});
}

function addFacesToCube(cube, position) {
	for (var j = 0; j < 6; j++) {
		var cubeFace = document.createElement("div");
		if(position != null) cubeFace.setAttribute("data-position", position);
		cubeFace.className = "cube-face cube-face-" + cubeFaces[j];
		cube.appendChild(cubeFace);
	}
}