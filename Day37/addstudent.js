angular.module("AppMod", [])
    .controller("AppCtrl", ["$http", function($http) {
        var self = this;
    
    self.addStudent = function() {
        var conf = confirm("Save student to database?");
        var url = "http://localhost:8080/addstudent" 
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
