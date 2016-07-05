'use strict';

angular.module('just2tradeApp').controller('WebinarsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Webinars',
        function($scope, $stateParams, $uibModalInstance, entity, Webinars) {

        $scope.webinars = entity;
        $scope.load = function(id) {
            Webinars.get({id : id}, function(result) {
                $scope.webinars = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('just2tradeApp:webinarsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.webinars.id != null) {
                Webinars.update($scope.webinars, onSaveSuccess, onSaveError);
            } else {
                Webinars.save($scope.webinars, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
