$('.form_date').datetimepicker({
	language : 'fr',
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	forceParse : 0
});

App.controller('ErrorCtrl', function($scope, $http, $timeout, $interval, factorys, PaginationService) {
	$scope.init = function() {
		$scope.errorList(1);
	}

	/**
	 * 列表查询
	 */
	var pageSize = 10;
	$scope.errorList = function(page) {
		var params = {
			"pageSize" : pageSize,
			"page" : page
		}
		factorys.get(params, "/errorLog/queryError").then(function(data) {
			filterPost(data);
			$scope.alertererrors = data.result;
			$scope.conf = PaginationService.config(page, data.dataSum, pageSize);

		});
	};
	$scope.errorList(1);
	/**
	 * 条件查询
	 */
	$scope.showView = function() {
		var serverType = $("#serverType").val();
		var alertType = $("#alertType").val();
		var params = {
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val(),
			serverType : serverType,
			alertType : alertType,
			pageSize : pageSize,
			page : 1
		}
		factorys.get(params, "/errorLog/queryError").then(function(resData) {
			filterPost(resData);
			$scope.alertererrors = resData.result;
			$scope.conf = PaginationService.config(1, resData.dataSum, pageSize);
		});

	}

	$scope.mouseenter = function(alertererror) {
		// alert(alertererror.alertMsg);

		var data = alertererror.alertMsg;
		var strHtml = '';
		if (alertererror.alertType == 'FATALERROR') {
			strHtml = "<p style='word-break: break-all; word-wrap:break-word;'>" + data + "</p>";
		} else {
			var jsonObj = JSON.parse(data);
			strHtml = "<table class='table table-striped table-bordered table-hover'>";
			for (i = 0; i < jsonObj.length; i++) {
				var realData = jsonObj[i].actualValue;
				var thresholdData = jsonObj[i].thresholdValue;
				var thresholdName = jsonObj[i].thresholdName;
				strHtml += "<tr><td>阀值名称:" + thresholdName + "</td><td>报警阀值:" + thresholdData
						+ "</td><td>实际指标:" + realData + "</td></tr>";

				// strHtml+="阀值名称:"+thresholdName+";报警阀值:"+thresholdData+";真实阀值:"+realData+"</br>";
			}
			strHtml += "</table>";
		}
		// 将获取的数据转换成json对象
		$("#showDetail").html(strHtml);
		$("#alertModal").modal();

	}

	// 分页
	$scope.changeCurrentPage = function(item) {
		if (item == '...') {
			return;
		} else {
			$scope.errorList(item);
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
