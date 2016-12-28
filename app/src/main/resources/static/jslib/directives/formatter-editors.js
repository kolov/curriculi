
civiModule.directive('inputCssEditor', function () {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: '/directives/edit-input-css.html'
  }
});

civiModule.directive('outputCssEditor', function () {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: '/directives/edit-output-css.html'
  }
});

civiModule.directive('hintsEditor', function () {
  return {
    restrict: 'E',
    replace: true,
    templateUrl: '/directives/edit-hints.html'
  }
});

