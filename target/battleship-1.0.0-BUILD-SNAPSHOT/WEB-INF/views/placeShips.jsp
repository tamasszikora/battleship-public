<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.placeship.js"></script>
</head>
<body>
	<h1>
		Battleship
	</h1>

	<h2>Place Your Fleet</h2>
	<div id="playerGrid"></div>
	<div id="placeableShips"></div>
	<div id="placeResult"></div>
	
	<form action="/battleship/createShip?step=newShip" method="POST">
		<button type="submit">Create Your Custom Ships</button>
	</form>
	
	<form action="/battleship/placeShipsRandomly" method="POST">
		<button type="submit">Place Ships Randomly</button>
	</form>
	
	<form action="/battleship/playBattle" method="POST">
		<button type="submit">Start Game</button>
	</form>
	
</body>
</html>
