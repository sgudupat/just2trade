'use strict';

angular.module('just2tradeApp')
    .controller('PartnersDetailController', function ($scope, $rootScope, $stateParams, entity, Partners) {
        $scope.partners = entity;
        $scope.load = function (id) {
            Partners.get({id: id}, function(result) {
                $scope.partners = result;
            });
        };
        var unsubscribe = $rootScope.$on('just2tradeApp:partnersUpdate', function(event, result) {
            $scope.partners = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
