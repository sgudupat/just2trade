'use strict';

angular.module('just2tradeApp').controller('MmaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mma',
        function($scope, $stateParams, $uibModalInstance, entity, Mma) {

        $scope.mma = entity;
        $scope.load = function(id) {
            Mma.get({id : id}, function(result) {
                $scope.mma = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('just2tradeApp:mmaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mma.id != null) {
                Mma.update($scope.mma, onSaveSuccess, onSaveError);
            } else {
                Mma.save($scope.mma, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
