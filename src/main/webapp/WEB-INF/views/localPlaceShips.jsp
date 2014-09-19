<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="/battleship/resources/js/battleship.placeship.js" type="text/javascript"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.common.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Place Your Fleet</h2>
			<a href="" data-form="create-ship-form">Create Your Ship</a>
			<a href="" data-form="place-ships-randomly-form">Place Ships Randomly</a>
			<a href="" data-form="start-battle-form">Start Battle</a>
		</div>
		<form id="create-ship-form" action="/battleship/createShip?step=newShip" method="POST"></form>
		<form id="place-ships-randomly-form" action="/battleship/placeShipsRandomly" method="POST"></form>
		<form id="start-battle-form" action="/battleship/local/playBattle" method="POST"></form>
		
		<div id="player-grid-parent">
			<div id="scene">
				<div id="player-grid" ondrop="drop(event)" ondragover="allowDrop(event)"></div>
				<div id="placeable-ships-grid"></div>
			</div>
		</div>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
	<%@ include file='/WEB-INF/views/jspf/dimensions.jspf' %>
</body>
</html>