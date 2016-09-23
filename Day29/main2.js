function postMessage(channelName, text, token) {
  var pURL = "https://slack.com/api/chat.postMessage";

   $.ajax(pURL, {
     data: {
       token: token,
       channel: channelName,
       text: text,
       username: "Kyle-Bot",
       as_user: false
     },
     method: "POST"
   })
}


$(document).ready(function() {
  var token = getSlackToken();
  var publicurl = "https://slack.com/api/channels.list";
  var privateurl = "https://slack.com/api/groups.list";
  var channelName = "none";
  var channelIDs = [];

  $.ajax(publicurl, {
    data: {
      token: token
    },
    method: "GET"
  }).then(function(result) {
    // Loop through objects and generate radio button and label for each channel
    for (var i=0; i < result.channels.length; i++) {
      $("<input type='radio' name='name' class='channel'></input>")
      .attr({"value":result.channels[i].id, "id":i})
      .appendTo("#channel-list");
      $("<label for=" + i + ">" + result.channels[i].name + "</label><br />").appendTo("#channel-list");
      // Collect list of ids
      channelIDs.push(i);
    }
  });

    // Load private channels
    $.ajax(privateurl, {
      data: {
        token: token
      },
      method: "GET"
    }).then(function(result) {
      // Loop through objects and generate radio button and label for each channel
      for (var i=0; i < result.groups.length; i++) {
        $("<input type='radio' name='name' class='channel'></input>")
        .attr({"value":result.groups[i].id, "id":channelIDs.length})
        .appendTo("#private-channel-list");
        $("<label for=" + i + ">" + result.groups[i].name + "</label><br />").appendTo("#private-channel-list");
        // Collect list of ids
        channelIDs.push(i);
    }
  });

    channelName = $("input[type='radio']").click(function(){
      //Remove green color from all other labels
      for(var i=0; i < channelIDs.length; i++) {
        $("label[for=" + i + "]").removeClass("green");
      }
      // Collect id of clicked element
      var id = this.id;
      // Get value of clicked radio button and set channelName to it

      // Change label for selected radio button to green
      $("label[for=" + id + "]").addClass("green");
      // Hide error message if visible
      $("#error-message").hide();

      return $("input[type='radio']:checked").val();
    });

  $("#post-button").click(function(channelName) {
    // Check if channel radio button is selected show error if not
    if(channelName === "none") {
      $("#error-message").show();
    } else {
      var text = $("#message-box").val();
      postMessage(channelName, text, token);
      $("#message-box").val("");
    }

  });
  // Allow pressing enter to send message
  $("#message-box").keypress(function (k) {
    var key = k.which;
    if(key === 13) {
      $("#post-button").click();
      return false;
    }
  });

});
