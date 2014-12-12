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
	object : {
		path: function(obj, path, def){

		    for(var i = 0,path = path.split('.'),len = path.length; i < len; i++){
		        if(!obj || typeof obj !== 'object') return def;
		        obj = obj[path[i]];
		    }

		    if(obj === undefined) return def;
		    return obj;
		}
	},
	table : {
		prepareData : function($defer, $filter, params, data) {
			var dateFilterObject = {};
			//Remove empty filters
			for(var key in params.filter()) {
				if(!params.filter()[key]) {
					delete params.filter()[key];
					continue;
				}
				if (ui.util.isDateField(key)) {
					data = ui.util.filterDateField(data,params.filter()[key]);
					dateFilterObject[key] = params.filter()[key];
					delete(params.filter()[key]);
				}
			}
			
			data = params.filter() ? $filter('filter')(data, params.filter())
					: data;
			data = params.sorting() ? $filter('orderBy') (data, params.orderBy()) : data;
					
			$.each(dateFilterObject, function(key,value) {params.filter()[key] = value;});
			params.total(data.length);
			$defer.resolve(data.slice((params.page() - 1) * params.count(),
					params.page() * params.count()));
		}
	},
	filterDateField : function(data,value) {
		var filteredData = [];
		var dates = value.split(" - ");
		if (!moment(dates[0]).isValid() || !moment(dates[1]).isValid()) {
			return filteredData;
		}

		for ( var i in data) {
			var date = moment(data[i]["date"]);
			if (moment(dates[0])<=date && date<= moment(dates[1])) {
				filteredData.push(data[i]);
			}
		}

		return filteredData;
	},
	initializeDateRangePicker: function(name) {
		$("input[name='" + name + "']").daterangepicker({
			 format: 'YYYY-MM-DD',
			 showDropdowns : true,
			 endDate: moment(),
			 maxDate: moment(),
			 opens: 'left'
		});
		
		$(".cancelBtn").on("click",function(){
			$("input[name='" + name + "']").val("");
			$(".clear-"+name).click();
		});
		
		$(".applyBtn").on("click",function(){
			$(".apply-"+name).click();
		});
	},
	isDateField: function(str) {
		return str.toLowerCase().indexOf("date") >= 0;
	}
};

$(function(){ 
	ui.init();
});
