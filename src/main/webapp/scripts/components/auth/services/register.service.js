'use strict';

angular.module('just2tradeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


