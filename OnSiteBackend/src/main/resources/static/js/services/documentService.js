(function () {
    'use strict';

    angular
        .module('app')
        .factory('documentService', documentService);

    documentService.$inject = ['$http' , 'serverSettings','$filter'];

    function documentService($http , serverSettings, $filter) {
        var webApi = serverSettings.webApi ;
        
        var service = {
            getDocument: getDocument
        };
        
       // var defer = $q.defer();//http X
        
        return service;

        function getDocument(userID) {
            //return $http.get(webApi + '/project/involved' + userID).then(handleSuccess, handleError('Loging error'));
            return $http.get(webApi + '/documents/get/{projectId}' ).then(handleSuccess, handleError('Loging error'));
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