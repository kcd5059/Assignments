angular.module("AppMod", [])
    .controller("AppCtrl", ["$http", function($http) {
        var self = this;
        
        
    $http.get("http://localhost:8080/students")
        .then(function(result) {
        self.students = result.data;
        for (var student of self.students) {
            student.vis = true;
        }
    }, function(err) {
    }); // End get
        
    self.refresh = function() {
        location.reload();
    }
    
    self.toggleRow = function(id) {
        for(var student of self.students) {
            if(student.id === id) {
                student.vis = !student.vis;
            }
        }
    }
    
    self.filterBySAT = function() {
        var filterValue = $("#filter-param").val();
        for(var student of self.students) {
            student.vis = true;
            if(student.sat < filterValue) {
                student.vis = false;
            }
        }
    }
        
    }]); //end controller

$(document).ready(function() {
    document.getElementById('filter-param').addEventListener('keydown', function(e) {
    var key   = e.keyCode ? e.keyCode : e.which;

    if (!( [8, 9, 13, 27, 46, 110, 190].indexOf(key) !== -1 ||
         (key == 65 && ( e.ctrlKey || e.metaKey  ) ) || 
         (key >= 35 && key <= 40) ||
         (key >= 48 && key <= 57 && !(e.shiftKey || e.altKey)) ||
         (key >= 96 && key <= 105)
       )) e.preventDefault();
});
    
    $("#delete-btn").click(function() {
        var allVals = [];
        $("table :checked").each(function() {
            allVals.push($(this).val());
        });
        var conf = confirm("Delete selected student(s)?");
        if(conf){
            for(var i = 0; i < allVals.length; i++) {
            var durl = "http://localhost:8080/deletestudent/";
            durl += allVals[i];
            $.ajax({
            url: durl
            })
            .then(function() {
             location.reload();
            }); // End delete call
        } // End for loop
        }
    }); // End delete function
})



