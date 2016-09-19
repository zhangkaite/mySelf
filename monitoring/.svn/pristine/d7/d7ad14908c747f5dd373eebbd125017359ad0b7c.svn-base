App.controller('cpuList', function($scope, $http, $timeout, $interval, factorys, PaginationService) {

	$scope.init = function() {
		$scope.cpuList(1);

	}

	/**
	 * 列表查询
	 */
	var pageSize = 10;
	$scope.cpuList = function(page) {
		var params = {
			"pageSize" : pageSize,
			"page" : page
		}
		factorys.get(params, "/collection/queryCpuList").then(function(data) {
			filterPost(data);
			$scope.dataLists = data.result;
			$scope.conf = PaginationService.config(page, data.dataSum, pageSize);

		});
	};

	$scope.cpuList(1);

	

	// 分页
	$scope.changeCurrentPage = function(item) {
		if (item == '...') {
			return;
		} else {
			$scope.ipList(item);
		}
	}
	// changeItemsPerPage
	$scope.changeItemsPerPage = function() {
		pageSize = $scope.conf.itemsPerPage;
		$scope.changeCurrentPage(1);
	}
	// prevPage
	$scope.prevPage = function() {
		if ($scope.conf.currentPage > 1) {
			var item = $scope.conf.currentPage - 1;
			$scope.changeCurrentPage(item);
		}
	};
	// nextPage
	$scope.nextPage = function() {
		if ($scope.conf.currentPage < $scope.conf.numberOfPages) {
			var item = $scope.conf.currentPage + 1;
			$scope.changeCurrentPage(item);
		}
	};

});


