'use strict';

angular.module('just2tradeApp').controller('PartnersDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Partners',
        function($scope, $stateParams, $uibModalInstance, entity, Partners) {

        $scope.partners = entity;
        $scope.load = function(id) {
            Partners.get({id : id}, function(result) {
                $scope.partners = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('just2tradeApp:partnersUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.partners.id != null) {
                Partners.update($scope.partners, onSaveSuccess, onSaveError);
            } else {
                Partners.save($scope.partners, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
