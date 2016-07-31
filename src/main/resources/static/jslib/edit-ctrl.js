civiModule.controller("EditCtrl", function ($scope, $http, $modal, $log, formattersService,
                                            currentDocumentService, currentFormatterService, angularLoad) {

    $scope.dirty = false;
    $scope.showFormatterEditors = true;
    $scope.activeDoc = null;
    $scope.xmldoc = null;
    $scope.showPreview = false;
    $scope.editingInProgress = false;

    currentDocumentService.query(
      function (data) {
        $scope.isNew = data.id === null;
      }
    );


    $scope.toggleShowFormatterEditors = function () {
      $scope.showFormatterEditors = !$scope.showFormatterEditors;
    };

    $scope.onDocumentChanged = function () {
      $scope.dirty = true;
      if ($scope.showPreview) {
        document.getElementById('formatted-frame').contentWindow.location.reload();
      }
    };

    $scope.$on('changed', $scope.onDocumentChanged);

    $scope.$on('edited-in-place', function (event, data) {
      if (data.name == 'xmldoc-name' && data.value != $scope.xmldoc.name) {
        $http.put('/current/update-xmldoc', {name: data.value})
          .success(function () {
            $scope.xmldoc.name = data.value;
          })
          .error(function () {
            alert("Could not save document, sorry");
            $scope.$broadcast('edit-in-place-rejected');
          });
      }

    });


    $scope.inputCssDocs = {
      url: '/v1/formatters/css-in',
      documentType: 'css-in',
      formatterLongName: ' Input CSS',
      editMode: 'css',
      all: [],
      current: null,
      applySelectionChange: function () {
        document.getElementById('editor-frame').contentWindow.location.reload();
      },
      applyContentChange: function (content) {
        document.getElementById('editor-frame').contentWindow.applyCss(content);
      }

    };

    $scope.outputCssDocs = {
      url: '/v1/formatters/css-out',
      editMode: 'css',
      documentType: 'css-out',
      formatterLongName: ' Output CSS',
      all: [],
      current: null,
      applySelectionChange: function () {
        if ($scope.showPreview) {
          document.getElementById('formatted-frame').contentWindow.location.reload();
        }
      }
    };

    $scope.hintsDocs = {
      url: '/hintss',
      editMode: 'properties',
      documentType: 'hints',
      formatterLongName: ' Hints',
      all: [],
      current: null,
      applySelectionChange: function () {
        if ($scope.showPreview) {
          document.getElementById('formatted-frame').contentWindow.location.reload();
        }
        document.getElementById('editor-frame').contentWindow.location.reload();
      }

    };


    $scope.codeMirrorOptions = {
      lineWrapping: false,
      lineNumbers: true,
      mode: 'css'
    };

    $scope.togglePreview = function () {
      $scope.showPreview = !$scope.showPreview;
    }

    $scope.pullDocuments = function (desc, selectedId) {
      formattersService.query({
          type: desc.documentType,
          fields: 'id,name,selected,private,group'
        },
        function (data) {
          desc.all = data;
          var filtered = data.filter(function (e) {
            return selectedId ?
            e.id === selectedId
              : e.selected == true;
          });
          desc.current = filtered[0];
        })
    };

    $scope.pullDocuments($scope.inputCssDocs);
    $scope.pullDocuments($scope.outputCssDocs);
    $scope.pullDocuments($scope.hintsDocs);


    $scope.cloneDoc = function (desc) {
      formattersService.clone({
          id: desc.current.id
        },
        function (data) {
          $scope.pullDocuments(desc, data.id);
          formatterChanged(desc);
        });
    };


    $scope.deleteFormatterDlg = function (desc) {

      // find an id to use
      var newSelectedId = null;
      for (var i = 0; i < desc.all.length; i++) {
        if (desc.all[i].id !== desc.current.id) {
          newSelectedId = desc.all[i].id;
          break;
        }
      }
      if (!newSelectedId) {
        alert('Cant delete the last one');
        return;
      }

      var modalInstance = $modal.open({
        templateUrl: '/static/jslib/html/confirm.html',
        controller: 'ConfirmController',
        size: 'sm',
        resolve: {
          title: function () {
            return 'Delete ' + desc.formatterLongName;
          },
          question: function () {
            return 'Are you sure you want to delete template <i>' + desc.current.name +
              '</i>? This process is irreversible!!!<p>Note that at the moment the application is NOT checking if ' +
              'this template is being used by a document. ' +
              '</p>';
          }
        }
      });

      modalInstance.result.then(function (data) {
        formattersService.delete({
            id: desc.current.id
          },
          function () {
            $scope.pullDocuments(desc, newSelectedId);
          });

      });

    };


    $scope.getXmldoc = function () {
      $http.get('/current/document')
        .success(function (data, status, headers, config) {
          $scope.xmldoc = data;
        }).error(function (data, status, headers, config) { // Handle the error
          $scope.showError(data);
        });
    };
    $scope.getXmldoc();


    $scope.viewDlg = function (desc) {
      angularLoad.loadScript('/static/jslib/codemirror/codemirror-compressed.js').then(function () {
        editDlg($scope, desc, false);
      });
    };

    $scope.editDlg = function (desc) {
      angularLoad.loadScript('/static/jslib/codemirror/codemirror-compressed.js').then(function () {
        editDlg($scope, desc, true);
      });
    };

    editDlg = function (scope, desc, editable) {
      formattersService.get({id: desc.current.id, fields: 'content'},
        function (data) {
          var modalInstance = $modal.open({
                templateUrl: '/static/jslib/html/edit-' + desc.documentType + '.html',
                controller: 'EditFormatterController',
                windowClass: 'app-modal-window',
                resolve: {
                  formatter: function () {
                    return {
                      id: desc.current.id,
                      content: data.content,
                      name: data.name,
                      editable: editable
                    };
                  },

                  editorOptions: {
                    lineWrapping: false,
                    lineNumbers: true,
                    mode: desc.editMode
                  }
                }
              }
            )
            ;

          modalInstance.result.then(function (data) {
            $scope.pullDocuments(desc);
            desc.applySelectionChange();
          }, function () {
            $scope.pullDocuments(desc);
          });

        },
        function (data) { // Handle the error
          alert("Error: " + data)
        });
    };


    $scope.formatterChanged = function (desc) {
      currentFormatterService.formatterChanged({
        formatterType: desc.documentType,
        id: desc.current.id
      }, function () {
        desc.applySelectionChange();
      }, function () {
        alert("Could not apply your selection, sorry");
      });
    };


    $scope.showError = function (text) {
      alert("Something went wrong: " + text);
    };

    $scope.canEdit = function (user, desc) {
      var result = !!(desc.current && desc.current['private']);
      return result;
    };

    $scope.showPermalink = function () {
      $http.get('/current/document')
        .success(function (data) {
          $modal.open({
            templateUrl: '/static/jslib/html/show-permalink.html',
            controller: function ($scope, $modalInstance) {
              $scope.close = function () {
                $modalInstance.close();
              };
              $scope.linkText = 'http://curricu.li/doc/pdf/' + data.id;
            }
          });
        }).error(function (data) {
          $scope.showError(data);
        });
    };

    $scope.downloadPdf = function () {
      $http.get('/current/document')
        .success(function (data) {
          window.location = '/doc/pdf/' + data.id;
        }
      ).error(function (data) {
          $scope.showError(data);
        });
    };

  }
)
;

