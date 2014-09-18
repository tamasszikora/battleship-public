<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Battleship</title>
</head>
<body>
	<h1>
		Battleship  
	</h1>
	
	<form action="/battleship/gettingStarted" method="POST">
		<label for="host">Please select a Host:</label>
		<select name="host">
			<c:forEach var="host" items="${hosts}">
				<option value="${host.value}">${host.key}</option>
			</c:forEach>
		</select>
		<br>
		<button type="submit">Start Game</button>
	</form>
</body>
</html>
