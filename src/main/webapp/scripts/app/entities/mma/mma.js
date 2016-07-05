'use strict';

angular.module('just2tradeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mma', {
                parent: 'entity',
                url: '/mmas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.mma.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mma/mmas.html',
                        controller: 'MmaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mma');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mma.detail', {
                parent: 'entity',
                url: '/mma/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.mma.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mma/mma-detail.html',
                        controller: 'MmaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mma');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Mma', function($stateParams, Mma) {
                        return Mma.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mma.new', {
                parent: 'mma',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mma/mma-dialog.html',
                        controller: 'MmaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    middleName: null,
                                    mobile: null,
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mma', null, { reload: true });
                    }, function() {
                        $state.go('mma');
                    })
                }]
            })
            .state('mma.edit', {
                parent: 'mma',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mma/mma-dialog.html',
                        controller: 'MmaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Mma', function(Mma) {
                                return Mma.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mma', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mma.delete', {
                parent: 'mma',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mma/mma-delete-dialog.html',
                        controller: 'MmaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Mma', function(Mma) {
                                return Mma.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mma', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
