'use strict';

angular.module('just2tradeApp')
    .factory('Mma', function ($resource, DateUtils) {
        return $resource('api/mmas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
