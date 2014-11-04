ui = {};

ui.init = function() {
	ui.navbar.init();
}

ui.navbar = {
	init : function() {
		$('.navbar [data-toggle="popover"]').each(function() {
			ui.navbar.initPopover($(this));
		});
	},
	initPopover : function($element) {
		$element.popover({
			html : true,
			trigger : 'manual',
			content : function() {
				return $($element.attr('data-target')).html();
			}
		}).closest('li').mouseenter(function() {
			var timer = $element.data('popover-timer');
			if (timer) {
				clearTimeout(timer);
				$element.removeData('popover-timer');
			} else {
				$element.popover('show');
				angular.element(document).injector().invoke(function($compile) {
					var content = $(".popover-content");
					var scope = angular.element(content).scope();
				    $compile(content)(scope);
				});
			}
		}).mouseleave(function() {
			var timer = $element.data('popover-timer');
			if (timer || $('.ui-autocomplete.ui-widget:visible').length > 0) {
				return;
			}
			var timer = setTimeout(function() {
				$element.popover('hide');
				$element.removeData('popover-timer');
			}, 500);
			$element.data('popover-timer', timer);
		});
	}
}
ui.util = {
	table : {
		prepareData : function($defer, $filter, params, data) {
			data = params.filter() ? $filter('filter')(data, params.filter())
					: data;
			data = params.sorting() ? $filter('orderBy')
					(data, params.orderBy()) : data;
			params.total(data.length);
			$defer.resolve(data.slice((params.page() - 1) * params.count(),
					params.page() * params.count()));
		}
	},
	image : {
		addImage : function(location, image, sex) {
			$(location).append(ui.util.image.processImage(image, sex));
		},
		processImage : function(image, sex) {
			if (image instanceof Object) { // Image missing, show default
				var imgSrc = sex === "FEMALE" ? "jane_doe_test.png"
						: "john_doe_test.png";
				return '<img class="setImgSize img-rounded" src="../../images/' + imgSrc
						+ '">';
			} else {
				return '<img class="setImgSize img-rounded" src="data:image/png;base64,'
						+ image.replace(/\"/g, "") + '">'; // regex to remove quotes
			}
		}
	}
}

$(function(){ 
	ui.init();
});
