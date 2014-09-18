$(document).ready(function() {
	$("#playerGrid").load("/battleship/loadPlayerGrid");
	$("#placeableShips").load("/battleship/loadPlaceableShips");
});

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
	if(cell.nodeName != 'td') {
		cell = $(cell).closest('td').get(0);
	}
	var position = $(cell).data("position");
	$("#placeResult").load("/battleship/placeShip?name=" + shipName + "&position=" + position);
	//document.getElementById("playerGrid").innerHTML = "";
	$("#playerGrid").load("/battleship/loadPlayerGrid");
	//document.getElementById("placeableShips").innerHTML = "";
	$("#placeableShips").load("/battleship/loadPlaceableShips");
	//event.target.appendChild(document.getElementById(ship));
};