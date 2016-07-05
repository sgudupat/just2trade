'use strict';

angular.module('just2tradeApp')
	.controller('MmaDeleteController', function($scope, $uibModalInstance, entity, Mma) {

        $scope.mma = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Mma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
