
function linkInput(http, q) {
  return function (scope, element) {
    var input = element[0];
    angular.element(input).bind('blur', function (evt) {
      if (scope.valueOnEnter != input.value) {
        onChanged(http, input.id, input.value, scope, q);
      }
    });
    angular.element(input).bind('focus', function () {
      scope.valueOnEnter = input.value;
    });
  }
}

function abstractInput(http, q, url) {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      value: '@'
    },

    templateUrl: url,
    link: linkInput(http, q)
  };
}
civiModule.directive('ciInputString', function ($http, $q) {
  return abstractInput($http, $q, '/directives/ciInputString.html');
});

civiModule.directive('ciInputDate', function ($http, $q) {
  return abstractInput($http, $q, '/directives/ciInputDate.html');
});

civiModule.directive('ciInputTextarea', function ($http, $q) {
  return abstractInput($http, $q, '/directives/ciInputTextarea.html');
});


civiModule.directive('ciEntry', function ($http, $q) {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      name: '@',
      labelAdd: '@',
      labelDelete: '@',
      label: '@',
      path: '@',
      type: '@',
      value: '@',
      idParent: '@'
    },
    controller: function ($scope) {
      $scope.addField = function () {
        $scope.$emit("addField", {idParent: $scope.idParent, path: $scope.path});
      };
      $scope.deleteField = function () {
        $scope.$emit("deleteField", {idParent: $scope.idParent, path: $scope.path});
      };
    },
    templateUrl: '/static/directives/ci-entry.html'
  }
});

