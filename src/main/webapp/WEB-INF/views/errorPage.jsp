<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Battleship</title>
	<link href="/battleship/resources/css/reset.css" rel="stylesheet" type="text/css">
	<link href="/battleship/resources/css/style.css" rel="stylesheet" type="text/css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
	<script type="text/javascript" src="/battleship/resources/js/battleship.error.js"></script>
</head>
<body>
	<%@ include file='/WEB-INF/views/jspf/header.jspf' %>
	<div class="main">
		<div class="menu">
			<h2>Error Occurred</h2>
			<p id="reset-game-button">Back To Home Page</p>
		</div>
		<h3>${errorText}</h3>
		<form id="reset-game-form" action="/battleship/" method="GET"></form>
	</div>
	<%@ include file='/WEB-INF/views/jspf/footer.jspf' %>
</body>
</html>
