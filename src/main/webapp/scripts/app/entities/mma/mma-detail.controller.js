'use strict';

angular.module('just2tradeApp')
    .controller('MmaDetailController', function ($scope, $rootScope, $stateParams, entity, Mma) {
        $scope.mma = entity;
        $scope.load = function (id) {
            Mma.get({id: id}, function(result) {
                $scope.mma = result;
            });
        };
        var unsubscribe = $rootScope.$on('just2tradeApp:mmaUpdate', function(event, result) {
            $scope.mma = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
