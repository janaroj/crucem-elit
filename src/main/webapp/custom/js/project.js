$(function() {

	ui = {};

	ui.util = {
		table : {
			prepareData : function($defer, $filter, params, data) {

				data = params.filter() ? $filter('filter')(data,
						params.filter()) : data;

				data = params.sorting() ? $filter('orderBy')(data,
						params.orderBy()) : data;
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
					return '<img class="setImgSize" src="../../images/'
							+ imgSrc + '">';
				} else {
					return '<img class="setImgSize" src="data:image/png;base64,'
							+ image.replace(/\"/g, "") + '">'; // regex to
					// remove quotes
				}
			}
		}
	}

	$(".pop").popover({
		trigger : "manual",
		html : true,
		animation : false
	}).on("mouseenter", function() {
		var _this = this;
		$(this).popover("show");
		$(".popover").on("mouseleave", function() {
			$(_this).popover('hide');
		});
	}).on("mouseleave", function() {
		var _this = this;
		setTimeout(function() {
			if (!$(".popover:hover").length) {
				$(_this).popover("hide");
			}
		}, 300);
	});

});