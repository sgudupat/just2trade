'use strict';

angular.module('just2tradeApp')
	.controller('WebinarsDeleteController', function($scope, $uibModalInstance, entity, Webinars) {

        $scope.webinars = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Webinars.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
