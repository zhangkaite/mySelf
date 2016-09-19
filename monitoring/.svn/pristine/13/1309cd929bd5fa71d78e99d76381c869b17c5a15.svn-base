App.controller('IpFilter', function($scope, $http, $timeout, $interval, factorys, PaginationService) {

	$scope.init = function() {
		$scope.ipList(1);

	}

	/**
	 * 列表查询
	 */
	var pageSize = 10;
	$scope.ipList = function(page) {
		var params = {
			"pageSize" : pageSize,
			"page" : page
		}
		factorys.get(params, "/ipFilter/queryWhitelist").then(function(data) {
			filterPost(data);
			// $timeout(function() {
			$scope.ipFilters = data.result;
			$scope.conf = PaginationService.config(page, data.dataSum, pageSize);
			// }, 300);

		});
	};

	$scope.ipList(1);

	/**
	 * 对获取的数据进行保存
	 */

	$scope.saveIP = function(ips) {
		var startIP = ips.ip1 + "." + ips.ip2 + "." + ips.ip3 + "." + ips.ip4;
		var endIP = '';
		var type = 1;
		if ($('#ckbox').is(':checked')) {
			endIP = ips.ip5 + "." + ips.ip6 + "." + ips.ip7 + "." + ips.ip8;
			type = 2;
		}
		if (ips.ip4 > ips.ip8) {
			alert("结束IP值必须大于起始IP值");
			return;
		}

		var params = {
			"startIP" : startIP,
			"endIP" : endIP,
			"type" : type
		}

		factorys.add(params, "/ipFilter/addip").then(function(resData) {
			filterPost(resData);
			$("#myModal").modal("hide");
			$scope.alertMsg = resData.result;
			$("#alertModal").modal();
			$scope.ipList(1);

		});

	}

	$scope.modifyip = function(ip) {

		var obj = new Object();
		var startIP = ip.startIP;
		var endIP = '';
		var endips = '';

		var startips = startIP.split(".");
		obj.ip1 = startips[0];
		obj.ip2 = startips[1];
		obj.ip3 = startips[2];
		obj.ip4 = startips[3];
		if (ip.type == '2' || ip.type == 2) {
			$("#ckboxEdit").attr("checked", 'true');
			$("#div2Edit").show();
			endIP = ip.endIP;
			endips = endIP.split(".");
			obj.ip5 = endips[0];
			obj.ip6 = endips[1];
			obj.ip7 = endips[2];
			obj.ip8 = endips[3];
		} else {
			$("#div2Edit").hide();
			$("#ckboxEdit").removeAttr("checked");
			// $("#ckboxEdit").attr("checked",false);
		}
		obj.whiteID = ip.whiteID;
		$scope.whiteips = obj;
	}

	// 更新数据
	// 更新用户
	$scope.updateIP = function(ips) {
		var startIP = ips.ip1 + "." + ips.ip2 + "." + ips.ip3 + "." + ips.ip4;
		var endIP = '';
		var type = 1;
		if ($('#ckboxEdit').is(':checked')) {
			endIP = ips.ip5 + "." + ips.ip6 + "." + ips.ip7 + "." + ips.ip8;
			type = 2;
			if (ips.ip4 > ips.ip8) {
				alert("结束IP值必须大于起始IP值");
				return;
			}
		}

		var whiteID = ips.whiteID;
		var params = {
			"whiteID" : whiteID,
			"startIP" : startIP,
			"endIP" : endIP,
			"type" : type
		}
		factorys.update(params, "/ipFilter/updateIp").then(function(resData) {
			filterPost(resData);
			$scope.ipList(1);
			$("#editModal").modal("hide");
			$("#ckboxEdit").attr("checked", 'false');
		});
	};

	// init load data

	/**
	 * 删除列表
	 */

	// 删除用户
	$scope.delip = function(ip) {

		var params = {
			"whiteID" : ip.whiteID

		}
		if (confirm('确定删除吗?')) {
			factorys.del(params, "/ipFilter/delip").then(function(resData) {
				filterPost(resData);
				$scope.alertMsg = resData.result;
				$("#alertModal").modal();
				$scope.ipList(1);
			});
		}
	}

	$scope.changeNextIp = function(ips, type) {
		if (type == '0' || type == 0) {
			$scope.whiteip.ip5 = ips.ip1;
			$scope.whiteip.ip6 = ips.ip2;
			$scope.whiteip.ip7 = ips.ip3;
		} else {
			$scope.whiteips.ip5 = ips.ip1;
			$scope.whiteips.ip6 = ips.ip2;
			$scope.whiteips.ip7 = ips.ip3;
		}

	}

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

function ipcheck() {
	var ip = $("#whiteip").val();
	var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = ip.match(exp);
	if (reg == null) {
		$("#isShowError").show();
		$("#ipSave").attr("disabled", true);
	}

}

$(function() {
	/*
	 * $("input").each(function(){ $(this).val(100); });
	 */
	$("input").bind("keyup", function() {
		$(this).val($(this).val().replace(/\D/gi, ""));
	});
});

function showDispaly() {
	if ($('#ckbox').is(':checked')) {
		$("#div2").show();
	} else {
		$("#div2").hide();
	}

}
function showDispalyEdit() {
	if ($('#ckboxEdit').is(':checked')) {
		$("#div2Edit").show();
	} else {
		$("#div2Edit").hide();
	}

}
