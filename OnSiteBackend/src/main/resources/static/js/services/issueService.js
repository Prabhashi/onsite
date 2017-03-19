(function () {
    'use strict';

    angular
        .module('app')
        .factory('issueService', issueService);

    issueService.$inject = ['$http' , 'serverSettings'];

    function issueService($http , serverSettings) {


        var webApi = serverSettings.webApi ;

        var service = {
            issue: issue
        };

        return service;

        function register(user) {
            return $http({
                url: webApi + '/issues/create',
                method: "POST",
                data: user,
                headers: { 'Content-Type': 'application/json' }
            }).then(handleSuccess, handleError('Error issues'));
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
