civiModule.directive('helloUser', function () {
  return {
    restrict: 'E',
    controller: function ($scope, logoutService, usersService) {
      $scope.logout = function () {
        logoutService.logout({},
          function () {
            $scope.user = usersService.query()
          });
      };
      $scope.providers = {
        google: "https://www.googleapis.com/plus/v1/people/me/openIdConnect",
        yahoo: "http://me.yahoo.com",
        wordpress: "http://username.wordpress.com"
      };

      $scope.login = function (provider) {
        // not using $resource or $http because of CORS issues

        document.forms['login-form-' + provider].submit();
      };

    },
    scope: {
      user: '='
    },
    templateUrl: '/directives/hello-user.html'
  };

});
