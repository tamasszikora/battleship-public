$(document).ready(function() {
	$("a").on("click", function(event){
		event.preventDefault();
		if (($(".menu p").text() == "" || $("#opponent-select").disabled)&& ($(this).data("form") == "local-game-form" || $(this).data("form") == "send-request-form")) {
			alert("You have to log in if you want to play!");
		} else {
			document.getElementById($(this).data("form")).submit();
		}
	});
	
	checkUserStatus();
});

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