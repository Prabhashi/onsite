(function () {
    'use strict';


    angular
        .module('app')
        .controller('reportController', reportController);

    reportController.$inject = ['$scope' , 'reportService' , '$state','$window'];

    function reportController($scope , reportService , $state) {
         
        $scope.report = function () {
         reportService.report($scope.user).then(function success(data) {
            if(data.status==200){
                window.alert("Report Success");
             }else{
                 $state.go('app.report');
                window.alert("Invalid report!");
             }
         });
        }
    }

})();


