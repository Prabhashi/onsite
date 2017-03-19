(function () {
    'use strict';

    angular
        .module('app')
        .factory('loginService', loginService);

    loginService.$inject = ['$http' , 'serverSettings'];

    function loginService($http , serverSettings) {


        var webApi = serverSettings.webApi ;

        var service = {
            login: login
        };

        return service;

        function login(user) {
          console.log("In login section");
            return $http({
                url: webApi + '/user/login',
                method: "POST",
                data: user,
                headers: { 'Content-Type': 'application/json' }
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
