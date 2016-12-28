civiModule.controller("ConfirmController", function ($scope, $modalInstance, title, question) {
  $scope.title = title;
  $scope.question = question;

  $scope.ok = function () {
    $modalInstance.close();
  };

  $scope.cancel = function () {
    $modalInstance.dismiss();
  };
});