$(document).ready(function() {
	var createNewShip = document.getElementById("create-ship-button");
	createNewShip.addEventListener('click', function() {
		var shipName = document.getElementById("shipname");
		if(shipName.value == "") {
			alert("The Name input field must not be empty!");
		} else {
			document.getElementById("create-ship-form").submit();
		}
	});
	
	var placeShips = document.getElementById("place-ships-button");
	placeShips.addEventListener('click', function() {
		document.getElementById("place-ships-form").submit();
	});
	
	buildShapeMatrix();
});

function buildShapeMatrix() {
	var cubeFaces = ["front", "back", "left", "right", "top", "bottom"];
	
	var shapeMatrix = document.getElementById("shape-matrix");
	document.getElementById("scene").style.width = "335px";
	shapeMatrix.innerHTML = "";
	for(var i = 0; i < 16; i++) {
		var cube = document.createElement("div");
		cube.setAttribute("id", "position-" + i);
		cube.setAttribute("data-position", i);
		cube.className = "cube water";
		for(var k = 0; k < 6; k++) {
			var cubeFace = document.createElement("div");
			cubeFace.setAttribute("id",cubeFaces[k] + "-" + i);
			cubeFace.className = "cube-face cube-face-" + cubeFaces[k];
			cube.appendChild(cubeFace);
		}
		cube.addEventListener('click', function() {
			var target = $(this);
			$.ajax({
				url : "/battleship/createShip?step=addShipPart&position=" + $(this).data("position"),
			}).done(function(msg) {
				if(msg == 0) {
					$(target).css("background", "#444");
				} else {
					$(target).css("background", "#647687");
				}
			});
		}, false);
		shapeMatrix.appendChild(cube);
	};
}