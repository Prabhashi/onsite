(function () {
    'use strict';


    angular
        .module('app')
        .controller('documentController', documentController);

    documentController.$inject = ['$scope' , 'documentService' , '$state', '$http', '$filter', 'serverSettings','$window'];
    function documentController($scope , documentService , $state ,$http, $filter, serverSettings, $window) {
        $scope.userID = JSON.parse(localStorage.getItem("user")).userID;
    }
})();