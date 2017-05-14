angular
.module('app')
.config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$breadcrumbProvider', function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $breadcrumbProvider) {
  $stateProvider
  .state('app.icons', {
    url: "/icons",
    abstract: true,
    template: '<ui-view></ui-view>',
    ncyBreadcrumb: {
      label: 'Icons'
    }
  })
  .state('app.icons.fontawesome', {
    url: '/font-awesome',
    templateUrl: 'views/icons/font-awesome.html',
    ncyBreadcrumb: {
      label: 'Font Awesome'
    }
  })
  .state('app.icons.simplelineicons', {
    url: '/simple-line-icons',
    templateUrl: 'views/icons/simple-line-icons.html',
    ncyBreadcrumb: {
      label: 'Simple Line Icons'
    }
  })
  .state('app.components', {
    url: "/components",
    abstract: true,
    template: '<ui-view></ui-view>',
    ncyBreadcrumb: {
      label: 'Components'
    }
  })
  .state('app.components.buttons', {
    url: '/buttons',
    templateUrl: 'views/components/buttons.html',
    ncyBreadcrumb: {
      label: 'Buttons'
    }
  })
  .state('app.components.social-buttons', {
    url: '/social-buttons',
    templateUrl: 'views/components/social-buttons.html',
    ncyBreadcrumb: {
      label: 'Social Buttons'
    }
  })
  .state('app.components.cards', {
    url: '/cards',
    templateUrl: 'views/components/cards.html',
    ncyBreadcrumb: {
      label: 'Cards'
    }
  })
  .state('app.components.forms', {
    url: '/forms',
    templateUrl: 'views/components/forms.html',
    ncyBreadcrumb: {
      label: 'Forms'
    }
  })
  .state('app.reports', {
    url: '/reports',
    templateUrl: 'views/pages/reports.html',
      
    ncyBreadcrumb: {
      label: 'Reports'
    }
  })
  .state('app.documents', {
    url: '/documents',
    templateUrl: 'views/pages/documents.html',
    controller:'documentController',  
    ncyBreadcrumb: {
      label: 'Documents'
    }
  })
  .state('app.track', {
    url: '/track',
    templateUrl: 'views/pages/track.html',
    controller:'trackController', 
    ncyBreadcrumb: {
      label: 'Track'
    }
  })
  .state('app.schedule', {
    url: '/schedule',
    templateUrl: 'views/pages/schedule.html',
    controller:'scheduleController', 
    ncyBreadcrumb: {
      label: 'Schedule'
    }
  })
  .state('app.floorPlans', {
    url: '/floorPlans',
    templateUrl: 'views/pages/floorPlans.html',
      
    ncyBreadcrumb: {
      label: 'foorPlan'
    }
  })
  .state('app.home', {
    url: '/home',
    templateUrl: 'views/pages/home.html',
    controller:'homeController', 
    ncyBreadcrumb: {
      label: 'Home'
    }
  })
  .state('app.userProfile', {
    url: '/userProfile',
    templateUrl: 'views/userProfile.html',
      
    ncyBreadcrumb: {
      label: 'Reports'
    }
  })
  .state('app.forms', {
    url: '/forms',
    templateUrl: 'views/forms.html',
    ncyBreadcrumb: {
      label: 'Forms'
    },
    resolve: {
      loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
        return $ocLazyLoad.load([
          {
            serie: true,
            files: ['js/libs/moment.min.js']
          },
          {
            serie: true,
            files: ['js/libs/daterangepicker.min.js', 'js/libs/angular-daterangepicker.min.js']
          },
          {
            files: ['js/libs/mask.min.js']
          },
          {
            files: ['js/libs/select.min.js']
          }
        ]);
      }],
      loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
        // you can lazy load files for an existing module
        return $ocLazyLoad.load({
          files: ['js/controllers/forms.js']
        });
      }]
    }
  })
 
  
}]);
