<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.gameplay.js"></script>
</head>
<body>
	<h1>
		Battleship  
	</h1>
	
	<h2>Destroy The Hostile Fleet</h2>
	
	<div id="enemyGrid"></div>
	<div id="playerGrid"></div>
	
	<form action="/resetGame" method="POST">
		<button type="submit">Reset Game</button>
	</form>
</body>
</html>
