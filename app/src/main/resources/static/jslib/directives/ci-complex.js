civiModule.directive('ciComplex', function () {
  return {
    restrict: 'C',
    replace: true,
    transclude: true,
    scope: {
      title: '@',
      labelAdd: '@',
      labelDelete: '@',
      idParent: '@',
      path: '@',
      name: '@'
    },
    controller: function ($scope) {

      $scope.opened = true;

      $scope.toggle = function () {
        $scope.opened = !$scope.opened;
      };

      $scope.addField = function () {
        $scope.$emit("addField", {idParent: $scope.idParent, path: $scope.path});
      };
      $scope.deleteField = function () {
        $scope.$emit("deleteField", {idParent: $scope.idParent, path: $scope.path});
      };
      $scope.getClasses = function () {
        return "ci-complex-" + $scope.name
        + " "
        + ($scope.opened ? "opened" : "closed");
      }
    },

    templateUrl: '/directives/ci-complex.html'
  }
});
