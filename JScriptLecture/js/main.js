
	
var triesLeft = 6;
// Random number generator between 1 and 100
var answer = Math.floor(Math.random() * 100) + 1;
	
	  
$(document).ready(function() {
  var tryText = $("#try-count");
  var resultMsg = $(".result");
  var inputBox = $("#guess-input")
  console.log(answer);
  
  resultMsg.hide();
  $("#guess-h1").text("Guess a number between 1 and 100");
		
  $("#guess-button").click(function() {
	  inputBox.removeClass("invalid")
	var guess = Number($("#guess-input").val());
	triesLeft--;
		  
	tryText.text("Tries left: " + triesLeft);
		
	if(guess > answer) {
	  result = "High";
	} else if(guess < answer) {
	  result = "Low";
	} else if(isNaN(guess)) {
		inputBox.addClass("invalid")
	} else {
	  $("#guess-button").attr("disabled", "disabled");
	  result = "Correct";
	  
	  resultMsg.text("You win!")
	  resultMsg.show();
	}
	
	if (triesLeft === 0 && guess !== answer) {
		resultMsg.text("You lose! The answer is " + answer + ".").
		show();
		
		$("#guess-button").attr("disabled", "disabled");
	}
		  
	var newRow = $("<tr><td>" + guess + "</td></tr>");
	var newCell = $("<td>" + result + "</td>");
	// Append adds before last closing tag
	newRow.append(newCell);
	$("table").prepend(newRow);
  });
  tryText.text("Tries left: " + triesLeft);
});
	  
	  