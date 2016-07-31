civiModule.directive('cookiesWarning', function () {
  return {
    restrict: 'E',
    controller: function ($scope, acceptCookieService) {
      $scope.acceptClicked = function () {
        acceptCookieService.accept(
          function (data) {
            $scope.user = data;
          }
        );
      };
      $scope.showWarning = function () {
        return !($scope.user && $scope.user['accepts-cookies']);
      };
    },

    scope: {
      user: '=user'
    },
    templateUrl: '/directives/cookies-warning.html'
  };
});
