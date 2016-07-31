civiModule.controller("IndexCtrl",
  function ($scope, $http, $log, _, formattersService, servicecvs, $modal, usersService, examplesService) {


    $scope.xsds = [];
    $scope.publicXsds = [];
    $scope.privateXsds = [];
    $scope.groupXsds = {};
    $scope.cvs = [];
    $scope.demoCvs = [];
    $scope.examplePdfs = {};
    $scope.initedExamplePdfs = false;


    function refreshXsds() {

      function isGroup(xsd) {
        return xsd['group'] && xsd['group'] !== 'welcome';
      }

      formattersService.query({
        fields: 'id,name,group,private',
        sort: 'seq',
        type: 'xsd'
      }, function (data) {
        $scope.xsds = data;
        $scope.privateXsds = _.filter(data, function (x) {
          return x['private'];
        });
        $scope.publicXsds = _.filter(data, function (x) {
          return !x['private'] && x['group'] == 'welcome';
        });
        _.forEach(data, function (x) {

          if (isGroup(x)) {
            var name = x['group'];
            var g = $scope.groupXsds[name];
            if (g) {
              g.xsds.push(x);
            } else {
              $scope.groupXsds[name] = {name: name, xsds: [x]};
            }
          }
        });
        if (!$scope.initedExamplePdfs) {
          $scope.initedExamplePdfs = true;

          examplesService.query({}, function (data) {
            _.forEach(data, function (x) {
              if (x.demo && x.demo.id_xsd) {
                var found = $scope.examplePdfs[x.demo.id_xsd];
                if (!found) {
                  found = [];
                  $scope.examplePdfs[x.demo.id_xsd] = found;
                }
                found.push(x.id);
              }
            });
          });
        }
      });
    }

    refreshXsds();


    $scope.hasGroupXsds = function () {
      return !_.isEmpty($scope.groupXsds);
    };

    $scope.hasPrivateXsds = function () {
      return !_.isEmpty($scope.privateXsds);
    };


    function refreshCvs() {
      $scope.cvs = servicecvs.query({
          fields: 'id,name',
          sort: 'created'
        },
        function (data) {
          $scope.cvs = _.filter(data, function(cv) {return !cv['demo']});
          $scope.demoCvs = _.filter(data, function(cv) {return !!cv['demo']});
        }
      );
    }

    refreshCvs();
    $scope.$on('updatedXsds', function () {
      refreshXsds();
    });


    $scope.user = {};
    usersService.query(function (data) {
      $scope.user = data;
    });

    $scope.mayEditTemplates = true;

    $scope.errorHandler = function () {
      $scope.showError("error");
    };


    $scope.deleteCv = function (ix) {
      servicecvs.delete({id: $scope.cvs[ix].id},
        function () {
          refreshCvs();
        }, $scope.errorHandler);
    };


    $scope.createGroup = function () {
      var modalInstance = $modal.open({
        templateUrl: 'static/jslib/html/add-group.html',
        controller: function ($scope, $modalInstance) {
          $scope.ok = function () {
            $modalInstance.close();
          };

          $scope.cancel = function () {
            $modalInstance.dismiss();
          };
        },
        size: 'sm',
        resolve: {
          existingNames: function () {
            return _.map($scope.groupXsds, function (x) {
              return x['name']
            });
          }
        }

      });

      modalInstance.result.then(function () {
        $scope.deleteCv(ix);
      }, function () {
      });


    }
    ;
    $scope.deleteCvDlg = function (ix) {

      var modalInstance = $modal.open({
        templateUrl: 'static/jslib/html/confirm.html',
        controller: 'ConfirmController',
        size: 'sm',
        resolve: {
          title: function () {
            return 'Delete CV';
          },
          question: function () {
            return 'Are you sure you want to delete this CV (<i>' + $scope.cvs[ix].name +
              '</i>)? This process is irreversible!!!';
          }
        }
      });

      modalInstance.result.then(function () {
        $scope.deleteCv(ix);
      }, function () {
      });

    };

    $scope.editCv = function (id) {
      window.location = "edit-old/" +  id;
    };

    $scope.showCv = function (id) {
      window.location = "doc/pdf/" +  id;
    };


    $scope.showError = function (text) {
      addError(text);
    };


  }
)
;
