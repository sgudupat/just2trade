'use strict';

angular.module('just2tradeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('webinars', {
                parent: 'entity',
                url: '/webinarss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.webinars.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/webinars/webinarss.html',
                        controller: 'WebinarsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('webinars');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('webinars.detail', {
                parent: 'entity',
                url: '/webinars/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.webinars.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/webinars/webinars-detail.html',
                        controller: 'WebinarsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('webinars');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Webinars', function($stateParams, Webinars) {
                        return Webinars.get({id : $stateParams.id});
                    }]
                }
            })
            .state('webinars.new', {
                parent: 'webinars',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/webinars/webinars-dialog.html',
                        controller: 'WebinarsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fullName: null,
                                    mobile: null,
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('webinars', null, { reload: true });
                    }, function() {
                        $state.go('webinars');
                    })
                }]
            })
            .state('webinars.edit', {
                parent: 'webinars',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/webinars/webinars-dialog.html',
                        controller: 'WebinarsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Webinars', function(Webinars) {
                                return Webinars.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('webinars', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('webinars.delete', {
                parent: 'webinars',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/webinars/webinars-delete-dialog.html',
                        controller: 'WebinarsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Webinars', function(Webinars) {
                                return Webinars.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('webinars', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
