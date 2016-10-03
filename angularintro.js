angular.module("AppMod", [])
    .controller("AppCtrl", [function() {
        var self = this;
        self.about = "About AngularJS";
    self.changeAbout = function(param1) {
      self.about = "AngularJS" + param1; 
    };
        
    self.students = [
        {id: 1, name: 'Kyle Deen', sat: 1200, gpa: 3.7, major_id: 4, vis: true},
        {id: 2, name: 'Derp Dorspon', sat: 400, gpa: 1.0, major_id: 6, vis: false},
        {id: 3, name: 'Jortle Joggback', sat: 800, gpa: 2.0, major_id: 3, vis: true},
        {id: 4, name: 'Yogey Birgwomp', sat: 1050, gpa: 3.0, major_id: 7, vis: false},
        {id: 5, name: 'Lile Kouglar', sat: 900, gpa: 3.2, major_id: 1, vis: false},
        {id: 6, name: 'Yogey Birgwomp', sat: 1000, gpa: 3.1, major_id: 5, vis: false},
        {id: 7, name: 'Scooter Folphin', sat: 975, gpa: 2.3, major_id: 3, vis: true},
        {id: 8, name: 'Donny Dorpson', sat: 250, gpa: 2.8, major_id: 2, vis: true},
        {id: 9, name: 'Dirk Doggler', sat: 1550, gpa: 4.0, major_id: 5, vis: false},
        {id: 10, name: 'Lolly Troxter', sat: 1300, gpa: 3.5, major_id: 7, vis: true}
    ];
        
    self.showAll = function() {
        for(var student of self.students) {
            student.vis = true;
        }
    };
        
    }]); //end controller