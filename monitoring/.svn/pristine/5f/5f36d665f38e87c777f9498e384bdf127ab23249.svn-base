App.controller('DemoCtrl', function($scope, $http, $timeout, $interval, factorys, PaginationService) {
	$scope.person = {};
	$scope.loadList = function() {
		$scope.burglar.alerterName = '';
		$scope.burglar.count = '';
		$scope.burglar.model = '';
		$scope.multipleDemo.selectedPeople = [];
		factorys.get({
			pageSize : 100,
			page : 1
		}, "/userShow").then(function(data) {
			//filterPost(data);
			$scope.people = data.result;

		});
	};

	$scope.init = function() {
		$scope.burglarList(1);
	}

	/**
	 * 列表查询
	 */
	var pageSize = 10;
	$scope.burglarList = function(page) {
		var params = {
			"pageSize" : pageSize,
			"page" : page
		}
		factorys.get(params, "/burglar/queryBurglar").then(function(data) {
			filterPost(data);
			// $timeout(function() {
			$scope.alerters = data.result;
			$scope.conf = PaginationService.config(page, data.dataSum, pageSize);
			// }, 300);

		});
	};
	// init load data
	// $scope.loadList();
	$scope.burglarList(1);
	$scope.multipleDemo = {};

	/**
	 * 修改报警器,查询报警器信息
	 */
	$scope.burglarModify = function(burglar) {
		factorys.get({
			pageSize : 100,
			page : 1
		}, "/userShow").then(function(data) {
			$scope.mmm = data.result;
			factorys.get(burglar, "/burglar/queryBurglarId").then(function(resData) {
				filterPost(resData);
				$scope.burglar.alerterID = resData.result.alerterID;
				$scope.burglar.alerterName = resData.result.alerterName;
				$scope.burglar.count = resData.result.alerterCont;
				$scope.burglar.model = resData.result.alerterMsg;
				var us = resData.result.alerterUsers;
				var datas = [];
				var tmp = 0;
				for (i = 0; i < $scope.mmm.length; i++) {
					var userID = $scope.mmm[i].userID;
					for (j = 0; j < us.length; j++) {
						if (userID == us[j]) {
							datas[tmp] = $scope.mmm[i];
							tmp++;
						}
					}
				}
				$scope.multipleDemo.selectedPeople = datas;
				var str = resData.result.alerterType;
				var strs = str.split("|"); // 字符分割
				var types = [];
				for (var i = 0; i < strs.length; i++) {
					types[i] = Number(strs[i]);
				}
				$scope.burgl = {
					alerterTypes : types
				};

			});
		});
	}

	// $scope.multipleDemo.selectedPeople = [$scope.people[5],
	// $scope.people[4]];
	$scope.multipleDemo.selectedPeople = [];

	/**
	 * 添加checkbox list
	 */

	$scope.alerterTypes = [ {
		id : 1,
		text : '短信'
	}, {
		id : 2,
		text : '邮件'
	}

	];

	$scope.burglar = {
		alerterTypes : []
	};

	/**
	 * 对获取的数据进行保存
	 */

	var currnt = '';
	$scope.selectData = function(id) {
		currnt = currnt + id + '|';
	}

	$scope.saveBurglar = function(burglar) {
		var obj = new Object();
		obj.alerterName = burglar.alerterName;
		obj.alerterType = currnt.substr(0, currnt.length - 1);
		// 信息模版暂时默认为1
		obj.alerterCont = burglar.count;
		obj.alerterMsg = 1;
		var userids = [];
		var len = $scope.multipleDemo.selectedPeople.length;
		for (i = 0; i < len; i++) {
			// alert($scope.multipleDemo.selectedPeople[i].userID);
			userids[i] = $scope.multipleDemo.selectedPeople[i].userID;
		}
		obj.alerterUsers = userids;

		factorys.add(obj, "/burglar/addBurglar").then(function(resData) {
			filterPost(resData);
			$("#myModal").modal("hide");
			$scope.alertMsg = resData.result;
			$("#alertModal").modal();
			$scope.burglarList(1);

		});

	}
	$scope.updateBurglar = function(burglar) {
		var obj = new Object();
		obj.alerterID = burglar.alerterID;
		obj.alerterName = burglar.alerterName;
		var tmpData = $scope.burgl.alerterTypes;
		var data = '';
		for (i = 0; i < tmpData.length; i++) {
			data = data + tmpData[i] + "|";
		}
		obj.alerterType = data.substr(0, data.length - 1);
		// alert(data);
		obj.alerterCont = burglar.count;
		// 信息模版暂时默认为1
		obj.alerterMsg = 1;
		var userids = [];
		var len = $scope.multipleDemo.selectedPeople.length;
		for (i = 0; i < len; i++) {
			// alert($scope.multipleDemo.selectedPeople[i].userID);
			userids[i] = $scope.multipleDemo.selectedPeople[i].userID;
		}
		obj.alerterUsers = userids;
		factorys.add(obj, "/burglar/updateBurglar").then(function(resData) {
			filterPost(resData);
			$scope.alertMsg = resData.result;
			$("#alertModal").modal();
			$scope.burglarList(1);
			$("#modifyModal").modal("hide");
		});

	}

	// init load data

	/**
	 * 删除列表
	 */

	// 删除用户
	$scope.delBurglar = function(burglar) {
		if (confirm('确定删除吗?')) {
			factorys.del(burglar, "/burglar/delBurglar").then(function(resData) {
				filterPost(resData);
				$scope.alertMsg = resData.result;
				$("#alertModal").modal();
				$scope.burglarList(1);
			});
		}
	}

	// 分页
	$scope.changeCurrentPage = function(item) {
		if (item == '...') {
			return;
		} else {
			$scope.burglarList(item);
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
