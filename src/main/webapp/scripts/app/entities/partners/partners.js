'use strict';

angular.module('just2tradeApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partners', {
                parent: 'entity',
                url: '/partnerss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.partners.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partners/partnerss.html',
                        controller: 'PartnersController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partners');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partners.detail', {
                parent: 'entity',
                url: '/partners/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'just2tradeApp.partners.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partners/partners-detail.html',
                        controller: 'PartnersDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partners');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Partners', function($stateParams, Partners) {
                        return Partners.get({id : $stateParams.id});
                    }]
                }
            })
            .state('partners.new', {
                parent: 'partners',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/partners/partners-dialog.html',
                        controller: 'PartnersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fullName: null,
                                    mobile: null,
                                    email: null,
                                    comments: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('partners', null, { reload: true });
                    }, function() {
                        $state.go('partners');
                    })
                }]
            })
            .state('partners.edit', {
                parent: 'partners',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/partners/partners-dialog.html',
                        controller: 'PartnersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Partners', function(Partners) {
                                return Partners.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partners', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('partners.delete', {
                parent: 'partners',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/partners/partners-delete-dialog.html',
                        controller: 'PartnersDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Partners', function(Partners) {
                                return Partners.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partners', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
