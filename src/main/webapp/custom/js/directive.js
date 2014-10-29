(function() {
	var app = angular.module('crucem-elit');
	app.directive('msg', function() {
		return {
			restrict : 'EA',
			link : function(scope, element, attrs) {
				var key = attrs.key;
				if (attrs.keyExpr) {
					scope.$watch(attrs.keyExpr, function(value) {
						key = value;
						element.text($.i18n.prop(value));
					});
				}
				scope.$watch('language()', function(value) {
					element.text($.i18n.prop(key));
				});
			}
		};
	});
	
	app.directive("autoComplete", ["$location", "autoCompleteService", function ($location, autoCompleteService) {
	    return {
	        restrict: "A",
	        link: function (scope, elem, attr, ctrl) {
	            elem.autocomplete({
	                source: function (searchTerm, response) {
	                    autoCompleteService.getSuggestions(searchTerm.term).then(function (autoCompleteResults) {
	                        response($.map(autoCompleteResults, function (result) {
	                            return {
	                                label: result.text,
	                                type: result.type,
	                                id: result.id 
	                            }
	                        }))
	                    });
	                },
	                minLength: 2,
	                delay: 500,
	                select: function (event, selected) {
	                	$location.path('/user/' + selected.item.type +'/' + selected.item.id);
	                	$('.navbar [data-toggle="popover"]').popover('hide');
	                	scope.$apply();
	                    event.preventDefault();
	                    scope.search = '';
	                }
	            });
	        }
	    };
	}]);
}());