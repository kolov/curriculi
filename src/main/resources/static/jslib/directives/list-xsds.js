civiModule.directive('listXsds', function () {
  return {
    restrict: 'E',
    controller: function ($scope, $modal, $log, formattersService, envService, angularLoad) {

      envService.query({}, function (data) {
        $scope.env = data['env'];
      });

      function emitUpdateEvent() {
        $scope.$emit('updatedXsds');
      }


      $scope.$on('error', function (event, text) {
        $scope.addError(text);
      });

      $scope.editXsdDlg = function (ix) {
        angularLoad.loadScript('/static/jslib/codemirror/codemirror-compressed.js').then(function () {
          editXsdDlg($scope, ix, true);
        });
      };

      $scope.viewXsdDlg = function (ix) {
        angularLoad.loadScript('/static/jslib/codemirror/codemirror-compressed.js').then(function () {
          editXsdDlg($scope, ix, false);
        });
      };

      function editXsdDlg($scope, ix, editable) {
        formattersService.get({id: $scope.xsds[ix].id},
          function (data) {
            var modalInstance = $modal.open({
              templateUrl: 'static/jslib/html/edit-xsd.html',
              controller: 'EditFormatterController',
              windowClass: 'app-modal-window',
              resolve: {
                formatter: function () {
                  return {
                    id: $scope.xsds[ix].id,
                    content: data.content,
                    name: data.name,
                    editable: editable
                  };
                }
              }
            });

            modalInstance.result.then(emitUpdateEvent, emitUpdateEvent);

          },
          function (data) {
            alert("Error: " + data)
          });
      };

      $scope.deleteXsd = function (ix) {
        formattersService.delete({id: $scope.xsds[ix].id},
          function () {
            emitUpdateEvent();
          }, $scope.showError);
      };

      $scope.editNew = function (id) {
        window.location.href = '/edit-new/' + id;
      };

      $scope.deleteXsdDlg = function (ix) {

        var modalInstance = $modal.open({
          templateUrl: 'static/jslib/html/confirm.html',
          controller: 'ConfirmController',
          size: 'sm',
          resolve: {
            title: function () {
              return 'Delete Template';
            },
            question: function () {
              return 'Are you sure you want to delete template <i>' + $scope.xsds[ix].name +
                '</i>? This process is irreversible!!!<p>Note that at the moment the application is NOT checking if ' +
                'this template is being used by a document. ' +
                '</p>';
            }
          }
        });

        modalInstance.result.then(function (data) {
          $scope.deleteXsd(ix);
        }, function () {

        });
      };


      $scope.cloneXsd = function (ix) {
        formattersService.clone({id: $scope.xsds[ix].id},
          function () {
            emitUpdateEvent();
          },
          $scope.showError);
      };

      $scope.cloneXsdDlg = function (ix) {

        var modalInstance = $modal.open({
          templateUrl: 'static/jslib/html/confirm.html',
          controller: 'ConfirmController',
          size: 'sm',
          resolve: {
            title: function () {
              return 'Clone Template';
            },
            question: function () {
              return '<p>You can clone any template' +
                ', edit it and use it as a template for CVs containing other information' +
                ' (e.g., require list of languagaes used for each project)</p>' +
                '<p>Cloned templates appear below the public templates.</p>' +
                '<p>Are you sure you want to clone <i>' + $scope.xsds[ix].name + '</i>?</p>';
            }
          }
        });

        modalInstance.result.then(function (data) {
          $scope.cloneXsd(ix);
        }, function () {

        });
      };


      $scope.showError = function (text) {
        $scope.$emit('error', 'Communication error');
      };

      $scope.openExample = function (id) {
        window.open('/pdf/' + id,
          'example', 'width=800, height=1200');
      };

      $scope.canEditXsd = function (user, ix) {
        var result = !!($scope.xsds[ix] && $scope.xsds[ix]['private']);
        return result;
      };

    },
    scope: {
      xsds: '=',
      user: '=',
      mayEditTemplates: '=',
      examples: '='
    },
    templateUrl: '/directives/list-xsds.html'
  };

});
