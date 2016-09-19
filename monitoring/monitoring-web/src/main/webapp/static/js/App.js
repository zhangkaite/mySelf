

var App = angular.module("apps",
		[ "ngSanitize", "ui.select", "checklist-model" ]);

App
		.config(function($httpProvider) {
			$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
			$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

			// Override $http service's default transformRequest
			$httpProvider.defaults.transformRequest = [ function(data) {
				/**
				 * The workhorse; converts an object to x-www-form-urlencoded
				 * serialization.
				 * 
				 * @param {Object}
				 *            obj
				 * @return {String}
				 */
				var param = function(obj) {
					var query = '';
					var name, value, fullSubName, subName, subValue, innerObj, i;

					for (name in obj) {
						value = obj[name];

						if (value instanceof Array) {
							for (i = 0; i < value.length; ++i) {
								subValue = value[i];
								fullSubName = name + '[' + i + ']';
								innerObj = {};
								innerObj[fullSubName] = subValue;
								query += param(innerObj) + '&';
							}
						} else if (value instanceof Object) {
							for (subName in value) {
								subValue = value[subName];
								fullSubName = name + '[' + subName + ']';
								innerObj = {};
								innerObj[fullSubName] = subValue;
								query += param(innerObj) + '&';
							}
						} else if (value !== undefined && value !== null) {
							query += encodeURIComponent(name) + '='
									+ encodeURIComponent(value) + '&';
						}
					}

					return query.length ? query.substr(0, query.length - 1)
							: query;
				};

				return angular.isObject(data)
						&& String(data) !== '[object File]' ? param(data)
						: data;
			} ];
		});


/**
 * 
 */
App.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});

/**
 *  Ajax 缓存
 */
App.config(function($httpProvider) {
	$httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
	});

/**
 * 表单自动补全 过滤
 */

App.filter('propsFilter', function() {
	return function(items, props) {
		var out = [];

		if (angular.isArray(items)) {
			var keys = Object.keys(props);

			items
					.forEach(function(item) {
						var itemMatches = false;

						for (var i = 0; i < keys.length; i++) {
							var prop = keys[i];
							var text = props[prop].toLowerCase();
							if (item[prop].toString().toLowerCase().indexOf(
									text) !== -1) {
								itemMatches = true;
								break;
							}
						}

						if (itemMatches) {
							out.push(item);
						}
					});
		} else {
			// Let the output be the input untouched
			out = items;
		}

		return out;
	};
});

/**
 * 增删该查公共方法 all 列表查询 get 单条信息查询 add 新增信息 update 更新信息
 */
App.factory('factorys', [ '$http', function($http) {
	var factory = {};
	factory.all = function(urls) {
		var datas = $http.post(urls).then(function(resp) {
			return resp.data;
		});
		return datas;
	};
	factory.get = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : JSON.stringify(params)
			}
		}
		var datas = $http(req).then(function(resp) {
			return resp.data;
		});
		return datas;
	};
	
	factory.getNormal = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : params
			}
		}
		var datas = $http(req).then(function(resp) {
			return resp.data;
		});
		return datas;
	};
	
	factory.add = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : JSON.stringify(params)
			}
		}
		var datas = $http(req).then(function(resp) {
			return resp.data;
		});
		return datas;
	};
	factory.update = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : JSON.stringify(params)
			}
		}
		var datas = $http(req).then(function(resp) {
			return resp.data;
		});
		return datas;
	};

	factory.del = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : JSON.stringify(params)
			}
		}
		var datas = $http(req).then(function(resp) {
			return resp.data;
		});
		return datas;
	};

	/**
	 * 只做数据请求，无数据返回 有参数
	 */
	factory.commethodparm = function(params, urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				reqData : JSON.stringify(params)
			}
		}
		$http(req);
	}

	/**
	 * 数据请求无返回值，无参
	 */
	factory.commethod = function(urls) {
		var req = {
			method : 'POST',
			url : urls,
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {}
		}
		$http(req);
	}

	return factory;
} ]);

//关于分页
App.directive('pagination',[function(){
	return {
		restrict: 'EA',
		template:'<div class="page-list">' +
		'<ul class="pagination" ng-show="conf.totalItems > 0">' +
		'<li ng-class="{disabled: conf.currentPage == 1}" ng-click="prevPage()"><span>&laquo;</span></li>' +
		'<li ng-repeat="item in conf.pageList track by $index" ng-class="{active: item == conf.currentPage, separate: item == \'...\'}"' +
		'ng-click="changeCurrentPage(item)">' +
		'<span>{{ item }}</span>' +
		'</li>' +
		'<li ng-class="{disabled: conf.currentPage == conf.numberOfPages}" ng-click="nextPage()"><span>&raquo;</span></li>' +
		'</ul>' +
		'<div class="page-total" ng-show="conf.totalItems > 0">' +
		'每页<select ng-model="conf.itemsPerPage" ng-options="option for option in conf.perPageOptions " ng-change="changeItemsPerPage(item)"></select>条/共<strong>{{ conf.totalItems }}</strong>条' +
		'</div>' +
		'<div class="no-items" ng-show="conf.totalItems <= 0">暂无数据</div>' +
		'</div>',
		replace: true
	}
}])

App.factory('PaginationService', ['$http', function ($http) {
	var pagination = {};
	pagination.config= function(currentPage,totalItems,itemsPerPage) {
		var numberOfPages = Math.ceil(totalItems/itemsPerPage);
		var pagesLength = 15;
		var pageList = [];
		if(numberOfPages <= pagesLength){
			// 判断总页数如果小于等于分页的长度，若小于则直接显示
			for(i =1; i <= numberOfPages; i++){
				pageList.push(i);
			}
		}else {
			// 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
			// 计算中心偏移量
			var offset = (pagesLength - 1) / 2;
			if (currentPage <= offset) {
				// 左边没有...
				for (i = 1; i <= offset + 1; i++) {
					pageList.push(i);
				}
				pageList.push('...');
				pageList.push(numberOfPages);
			} else if (currentPage > numberOfPages - offset) {
				pageList.push(1);
				pageList.push('...');
				for (i = offset + 1; i >= 1; i--) {
					pageList.push(numberOfPages - i);
				}
				pageList.push(numberOfPages);
			} else {
				// 最后一种情况，两边都有...
				pageList.push(1);
				pageList.push('...');

				for (i = Math.ceil(offset / 2); i >= 1; i--) {
					pageList.push(currentPage - i);
				}
				pageList.push(currentPage);
				for (i = 1; i <= offset / 2; i++) {
					pageList.push(currentPage + i);
				}

				pageList.push('...');
				pageList.push(numberOfPages);
			}
		}
		var datas = {
			currentPage: currentPage,
			totalItems: totalItems,
			itemsPerPage: itemsPerPage,
			pageList:pageList,
			numberOfPages:numberOfPages,
			perPageOptions:[10,20,30,40,50]
		}

		return datas;
	};

	return pagination;
}]);

function filterPost(resData){
	if(resData instanceof String ){
		if(resData.indexOf("script")){
			parent.location.href = "/";
		}	
	}
	
}










