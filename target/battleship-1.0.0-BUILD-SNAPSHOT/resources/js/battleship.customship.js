$(document).ready(function() {
	$("table tr").click(function(e) {
		var cell = $(e.target).get(0);
		if(cell.nodeName != 'td') {
			cell = $(cell).closest('td').get(0);
		}
		if (cell.style.color == "rgb(255, 0, 255)") {
			cell.style.color = "black";
		} else {
			cell.style.color = "magenta";
		}
		$(cell).load("/battleship/createShip?step=addShipPart&position=" + $(cell).text());
	});
});