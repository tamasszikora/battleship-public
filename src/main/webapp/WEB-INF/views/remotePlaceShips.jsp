<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.placeship.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.common.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Place Your Fleet</h2>
			<a href="" data-form="place-ships-randomly-form">Place Ships Randomly</a>
			<a href="" data-form="set-player-ready-form">Start Battle</a>
		</div>
		<form id="place-ships-randomly-form" action="/battleship/placeShipsRandomly" method="POST"></form>
		<form id="start-battle-remote-form" action="/battleship/remote/playBattle" method="GET"></form>
		
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
