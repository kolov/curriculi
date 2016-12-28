civiModule.controller("DevCtrl", function ($scope, $http, $modal, $log, formattersService,
                                           currentService) {

    $scope.cssContent = "";
    $scope.cssId = "";

    $http.get('/current/inputcss').success(function (data, d) {
      $scope.cssContent = data;
    });

    $scope.applyCss = function () {
      currentService.updateCss({},
        {content: $scope.cssContent},
        function () {
          document.getElementById('editor-frame').contentWindow.location.reload();
        });
    }
  }
)
;
