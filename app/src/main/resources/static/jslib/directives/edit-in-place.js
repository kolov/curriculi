civiModule.directive('editInPlace', function () {
  return {
    restrict: 'E',
    scope: {
      originalValue: '=value',
      name: '@'
    },
    link: function ($scope, element, attrs) {


      $scope.$watch('originalValue', function () {
        $scope.localValue = $scope.originalValue;
      });

      $scope.$on('edit-in-place-rejected', function () {
        $scope.localValue = $scope.originalValue;
      });

      $scope.editing = false;

      var inputElement = angular.element(element.children()[1]);

      element.addClass('edit-in-place');


      $scope.edit = function () {
        $scope.editing = true;
        element.addClass('active');
        inputElement[0].focus();
      };

      $scope.keyPressed = function (evt) {
        var code = evt.keyCode || evt.which;
        if (code === 13) {
          event.preventDefault();
          $scope.finishEditing();
        }
      };

      $scope.finishEditing = function () {
        $scope.editing = false;
        element.removeClass('active');
        $scope.$emit('edited-in-place', {name: $scope.name, value: $scope.localValue});
      };

      inputElement.bind('blur', $scope.finishEditing);
      inputElement.bind('keydown keypress', $scope.keyPressed);

    },
    template: '<div ng-click="edit()" ng-bind="localValue"></div><input ng-model="localValue"></input>'
  };

});