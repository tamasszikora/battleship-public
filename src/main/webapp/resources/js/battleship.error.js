$(document).ready(function() {
	var resetGame = document.getElementById("reset-game-button");
	resetGame.addEventListener('click', function() {
		document.getElementById("reset-game-form").submit();
	});
});