var threeD;

function switchDimension() {
	$.ajax({
		url : "/battleship/switchDimension"
	}).done(function(msg) {
		threeD = msg;
		$("#three-dimension").text(msg);
	});
}

$(document).ready(function() {
	threeDObj = document.getElementById("three-dimension");
	threeD = $(threeDObj).text();
	if(threeD == false || threeD == "false") {
		$("#two-d").attr("style", "visibility: hidden");
		$("#three-d").attr("style", "visibility: visible");
		$("#scene").css("-webkit-transform","rotateY(0deg) rotateX(0deg)");
	} else {
		$("#three-d").attr("style", "visibility: hidden");
		$("#two-d").attr("style", "visibility: visible");
		$("#scene").css("-webkit-transform","rotateY(-30deg) rotateX(30deg)");
	}
	
	var toTwoD = document.getElementById("two-d");
	toTwoD.addEventListener('click', function() {
		switchDimension();
		threeD = false;
		$("#two-d").attr("style", "visibility: hidden");
		$("#three-d").attr("style", "visibility: visible");
		$("#scene").css("-webkit-animation","to-2d 1s");
		$("#scene").css("-webkit-transform","rotateY(0deg) rotateX(0deg)");
	});
	
	var toThreeD = document.getElementById("three-d");
	toThreeD.addEventListener('click', function() {
		switchDimension();
		threeD = true;
		$("#three-d").attr("style", "visibility: hidden");
		$("#two-d").attr("style", "visibility: visible");
		$("#scene").css("-webkit-animation","to-3d 1s");
		$("#scene").css("-webkit-transform","rotateY(-30deg) rotateX(30deg)");
	});
});