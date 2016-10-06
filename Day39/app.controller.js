mod.controller("AppCtrl", ['$http', '$routeParams', function($http, $routeParams) {
		var self = this;
		self.id = $routeParams.studentId;

		$http.get('http://localhost:8080/students')
			.then(function(resp){
				self.students = resp.data;
                for (var student of self.students) {
                    student.vis = true;
                    if(student.id == self.id) {
                        self.student = student;
                    }
                }
			},function(err) {

			});
    /*
    $http.get('http://localhost:8080/student/'+self.id)
			.then(function(resp){
				self.student = resp.data;
			},function(err) {
			}); */
    
    

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
    } // end reload
    
    self.toggleRow = function(id) {
        for(var student of self.students) {
            if(student.id === id) {
                student.vis = !student.vis;
            }
        }
    } // end toggleRow
    
    self.filterBySAT = function() {
        var filterValue = $("#filter-param").val();
        for(var student of self.students) {
            student.vis = true;
            if(student.sat < filterValue) {
                student.vis = false;
            }
        }
    } // end filterBySAT
    
    self.deleteStudents = function() {
        var allVals = [];
        $("table :checked").each(function() {
            allVals.push($(this).val());
        });
        var conf = confirm("Delete selected student(s)?");
        if(conf){
            for(var i = 0; i < allVals.length; i++) {
                var durl = "http://localhost:8080/deletestudent/";
                durl += allVals[i];
                $http.get(durl)
                .then(function() {
                location.reload();
            }); // End delete call
        } // End for loop
        } // end if
    }
    
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
             window.location.replace("#/students");
             }); // end $http.get
        } // end if
    } // end addStudent
    
    self.loadStudent = function(id) {
        window.location.replace("#/editstudent/" + id);
    } // end loadStudent
    
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
             window.location.replace("#/students");
             }); // End get
        } // End if
    }

	}]) // end controller
	

;// end all 