(function () {
    'use strict';


    angular
        .module('app')
        .controller('registerController', registerController);

    registerController.$inject = ['$scope' , 'registerService' , '$state','$window'];

    function registerController($scope , registerService , $state) {
         
        $scope.register = function () {
         registerService.register($scope.user).then(function success(data) {
            if(data.status==200){
                $state.go('appSimple.login');
             }else{
                 $state.go('appSimple.register');
                window.alert("Invalid register!");
             }
         });
        }
    }

})();


