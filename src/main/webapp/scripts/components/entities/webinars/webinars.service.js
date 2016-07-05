'use strict';

angular.module('just2tradeApp')
    .factory('Webinars', function ($resource, DateUtils) {
        return $resource('api/webinarss/:id', {}, {
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
