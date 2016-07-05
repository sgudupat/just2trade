'use strict';

angular.module('just2tradeApp')
    .controller('WebinarsController', function ($scope, $state, Webinars) {

        $scope.webinarss = [];
        $scope.loadAll = function() {
            Webinars.query(function(result) {
               $scope.webinarss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.webinars = {
                fullName: null,
                mobile: null,
                email: null,
                id: null
            };
        };
    });
