ui = {};

ui.util = {
	table : {
		prepareData : function($defer, $filter, params, data) {

			data = params.filter() ? $filter('filter')(data, params.filter()) : data;

			data = params.sorting() ? $filter('orderBy')
					(data, params.orderBy()) : data;
			params.total(data.length);

			$defer.resolve(data.slice((params.page() - 1) * params.count(),
					params.page() * params.count()));
		}
	},
	image : {
		addImage : function(image, sex) {
			$("#imgDiv").append(ui.util.image.processImage(image, sex));
		},
		processImage : function(image, sex) {
			if (image instanceof Object) { //Image missing, show default
				var imgSrc = sex === "FEMALE" ? "jane_doe_test.png" : "john_doe_test.png";
				return '<img src="../../images/' + imgSrc + '">';
			}
			else {
				return '<img src="data:image/png;base64,' + image.replace(/\"/g, "") + '">'; //regex to remove quotes
			}
		}
	}
}