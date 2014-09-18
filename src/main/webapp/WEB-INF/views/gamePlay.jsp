<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<link href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.gameplay.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.global.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div id="end-dialog" title="Another Battle?">
		</div>
		<form id="reset-game-form" action="/battleship/reset" method="GET"></form>
		
		<div class="menu">
			<h2>Destroy The Hostile Fleet</h2>
			<p id="reset-game-button">Reset Game</p>
			<p id="battle-status"></p>
			<p id="elapsed-time">00:00</p>
		</div>
		
		<div id="player-grid-parent">
			<div id="scene">
				<div id="player-grid"></div>
				<div id="enemy-grid"></div>
			</div>
		</div>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
	<%@ include file='/WEB-INF/views/jspf/dimensions.jspf' %>
</body>
</html>
