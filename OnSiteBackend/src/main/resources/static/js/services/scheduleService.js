(function () {
    'use strict';

    angular
        .module('app')
        .factory('scheduleService', scheduleService);

    scheduleService.$inject = ['$http' , 'serverSettings','$filter'];

    function scheduleService($http , serverSettings, $filter) {
         var webApi = serverSettings.webApi ;
        
        var service = {
            schedule: schedule
        };
        
       // var defer = $q.defer();//http X
        
        return service;

        function schedule(currentProject) {
            //return $http.get(webApi + '/task/all' + currentProject).then(handleSuccess, handleError('Loging error'));
             return $http.get(webApi + '/task/all/{projectId}/{userId}' ).then(handleSuccess, handleError('Loging error'));
            /*var p = {"projects" : [{
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
