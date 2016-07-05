'use strict';

angular.module('just2tradeApp')
    .factory('Partners', function ($resource, DateUtils) {
        return $resource('api/partnerss/:id', {}, {
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
