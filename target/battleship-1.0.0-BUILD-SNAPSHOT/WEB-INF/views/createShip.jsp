<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Battleship</title>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.customship.js"></script>
</head>
<body>
	<h1>
		Battleship
	</h1>
	
	<h2>Create Your Custom Ship</h2>
	<form action="/battleship/createShip?step=newShip" method="POST">
		<label for="name">Name:</label>
		<input type="text" name="name">
		<label>Shape Matrix:</label>
		<table>
			<tr>
				<td>0</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
			</tr>
			<tr>
				<td>4</td>
				<td>5</td>
				<td>6</td>
				<td>7</td>
			</tr>
			<tr>
				<td>8</td>
				<td>9</td>
				<td>10</td>
				<td>11</td>
			</tr>
			<tr>
				<td>12</td>
				<td>13</td>
				<td>14</td>
				<td>15</td>
			</tr>
		</table>
		<br>
		<button type="submit">Create Ship</button>
	</form>
	
	<form action="/battleship/createShip?step=done" method="POST">
		<button type="submit">Place Ships</button>
	</form>
</body>
</html>
