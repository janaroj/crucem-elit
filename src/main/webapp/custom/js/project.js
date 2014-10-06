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
	}
}