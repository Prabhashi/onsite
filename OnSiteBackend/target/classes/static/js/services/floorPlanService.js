(function () {
    'use strict';

    angular
        .module('app')
        .factory('floorPlanService', floorPlanService);

    floorPlanService.$inject = ['$http' , 'serverSettings'];

    function floorPlanService($http , serverSettings) {


        var webApi = serverSettings.webApi ;

        var service = {
            addPlan: addPlan
        };

        return service;

        function addPlan(plan) {
          console.log("In plan section");
            return $http({
                url: webApi + '/plan/add',
                method: "POST",
                data: plan,
                headers : {'Content-Type': 'multipart/form-data'} 
            }).then(handleSuccess, handleError('Loging error'));
        }

        function handleSuccess(res) {
            return res;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }



    }
})();
