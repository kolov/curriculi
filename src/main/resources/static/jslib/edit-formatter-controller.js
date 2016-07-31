civiModule.controller("EditFormatterController",
  function ($scope, $modalInstance, formatter, formattersService, $rootScope) {

    $scope.formatter = formatter;
    $scope.lastFormatterContent = formatter.content;
    $scope.lastFormatterName = formatter.name;

    $scope.redMessage = "";
    $scope.greenMessage = "";

    $scope.showError = function (text) {
      $scope.redMessage = text;
      $scope.greenMessage = "";
    };

    $scope.ok = function () {
      formattersService.update({id: $scope.formatter.id},
        {content: $scope.formatter.content},
        function () {
          $scope.lastFormatterContent = $scope.formatter.content;
          $modalInstance.close($scope.formatter.content);
        },
        function (resp) {
          $scope.showError(resp.data);
        }
      );

    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.rename = function () {
      formattersService.update({id: formatter.id},
        {name: $scope.formatter.name},
        function () {
          $scope.lastFormatterName = $scope.formatter.name;
        },
        function (data) {
          $scope.showError(data);
        }
      );
    };

    $scope.canRename = function () {
      return $scope.lastFormatterName != $scope.formatter.name && $scope.formatter.name;
    };

    $scope.canSave = function () {
      return $scope.lastFormatterContent != $scope.formatter.content && $scope.formatter.content;
    };

    $scope.codemirrorLoaded = function (_editor) {

      setTimeout(function () {
        _editor.focus();
        _editor.refresh();
      }, 100);
    };

  });