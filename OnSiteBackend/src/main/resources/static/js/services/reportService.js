(function () {
    'use strict';

    angular
        .module('app')
        .factory('reportService', reportService);

    reportService.$inject = ['$http' , 'serverSettings'];

    function reportService($http , serverSettings) {


        var webApi = serverSettings.webApi ;

        var service = {
            report: report
        };

        return service;

        function report(user) {
          console.log("In issue report section");
            return $http({
                url: webApi + '/issues/create',
                method: "POST",
                data: user,
                headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
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

