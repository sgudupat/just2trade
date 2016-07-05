'use strict';

angular.module('just2tradeApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('just2tradeApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });