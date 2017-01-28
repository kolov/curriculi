var underscore = angular.module('underscore', []);
underscore.factory('_', function () {
  return window._; // assumes underscore has already been loaded on the page
});


var civiModule = angular.module("civi", ['ngResource',
  'ui.codemirror',
  'ui.bootstrap',
  'ui.bootstrap.modal',
  'ngSanitize',
  'uiSwitch',
  'angularLoad',
  'ui.router',
  'underscore']);

civiModule
  .config(['$sceDelegateProvider', function ($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist(
      ['self',
        'https://www.google.com/**']);
  }]);

civiModule.config(['$httpProvider', function ($httpProvider) {
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

civiModule
  .factory('servicecss', ['$resource', function ($resource) {
    return $resource('/servicecsss', {});
  }]);

civiModule.factory('formattersService', ['$resource', function ($resource) {
  return $resource('/v1/formatters/:id/:action', {},
    {
      delete: {
        method: 'DELETE',
        params: {
          id: '@id'
        }
      },
      'query': {
        method: 'GET',
        params: {
          fields: '@fields',
          sort: '@sort',
          type: '@type'
        },
        isArray: true
      },
      clone: {
        method: 'POST',
        params: {
          id: '@id',
          action: 'clone'
        }
      },
      get: {
        method: 'GET',
        params: {
          id: '@id'
        }
      },
      update: {
        method: 'PUT',
        params: {
          id: '@id'
        }
      }

    });

}]);


civiModule.factory('servicecvs', ['$resource', function ($resource) {
  return $resource('/service/cvs/:id/:action', {},
    {
      delete: {
        method: 'DELETE',
        params: {
          id: '@id'
        }
      },
      'query': {
        method: 'GET',
        params: {
          fields: '@fields',
          sort: '@sort'
        },
        isArray: true
      },
      clone: {
        method: 'POST',
        params: {
          id: '@id',
          action: 'clone'
        }
      },
      get: {
        method: 'GET',
        params: {
          id: '@id'
        }
      },
      update: {
        method: 'PUT',
        params: {
          id: '@id'
        }
      }

    });
}]);

civiModule.factory('currentFormatterService', ['$resource', function ($resource) {
  return $resource('/current/formatter-changed/:formatterType/:id', {},
    {
      'formatterChanged': {
        method: 'PUT',
        params: {
          formatterType: '@formatterType',
          id: '@id'
        }
      }
    });
}]);
civiModule.factory('currentDocumentService', ['$resource', function ($resource) {
  return $resource('/current/document', {},
    {
      'query': {isArray: false}
    });
}]);

civiModule.factory('currentService', ['$resource', function ($resource) {
  return $resource('/current/:type', {},
    {
      'inputcss': {
        method: 'GET',
        isArray: false,
        transformResponse: [function (x) {
          return x;
        }],
        responseType: 'text/css',
        params: {
          type: 'inputcss'
        }
      },
      'updateCss': {
        method: 'PUT',
        params: {
          type: 'inputcss'
        }
      }
    });
}]);

civiModule.factory('usersService', ['$resource', function ($resource) {
  return $resource('/v1//user', {},
    {
      'query': {isArray: false}
    });
}]);

civiModule.factory('acceptCookieService', ['$resource', function ($resource) {
  return $resource('/user/accepts-cookies', {},
    {
      'accept': {method: 'POST'}
    });
}]);

civiModule.factory('examplesService', ['$resource', function ($resource) {
  return $resource('/v1/examples', {},
    {
      'query': {isArray: true}
    });
}]);


civiModule.factory('logoutService', ['$resource', function ($resource) {
  return $resource('/logout', {},
    {
      'logout': {method: 'POST'}
    });
}]);
civiModule.factory('loginService', ['$resource', function ($resource) {
  return $resource('/login', {},
    {
      'login': {method: 'POST'}
    });
}]);




civiModule.factory('envService', ['$resource', function ($resource) {
  return $resource('/v1/env', {},
    {
      'query': {isArray: false}
    });
}]);

civiModule.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.useXDomain = true;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

civiModule.controller("TemplateCtrl", function ($scope, $rootScope, usersService) {
    $scope.user = {};
    usersService.query(function (data) {
      $scope.user = data;
    });

    $scope.alerts = [];

    $scope.addError = function (text) {
      $scope.alerts.push({type: 'danger', msg: text});
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    $rootScope.$on('error', function (event, text) {
      $scope.addError(text);
    });
    $scope.$on('error', function (event, text) {
      $scope.addError(text);
    });

    $scope.showDevelopmentWarning = true;

    $scope.onDocumentChanged = function () {
      $scope.$broadcast('changed');
    };

    $scope.getLocation = function() {
      return window.location.href;
    };

  }
);







