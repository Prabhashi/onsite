(function () {
    'use strict';


    angular
        .module('app')
        .controller('issueController', issueController);

    registerController.$inject = ['$scope' , 'issueService' , '$state','$window'];

    function issueController($scope , issueService , $state) {

        $scope.register = function () {
         issueService.register($scope.user).then(function success(data) {
            if(data.status=="success"){
                $state.go('app.reports');
             }else{
                 $state.go('app.reports');
                window.alert("Invalid register!");
             }
         });
        }
    }

})();
