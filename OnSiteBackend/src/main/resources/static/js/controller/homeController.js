(function () {
    'use strict';


    angular
        .module('app')
        .controller('homeController', homeController);
        
    homeController.$inject = ['$scope' , 'homeService' , '$state', '$http', '$filter', 'serverSettings','$window', '$rootScope'];
        function homeController($scope , homeService , $state ,$http, $filter, serverSettings, $window) {
           // var userID = JSON.parse(localStorage.getItem("user")).username;
            var userID = JSON.parse(localStorage.getItem("user")).username;
            homeService.details().then(function (projects) {
                $scope.projects = projects.projects;
                console.log($scope.projects);
            });
            
            $scope.getSchedule = function(projectID) {
                localStorage.setItem("currentProject", projectID);
                $state.go("app.schedule");
            }

        }
      
})();

