(function () {
    'use strict';

    angular
        .module('app')
        .factory('homeService', homeService);

    homeService.$inject = ['$http' , 'serverSettings','$filter', '$q'];

    function homeService($http , serverSettings, $filter, $q) {
        var webApi = serverSettings.webApi ;
        
        var service = {
            details: details
        };
        
        //var defer = $q.defer();//http X
        
        return service;

        function details() {
            //return $http.get(webApi + '/project/involved' + userID).then(handleSuccess, handleError('Loging error'));
            return $http.get(webApi + '/project/involved/'+localStorage.userId).then(handleSuccess, handleError('Loging error'));
           /* var p = {"projects" : [{
                            "projectID": 1,
                            "projectName": "test1"
                        },
                        {
                            "projectID": 2,
                            "projectName": "test2"
                        },
                        {
                            "projectID": 3,
                            "projectName": "test3"
                        }]
                    };
                    */
                    
            console.log(p);
            //defer.resolve(p);//http X
            //return defer.promise;//http X
        }

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }
})();

