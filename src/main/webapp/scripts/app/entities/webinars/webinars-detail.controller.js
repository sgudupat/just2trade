'use strict';

angular.module('just2tradeApp')
    .controller('WebinarsDetailController', function ($scope, $rootScope, $stateParams, entity, Webinars) {
        $scope.webinars = entity;
        $scope.load = function (id) {
            Webinars.get({id: id}, function(result) {
                $scope.webinars = result;
            });
        };
        var unsubscribe = $rootScope.$on('just2tradeApp:webinarsUpdate', function(event, result) {
            $scope.webinars = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
