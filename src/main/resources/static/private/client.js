var app = angular.module("ClientManagment", []);

// DIRECTIVE - FILE MODEL
app.directive("fileModel", [
  "$parse",
  function ($parse) {
    return {
      restrict: "A",
      link: function (scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind("change", function () {
          scope.$apply(function () {
            modelSetter(scope, element[0].files[0]);
          });
        });
      },
    };
  },
]);


app.controller("ClientController", function ($scope, $http, $timeout) {
  $scope.form = {};
  $scope.views = {};
  $scope.views.list = true;
  showHideLoad(true);
  $scope.showDownloadModal = false;

  $scope.editSaveSubscription = function () {
    alert("hi")
  };


  $scope.excelDownload = function () {
    showHideLoad();
    $http({
      method: 'GET',
      url: 'api/system-info/download-info-collector',
      responseType: 'arraybuffer'

    }).then(function successCallback(response) {
      var headers = response.headers();
      var fileName = headers['content-disposition']
        .split(';')[1]
        .split('=')[1]
        .trim()
        .replace(/^"|"$/g, '');

      var blob = new Blob([response.data], { type: 'application/octet-stream' });
      saveAs(blob, fileName);
      showHideLoad(true);
    }, function errorCallback(response) {
      console.log(response.statusText);
    });
  }

  $scope.changeView = function (view) {
    if (view == "add" || view == "list" || view == "show") {
      $("#user-name-error").hide();
      $scope.form = {};
    }
    if (view == "edit") {
      $("#user-name-error-edit").hide();
    }
    $scope.views.add = false;
    $scope.views.edit = false;
    $scope.views.list = false;
    $scope.views[view] = true;
  };
});