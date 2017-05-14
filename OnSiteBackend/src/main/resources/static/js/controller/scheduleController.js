(function () {
    'use strict';


    angular
        .module('app')
        .controller('scheduleController', scheduleController);

    scheduleController.$inject = ['$scope' , 'scheduleService' , '$state', '$http', '$filter', 'serverSettings','$window'];
    function scheduleController($scope , scheduleService , $state ,$http, $filter, serverSettings, $window) {
            //var currentProject = localStorage.getItem("currentProject");
            var currentProject = localStorage.getItem("currentProject");
            homeService.schedule(currentProject).then(function (projects) {
                $scope.projects = projects;
                console.log($scope.projects);
            });


    }
})();