<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.placeship.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.global.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Place Your Fleet</h2>
			<p id="create-ship-button"></p>
			<p id="place-ships-randomly-button"></p>
			<p id="start-battle-button"></p>
			<p id="place-ships-randomly-remote-button">Place Ships Randomly</p>
			<p id="set-player-ready-button">Start Battle</p>
		</div>
		<div id="player-grid-parent">
			<div id="scene">
				<div id="player-grid" ondrop="drop(event)" ondragover="allowDrop(event)"></div>
				<div id="placeable-ships-grid"></div>
			</div>
		</div>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
	<%@ include file='/WEB-INF/views/jspf/dimensions.jspf' %>
	<form id="place-ships-randomly-remote-form" action="/battleship/placeShipsRandomly" method="POST"></form>
	<form id="start-battle-remote-form" action="/battleship/remote/playBattle" method="GET"></form>
</body>
</html>
