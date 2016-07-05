 'use strict';

angular.module('just2tradeApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-just2tradeApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-just2tradeApp-params')});
                }
                return response;
            }
        };
    });
