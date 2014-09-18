var gridWidth = 10;
var gridSize = 100;
var end = false;
var classColors = new Array();
classColors["water"] = "#008A00";
classColors["ship"] = "#8697A9";
classColors["miss"] = "#1BA1E2";
classColors["hit"] = "#A20025";

var cubeFaces = [ "front", "back", "left", "right", "top", "bottom" ];

$(document).ready(function() {
	var resetGame = document.getElementById("reset-game-button");
	resetGame.addEventListener('click', function() {
		document.getElementById("reset-game-form").submit();
	});
	
	placeCubesFilledWithJSONDataToEnemyGrid();
	placeCubesFilledWithJSONDataToPlayerGrid();
	startGame();
	setElapsedTime();
	checkBattleStatus();
});

function startGame() {
	$.ajax({
		url : "/battleship/setStartTime"
	});
}

function setElapsedTime() {
	setInterval(function() {
		$("#elapsed-time").load("/battleship/getElapsedTime");
	}, 1000);
}

function checkBattleStatus() {
	battleStatusInterval = setInterval(function() {
		$.ajax({
			url : "/battleship/getBattleStatus",
		}).done(function(msg) {
			if (msg != "BATTLE_ERROR") {
				$("#battle-status").text(msg);
				if (msg != "YOUR_TURN" && msg != "NOT_YOUR_TURN") {
					alert(msg);
				}
				if (msg == "PLAYER_ONE_WON" || msg == "PLAYER_TWO_WON") {
					endDialog(msg);
					clearInterval(battleStatusInterval);
				}
			}
		});
	}, 1000);
}

function placeCubesFilledWithJSONDataToEnemyGrid() {
	$.ajax({
		url : "/battleship/loadEnemyGrid",
		accept : "application/json"
	}).done(function(msg) {
		var parsedMsg = JSON.parse(msg);
		var positions = parsedMsg.positions;
		document.getElementById("player-grid-parent").style.width = (gridWidth * 60 + 12) + "px";
		var scene = document.getElementById("enemy-grid");
		scene.innerHTML = "";
		for (var i = 0; i < gridSize; i++) {
			var cube = document.createElement("div");
			cube.setAttribute("id", "enemy-position-" + i);
			cube.setAttribute("data-position", i);
			cube.className = "cube " + positions[i].className;
			
			addFacesToCube(cube, i);
			addEventListenerToCube(cube);
			
			scene.appendChild(cube);
		}
	});
}

function addFacesToCube(cube, position) {
	for (var j = 0; j < 6; j++) {
		var cubeFace = document.createElement("div");
		cubeFace.setAttribute("data-position", position);
		cubeFace.className = "cube-face cube-face-" + cubeFaces[j];
		cube.appendChild(cubeFace);
	}
}

function addEventListenerToCube(cube) {
	cube.addEventListener('click', function() {
		var target = $(this);
		$.ajax({
			url : "/battleship/addFire?position=" + $(this).data("position")
		}).done(function(msg) {
			if ($("#battle-status").text() == "NOT_YOUR_TURN") {
				alert("This is not your turn!");
			} else {
				target.className = "cube " + msg;
				$(target).css("background", classColors[msg]);
				document.getElementById("player-grid").innerHTML = "";
				placeCubesFilledWithJSONDataToPlayerGrid();
				if (msg == "miss") {
					$(target).css("-webkit-transform", "translateZ(-9px)");
				}
				isEnemyFleetDestroyed();
				if(!end) {
					isPlayerFleetDestroyed();
				}
			}
		});
	}, false);
}

function placeCubesFilledWithJSONDataToPlayerGrid() {
	$.ajax({
		url : "/battleship/loadPlayerGridJSON",
		accept : "application/json"
	}).done(
		function(msg) {
			var parsedMsg = JSON.parse(msg);
			var positions = parsedMsg.positions;
			var scene = document.getElementById("player-grid");
			scene.style.width = gridWidth * 30 + "px";
			scene.style.height = gridWidth * 30 + "px";
			scene.innerHTML = "";
			for (var i = 0; i < gridSize; i++) {
				var cube = document.createElement("div");
				cube.setAttribute("id", "player-position-" + i);
				cube.setAttribute("data-position", i);
				cube.className = "cube " + positions[i].className;
				addFacesToCube(cube, i);
				scene.appendChild(cube);
			}
		});
};

function isPlayerFleetDestroyed() {
	$.ajax({
		url : "/battleship/isPlayerFleetDestroyed",
	}).done(function(msg) {
		if (msg == "true") {
			end = true;
			endDialog("This Battle is over. Your fleet has been destroyed!");
		}
	});
}
function isEnemyFleetDestroyed() {
	$.ajax({
		url : "/battleship/isEnemyFleetDestroyed",
	}).done(function(msg) {
		if (msg == "true") {
			endDialog("Victory is yours. You destroyed the enemy's fleet!");
		}
	});
}

function endDialog(message) {
	var dialog = document.getElementById("end-dialog");
	var p = document.createElement("p");
	p.innerHTML = message;
	dialog.appendChild(p);
	$("#end-dialog").dialog({
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Reset Game" : function() {
				document.getElementById("reset-game-form").submit();
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
};