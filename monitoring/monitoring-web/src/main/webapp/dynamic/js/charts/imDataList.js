App.controller('imDataList', function($scope, $filter, $timeout, factorys,
		PaginationService) {
	$scope.loadList = function() {
		var params = {}
		factorys.get(params, "/im/showDataList").then(
				function(dataList) {
					$scope.imServiceDatas = dataList.result;
				});
	};
	$scope.loadList();
	//设置页面刷新频率 5秒一次
	/*setInterval(function() {
		//alert("hello");
		$scope.imServiceDatas ="";
		$scope.loadList();
	}, 10000);
*/
});