'use strict';

angular.module('just2tradeApp')
    .controller('PartnersController', function ($scope, $state, Partners) {

        $scope.partnerss = [];
        $scope.loadAll = function() {
            Partners.query(function(result) {
               $scope.partnerss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.partners = {
                fullName: null,
                mobile: null,
                email: null,
                comments: null,
                id: null
            };
        };
    });
