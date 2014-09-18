$(document).ready(function() {
	var logInButton = document.getElementById("log-in-button");
	if(logInButton != null) {
		logInButton.addEventListener('click', function(){
			document.getElementById("log-in-form").submit();
		}, false);
	}
	
	var logOutButton = document.getElementById("log-out-button");
	if(logOutButton != null) {
		logOutButton.addEventListener('click', function(){
			document.getElementById("log-out-form").submit();
		}, false);
	}
	
	var localImg = document.getElementById("local-game-pic");
	localImg.addEventListener('click', function(){
		if(document.getElementById("opponent-selector").disabled) {
			alert("You have to log in if you want to play!");
		} else {
//			$(this).css("z-index","100");
//			$(this).css("-webkit-animation","move-ship 1s");
//			window.setTimeout(function() {submitLocalForm();},900);
			document.getElementById("local-game-form").submit();
		}
	}, false);
	
	var sendRequestImg = document.getElementById("send-request-pic");
	if(sendRequestImg != null) {
		sendRequestImg.addEventListener('click', function(){
			if(document.getElementById("opponent-selector").disabled) {
				alert("You have to log in if you want to play against others!");
			} else {
				document.getElementById("send-request-form").submit();
			}
		}, false);
	}
	
	checkUserStatus();
});

//function submitLocalForm() {
//	document.getElementById("local-game-form").submit();
//}

function checkUserStatus() {
	userStatusInterval = setInterval(function() {
		$.ajax({
			url : "/battleship/getUserStatus",
		}).done(function(msg) {
			if (msg == "REQUESTED") {
				battleRequestDialog("You have a Battle Request!");
				clearInterval(userStatusInterval);
			} else if (msg == "OCCUPIED") {
				checkBattleStatus();
				clearInterval(userStatusInterval);
			}
		});
	}, 2000);
}

function checkBattleStatus() {
	battleStatusInterval = setInterval(function() {
		$.ajax({
			url : "/battleship/getBattleStatus",
		}).done(function(msg) {
			if (msg == "PLACE_SHIPS") {
				document.getElementById("remote-game-form").submit();
				clearInterval(battleStatusInterval);
			} else if (msg == "REQUEST_DECLINED") {
				document.getElementById("decline-battle-request-form").submit();;
				clearInterval(battleStatusInterval);
			}
		});
	}, 2000);
}

function battleRequestDialog(message) {
	var dialog = document.getElementById("battle-request-dialog");
	var p = document.createElement("p");
	p.innerHTML = message;
	dialog.appendChild(p);
	$("#battle-request-dialog").dialog({
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Accept" : function() {
				document.getElementById("accept-battle-request-form").submit();
				$(this).dialog("close");
			},
			"Decline" : function() {
				document.getElementById("decline-battle-request-form").submit();
				$(this).dialog("close");
			}
		}
	});
};