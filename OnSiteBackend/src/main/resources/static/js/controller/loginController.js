(function () {
    'use strict';


    angular
        .module('app')
        .controller('loginController', loginController);

    loginController.$inject = ['$scope' , 'loginService' , '$state','$window' , '$rootScope'];

    function loginController($scope , loginService , $state , $window , $rootScope) {

        $scope.login = function () {
            console.log('test');
         loginService.login($scope.user).then(function success(data) {
           console.log("In login function");
             if(data.data.state==="failed"){
                 /*localStorage.setItem("user" , JSON.stringify(data.data.user));
                 $rootScope.user = data.data.user;**/
                  $state.go('appSimple.login');
                 window.alert("Invalid username and password!");
                
             }else{
                
                 
                 localStorage.userId = data.data.state;
                  localStorage.setItem("user" , JSON.stringify(data.data.user));
                 $rootScope.user = data.data.user;
                 
                 $state.go('app.home');
             }
         });
        }
        
       $scope.redirect = function(){
       $state.go('appSimple.register');
       localStorage.currentUser = { username: login.username, token: result.data };//save current user name in a variable
}
    }
    
    

})();

