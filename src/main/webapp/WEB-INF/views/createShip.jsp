<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.customship.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.global.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Create Your Ship</h2>
			<p id="create-ship-button">Create Ship</p>
			<p id="place-ships-button">Place Ships</p>
		</div>
		<div id="ship-div-data">
			<div id="scene">
				<div id="button-grid">
					<form id="create-ship-form" action="/battleship/createShip?step=newShip" method="POST">
						<label for="name">Name:</label>
						<input id="shipname" type="text" name="name">
						<div id="empty-name"></div>
					</form>
				</div>
				<div id="shape-matrix"></div>
			</div>
		</div>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
	<%@ include file='/WEB-INF/views/jspf/dimensions.jspf' %>
	<form id="place-ships-form" action="/battleship/createShip?step=done" method="POST"></form>
</body>
</html>
