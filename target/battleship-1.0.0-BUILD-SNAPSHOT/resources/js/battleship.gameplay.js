$(document).ready(function(){
	alert("Szevasz");
	$("#enemyGrid").load("/battleship/loadEnemyGrid");
	$("#playerGrid").load("/battleship/loadPlayerGridForGame");
});

$("#opponentgrid tr").click(function(e) {
	alert("Szevasz");
	var cell = $(e.target).get(0);
	if(cell.nodeName != 'td') {
		cell = $(cell).closest('td').get(0);
	}
	$(cell).load("/battleship/addFire?position=" + $(cell).data("position"));
});