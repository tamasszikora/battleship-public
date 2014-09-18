<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<link href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script src="/battleship/resources/js/battleship.home.js" type="text/javascript"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Choose your Enemy</h2>
			<c:choose>
				<c:when test="${loggedInAs == ''}">
					<p id="log-in-button">Log in as</p>
					<form id="log-in-form" action="/battleship/logIn" method="POST">
						<select name="userId">
							<c:forEach var="user" items="${users}">
								<option value="${user.id}" <c:if test="${user.name=='Tamás'}">selected</c:if>>${user.name}</option>
							</c:forEach>
						</select>
					</form>
				</c:when>
				<c:otherwise>
					<p>
						Logged in as ${loggedInAs}
					</p>
					<p id="log-out-button">Log out</p>
				</c:otherwise>
			</c:choose>
		</div>
		<form id="log-out-form" action="/battleship/logOut" method="POST"></form>
		
		<div id="home-div" class="game-mode-form">
			<div class="legend">Human Resistance</div>
			<form id="local-game-form" action="/battleship/local/gettingStarted" method="GET">
				<label>Fight against CPU</label>
				<img class="pic" id="local-game-pic" alt="" src="/battleship/resources/pics/battleship.png">
			</form>
		</div>
		
		<div class="game-mode-form">
			<div class="legend">Alien Fleet</div>
			<form id="send-request-form" action="/battleship/sendRequest" method="POST">
				<label for="opponentId">Choose your opponent</label>
				<select id="opponent-selector" name="opponentId" <c:if test="${loggedInAs == ''}">disabled</c:if>>
					<c:forEach var="opponent" items="${opponents}">
						<option value="${opponent.id}">${opponent.name}</option>
					</c:forEach>
				</select>
				<img class="pic" id="send-request-pic" alt="" src="/battleship/resources/pics/alienship.png">
			</form>
		</div>
		<form id="remote-game-form" action="/battleship/remote/gettingStarted" method="GET"></form>
		
		<div id="battle-request-dialog" title="Battle Request">
		</div>
		<form id="accept-battle-request-form" action="/battleship/acceptBattleRequest" method="GET"></form>
		<form id="decline-battle-request-form" action="/battleship/declineBattleRequest" method="GET"></form>
		
		<div id="leaderboard">
			<h2>Leaderboard</h2>
			<table>
				<tr>
					<th>#</th><th>Player</th><th>time</th>
				</tr>
				<tr>
					<td>1</td><td>David</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>2</td><td>Hasselhoff</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>3</td><td>As</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>4</td><td>Michael</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>5</td><td>Knight</td><td>11:50:30</td>
				</tr>
			</table>
			<table>
				<tr>
					<th>#</th><th>Player</th><th>time</th>
				</tr>
				<tr>
					<td>1</td><td>David</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>2</td><td>Hasselhoff</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>3</td><td>As</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>4</td><td>Michael</td><td>11:50:30</td>
				</tr>
				<tr>
					<td>5</td><td>Knight</td><td>11:50:30</td>
				</tr>
			</table>
		</div>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
</body>
</html>