App.controller('Redis', function($scope, $http, $timeout, $interval, factorys, PaginationService) {

	$scope.init = function() {
		$scope.redisList(1);

	}

	/**
	 * 列表查询
	 */
	var pageSize = 10;
	$scope.redisList = function(page) {
		var params = {
			"pageSize" : pageSize,
			"page" : page
		}
		factorys.get(params, "/redis/queryRedisDetail").then(function(data) {
			filterPost(data);
			$scope.redisList = data.result;
			$scope.conf = PaginationService.config(page, data.dataSum, pageSize);
			// }, 300);

		});
	};

	$scope.redisList(1);


});
