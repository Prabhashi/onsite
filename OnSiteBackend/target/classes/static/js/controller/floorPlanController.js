(function () {
    'use strict';


    angular
        .module('app')
        .controller('floorPlanController', floorPlanController);

    floorPlanController.$inject = ['$scope' , 'floorPlanService' , '$state', '$http', '$filter', 'serverSettings','$window'];
    function floorPlanController($scope , floorPlanService , $state ,$http, $filter, serverSettings, $window) {
         floorPlanService.addPlan($scope.plan).then(function success(data) {
           console.log("In plan function");
             if(data.status=="success"){
                 window.alert("Success");
             }else{
                 
                 window.alert("Upload fails");
             }
         });
    }
})();