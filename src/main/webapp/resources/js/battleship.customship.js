$(document).ready(function() {
	$("a").on("click", function(event){
		event.preventDefault();
		var shipName = document.getElementById("shipname");
		if ($(this).data("form") == "create-ship-form" && shipName.value == "") {
			alert("The Name input field must not be empty!");
		} else {
			document.getElementById($(this).data("form")).submit();
		}
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