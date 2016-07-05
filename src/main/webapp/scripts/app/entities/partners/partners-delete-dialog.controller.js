'use strict';

angular.module('just2tradeApp')
	.controller('PartnersDeleteController', function($scope, $uibModalInstance, entity, Partners) {

        $scope.partners = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Partners.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
