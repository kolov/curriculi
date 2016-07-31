var p = null;

civiModule.controller("EditorCtrl", function ($scope, $http, $q, $compile, $log) {


    $scope.promise = null;

    $scope.$on('addField', function (event, data) {
      $scope.addField(data.idParent, data.path);
    });
    $scope.$on('deleteField', function (event, data) {
      $scope.deleteField(data.idParent, data.path);
    });


    $scope.addField = function (idParent, path) {

      console.log("add field fuction called, $scope.promise=" + $scope.promise);
      console.log("idParenth=" + idParent);
      $q.when(p).then(function () {
          $http.post("/current/add-field", {path: path})
            .success(function (data) {
              var compiled = $compile(data)($scope);
              angular.element(document.getElementById(idParent)).replaceWith(compiled);
            })
            .error(function () {
              alert("Could not add field, sorry");
            })
        }
      );
      console.log("$scope.promise=" + $scope.promise);
    };

    $scope.deleteField = function (idParent, path, val) {
      $http.post("/current/delete-field", {path: path, val: val})
        .success(function (data, status, headers, config) {
          var compiled = $compile(data)($scope);
          angular.element(document.getElementById(idParent)).replaceWith(compiled);
        })
        .error(function (data, status, headers, config) { // Handle the error
          alert("Could not delete field, sorry");
        });
    };
  }
);


function onChanged(http, id, val, scope, q) {
  var deferred = q.defer();
  console.log("about to http changed");
  http.post("/current/changed", {id: id, value: val})
    .success(function () {
      console.log("changed returned");
      window.parent.angular.element(window.parent.document.getElementById('civi-template')).scope().$apply(
        function (scope) {
          scope.onDocumentChanged();
        }
      );
      deferred.resolve();
    })
    .error(function () {
      deferred.reject(new Error("error"));
    });
  console.log("change promise made");
  p = deferred.promise;
  console.log("scope=" + scope);
  console.log("scope.promise=" + scope.promise);
}




