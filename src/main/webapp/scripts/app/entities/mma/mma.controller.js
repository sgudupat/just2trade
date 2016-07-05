'use strict';

angular.module('just2tradeApp')
    .controller('MmaController', function ($scope, $state, Mma) {

        $scope.mmas = [];
        $scope.loadAll = function() {
            Mma.query(function(result) {
               $scope.mmas = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.mma = {
                firstName: null,
                lastName: null,
                middleName: null,
                mobile: null,
                email: null,
                id: null
            };
        };
    });
