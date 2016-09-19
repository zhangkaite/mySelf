/*$(function() {
	*//**
	 * 页面加载初始化下拉菜单
	 *//*
	$.post("/threshold/querySysType", {}, function(data) {
		var bToObj = JSON.parse(data);
		for (var i = 0; i < bToObj.length; i++) {
			$("#serverType").append("<option value=" + bToObj[i].value + ">" + bToObj[i].text + "</option>");
		}
	});
});
*/


App.controller('thresholdList', function($scope, $http, $timeout, $interval, factorys) {

	// 初始化选中值
	$scope.multipleDemo = {};
	$scope.alerter = {};
	$scope.person = {};
	$scope.people = [];
	$scope.multipleDemo.zkt = [];

	// 初始化报警器数据
	$scope.loadList = function() {
		// 初始化将数据绑定
		factorys.get({
			pageSize : 100,
			page : 1
		}, "/burglar/queryBurglar").then(function(resData) {
			//filterPost(resData);
			$scope.people = resData.result;
			$scope.loadThresholdList();
		});
	};
	$scope.loadList();
	// 初始化业务数据 左侧名称
	$scope.loadThresholdList = function() {
		var param = $("#serverType").val();
		// alert(param);
		factorys.getNormal(param, "/threshold/queryThreshold").then(function(resData) {
			filterPost(resData);
			// 获取
			$scope.thresholdNames = resData.result.thresholds;
			var alerId = resData.result.threshold_alerter_id;
			var datas = [];
			var tmp = 0;
			for (j = 0; j < $scope.people.length; j++) {
				var alerterID = $scope.people[j].alerterID;
				if (alerterID == alerId) {
					datas[tmp] = $scope.people[j];
					tmp++;
				}
			}
			$scope.multipleDemo.zkt = datas;

		});
	};

	// 保存添加的数据
	$scope.saveThreshold = function(datas) {
		var selectdatas = $scope.multipleDemo.zkt[0].alerterID;
		var obj = {};
		obj.key = "alerterID";
		obj.text = selectdatas;
		var result = $scope._formatArrayToObj(datas, obj);
		factorys.add(result, '/threshold/updateThreshold').then(function(resData) {
			filterPost(resData);
			$scope.alertMsg = resData.result;
			$("#alertModal").modal();
		});
	}

	/* 将js 数组 转换为 json */
	$scope._formatArrayToObj = function(array, obj) {
		var result = {};
		/* 报警器ID */
		result.alerterID = obj.text;
		var objArray = [];
		for ( var i in array) {
			var obj = array[i];
			/* params */
			var tmp = {};
			tmp[obj.key] = obj.value;
			objArray.push(tmp);
		}
		result.params = objArray;
		/* 添加阀值类型 */
		result.threshold_type = $("#serverType").val();
		return result;
	}

	/**
	 * 下拉列表选中，对选中的事件做相应的请求
	 */
	$("#serverType").change(function() {
		$scope.loadThresholdList();
	});

});