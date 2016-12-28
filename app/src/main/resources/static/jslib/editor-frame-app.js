var civiModule = angular.module("civi", ['ngResource',
    'ui.bootstrap',
    'ui.bootstrap.modal',
    'ngSanitize']);


civiModule
  .config(['$sceDelegateProvider', function ($sceDelegateProvider) {
      $sceDelegateProvider.resourceUrlWhitelist(
        ['self',
            'https://www.google.com/**']);
  }]);

civiModule.config(['$httpProvider', function ($httpProvider) {
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);








