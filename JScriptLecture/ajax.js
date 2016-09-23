$(document).read(function() {


  $.ajax("https://api.github.com/users/jamesdabbs/repos", {
    data: {
      sort: "updated",
      direction: "desc"
    }
  }).then(function(result) {
    console.log("Result", result)

    $("#main").text(result.length + " repos found");

    for(var i = 0; i < result.length; i++) {
      var item = $("<li>" +  result[i].name + "</li>");
      $("#repos").append(item);
    }
  });
});
