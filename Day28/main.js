

$(document).ready(function() {
  var number = 0;
  var numberLocation = $("#number-loc");
  var incButton = $("#increment");
  var decButton = $("#decrement");
  var resButton = $("#reset");
  var proButton = $("#pro-button");
  var heading = $("h1");
  var pro = false;
  var modifier = 1;
  var modifierInput = $("#mod-input");

    // Display initial value of number
    numberLocation.text(number);

    // When + button is clicked
    incButton.click(function() {
      generateNumberPlus();
      numberLocation.text(number);
      updateStatus();
    });
    // When - button is clicked...
    decButton.click(function() {
      generateNumberMinus();
      numberLocation.text(number);
    updateStatus();
    });
    // When reset button is clicked...
    resButton.click(function() {
      number = 0;
      numberLocation.text(number);
      updateStatus();
      heading.css("color", "#000000");
      $("img").hide();
      heading.text("Basic Number Counter");
      if(!pro) {
        modifier = 1;
        modifierInput.val("1");
      }

    });
    // Toggle between pro version of counter
    proButton.click(function() {
      if(pro) {
        proButton.text("Activate Pro");
        pro = false;
        modifierInput.val("1");
        modifier = 1;
        modifierInput.removeAttr("disabled");
      } else {
        proButton.text("Deactivate Pro");
        pro = true;
        modifierInput.val("Random")
        modifierInput.attr("disabled", "disabled");
      }

    });

    // Generate number between -10 and 10 or increment/decrement
    function generateNumberPlus() {
      if(pro) {
        number = Math.floor(Math.random() * 21) - 10;
      } else {
        modifier = Number(modifierInput.val());
        number += modifier;
      }
    }
    function generateNumberMinus() {
      if(pro) {
        number = Math.floor(Math.random() * 20) - 9;
      } else {
        modifier = Number(modifierInput.val());
        number -= modifier;
      }
    }


    // Updates various elements on the page after clicks
    function updateStatus() {
      // Generates a random background color for the number box
      var color = '#' + ("000000" + Math.random().toString(16).slice(2, 8).toUpperCase()).slice(-6);
      $("h1").css("color", color);

      if(number % 2 === 0 && number !== 0) {
        numberLocation.addClass("make-italic")
      } else {
        numberLocation.removeClass("make-italic");
      }

      if(number > 8) {
        numberLocation.addClass("make-red");
      } else {
        numberLocation.removeClass("make-red");
      }

      if((number + "").indexOf("7") !== -1) {
        numberLocation.addClass("make-green");
      } else {
        numberLocation.removeClass("make-green");
      }

      switch(number) {
        case 3: heading.text("Pretty Snazzy Basic Number Counter");
            break;
        case 6: heading.text("Super Cool Basic Number Counter");
            break;
        case 9: heading.text("Clearly the Best Basic Number Counter");
            break;
        case 10: $("img").show();
      }
    }



});
