angular.module("AppMod", [])
    .controller("AppCtrl", ["$http", function($http) {
        var self = this;
    
    self.refresh = function() {
        location.reload();
    }
    
    self.updateStudent = function() {
        var conf = confirm("Update student?");
        var url = "http://localhost:8080/updatestudent/" + $("#student-id").val() 
            + "/" + $("#first-name").val()
            + "/" + $("#last-name").val()
            + "/" + $("#sat").val()
            + "/" + $("#gpa").val()
            + "/" + $("#major-id").val();
        if(conf) {
            $http.get(url)
            .then(function() {
             window.location.replace("index.html");
             }); // End get
        } // End if
    } // End updateStudent
    
        
}]); //end controller

$(document).ready(function() {
    
    // Parse parameter from url
    var studentId = location.search.substr(4);
    
    $.ajax({
            url: "http://localhost:8080/student/" + studentId
        }).then(function(data) {
            $("#student-id").val(data.id);
            $("#first-name").val(data.first_name);
            $("#last-name").val(data.last_name);
            $("#sat").val(data.sat);
            $("#gpa").val(data.gpa);
            $("#major-id").val(data.major_id);
        }); // End ajax call
    
    
});
