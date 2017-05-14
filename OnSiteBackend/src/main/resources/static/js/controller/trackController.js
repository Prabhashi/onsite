(function () {
    'use strict';


    angular
        .module('app')
        .controller('trackController', trackController);

    trackController.$inject = ['$scope' , 'trackService' , '$state', '$http', '$filter', 'serverSettings','$window'];
    function trackController($scope , trackService , $state ,$http, $filter, serverSettings, $window) {
        // var userID = JSON.parse(localStorage.getItem("user")).username;
        var userID = JSON.parse(localStorage.getItem("user")).username;
            homeService.details(userID).then(function (projects) {
                $scope.projects = projects.projects;
                console.log($scope.projects);
            });
    }
})();