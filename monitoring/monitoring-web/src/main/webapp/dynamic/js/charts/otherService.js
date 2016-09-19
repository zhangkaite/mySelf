var serie;
var time = (new Date()).getTime();
var refreshIntervalId = null;
var previousId = "NO";
$(function() {
	/**
	 * 页面加载初始化下拉菜单
	 */
	$.post("/otherService/initFisData", {}, function(data) {
		var bToObj = JSON.parse(data);
		for (var i = 0; i < bToObj.length; i++) {
			$("#firsSelect").append("<option value=" + bToObj[i].value + ">" + bToObj[i].text + "</option>");
		}
		initOneData();
	});
	$("#firsSelect").change(function() {
		initOneData();
	});

	$("#f5Select").change(function() {
		showView();
	});
});

/**
 * 初始化一级下拉菜单
 */
function initOneData() {
	// 获取首个下拉菜单值
	var server_type = $("#firsSelect").val();
	$.post("/otherService/initOneData", {
		server_type : server_type
	}, function(data) {
		var bToObj = JSON.parse(data);

		$("#oneSelect").empty();
		$("#twoSelect").empty();
		$("#threeSelect").empty();
		if (bToObj != null && bToObj.length > 0) {
			for (var i = 0; i < bToObj.length; i++) {
				$("#oneSelect").append(
						"<option value=" + bToObj[i].text + ">" + bToObj[i].value + "</option>");
			}
		} else {
			$("#oneSelect").append("<option value=0.0.0.0>--请选择--</option>");
		}

		initSecData();
	});

	$("#oneSelect").change(function() {
		initSecData();
	});

}

/**
 * 初始化二级下拉菜单
 */
function initSecData() {
	// 获取下拉框选中项的text属性值
	var selectText = $("#oneSelect").find("option:selected").text();
	// 获取子系统类型
	var server_type = $("#firsSelect").val();
	// 清空二级下拉菜单
	// $("#twoSelect").empty();
	// 重新加载二级菜单数据
	$.post("/otherService/initSecData", {
		reqData : selectText,
		server_type : server_type
	}, function(data) {
		var bToObj = JSON.parse(data);
		$("#twoSelect").empty();
		if (!bToObj) {
			$("#twoSelect").append("<option value=-1>-1</option>");
		} else {
			for (var i = 0; i < bToObj.length; i++) {
				$("#twoSelect").append(
						"<option value=" + bToObj[i].text + ">" + bToObj[i].value + "</option>");
			}
		}
		initThrData();
	});
}
/**
 * 三级下拉菜单
 */
function initThrData() {
	var server_type = $("#firsSelect").val();
	$.post("/threshold/queryThreshold", {
		reqData : server_type
	}, function(data) {
		var bToObj = JSON.parse(data);
		$("#threeSelect").empty();
		for (var i = 0; i < bToObj.result.thresholds.length; i++) {
			$("#threeSelect").append(
					"<option value=" + bToObj.result.thresholds[i].key + ">"
							+ bToObj.result.thresholds[i].name + "</option>");
		}
		var fisData = $("#oneSelect").find("option:selected").text();
		var secData = $("#twoSelect").find("option:selected").text();
		var thrData = $("#threeSelect").val();
		initChars(fisData, secData, thrData);
		$("#threeSelect").change(function() {
			showView();
		});
		// showView();
	});
}

function initChars(fisData, secData, thrData) {
	// 获取子系统类型
	var server_type = $("#firsSelect").val();
	var f5 = $("#f5Select").val();
	var datas = [];
	$.post("/otherService/queryThityData", {
		server_type : server_type,
		ip : fisData,
		port : secData,
		time : (new Date()).getTime(),
		interval : f5
	}, function(data) {

		var bToObj = JSON.parse(data);
		if (bToObj != null) {
			for (var i = 0; i < bToObj.length; i++) {
				datas.push({
					x : bToObj[i].CreateTime,
					y : bToObj[i]["" + thrData + ""],
					z : bToObj[i].id
				});
			}
		}
		showChars(fisData, secData, thrData, datas, server_type, f5);
	})
}

function showChars(fisData, secData, thrData, datas, server_type, f5) {
	clean();
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});
	$('#container').highcharts({
		chart : {
			type : 'scatter',
			margin : [ 70, 50, 60, 80 ],
			events : {
				load : function() {
					// set up the updating of the chart each second
					series = this.series[0];
					refreshIntervalId = setInterval(function() {
						$.post("/otherService/queryData", {
							fisData : fisData,
							secData : secData,
							server_type : server_type,
							previousId : previousId,
							interval : 30,
							time : (new Date()).getTime()
						}, function(resData) {
							var bToObj = JSON.parse(resData);
							/* 记录前一个ID */
							previousId = bToObj.id;
							var x = bToObj.CreateTime; // current
							// time
							var y = bToObj["" + thrData + ""];
							var z = bToObj.id;
							series.addPoint({x:x,y:y,z:z}, true, true);
							// 动态更新顶部数据
							$("#roomCount").text(bToObj.RoomCount);
							$("#CPU").text(bToObj.CPU);
							$("#Disk").text(bToObj.Disk);
							$("#MEM").text(bToObj.MEM);
							$("#Rooms").text(bToObj.Rooms);
						});
					}, f5 * 1000);
				}
			}
		},
		title : {
			text : $("#threeSelect").find("option:selected").text() + '曲线图'
		},
		subtitle : {
			text : ''
		},
		xAxis : {
			minRange : 120000 * f5,
			// minRange : 120000* f5,
			// tickWidth : 0.5,// 设置X轴坐标点是否出现占位及其宽度
			// tickPixelInterval:6000,//设置横坐10标密度
			tickInterval : 1000 * f5,
			type : 'datetime',
			dateTimeLabelFormats : {
				minute : '%H:%M',
			}
		},
		yAxis : {
			title : {
				text : 'Value'
			},
			lineColor : '#FF0000',
			lineWidth : 1,
			allowDecimals:false
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		credits : {

			enabled : false

		},
		plotOptions : {
			series : {
				lineWidth : 1,
				point : {
					events : {
						'click' : function() {
							showCurrentDetail(this.z, server_type);
							// $("#alertModal").modal();
						}
					}
				}
			}
		},

		tooltip : {
			shared : true, // 是否共享提示，也就是X一样的所有点都显示出来
			xDateFormat : '%Y-%m-%d %H:%M:%S',
			useHTML : true, // 是否使用HTML编辑提示信息
			headerFormat : '<small><b>当前时间:{point.key}</b></small><table>',
			pointFormat : '<tr>' + '<td style="text-align: right"><b>实时数据:{point.y}</b></td></tr>',
			footerFormat : '</table>'
			
		// 数据值保留小数位数
		},
		series : [ {
			name : '当前数据',
			data : datas

		} ]
	});

}

function clean() {
	clearInterval(refreshIntervalId);
}

function showView() {
	
	var fisData = $("#oneSelect").find("option:selected").text();
	var secData = $("#twoSelect").find("option:selected").text();
	var thrData = $("#threeSelect").val();
	$("#roomCount").text("");
	$("#CPU").text("");
	$("#Disk").text("");
	$("#MEM").text("");
	$("#Rooms").text("");
	initChars(fisData, secData, thrData);

}


function showCurrentDetail(id, server_type) {
	// 异步请求获取数据详情
	if (id == null || id == '') {
		return false;
	}

	$.post("/mf/queryCurrentData", {
		server_type : server_type,
		id : id
	}, function(data) {
		var bToObj = JSON.parse(data);
		var strHtml = "<table >";
			strHtml += "<tr><td style='text-align: right'><b>房间数量:" + bToObj.RoomCount + "</b></td></tr>";
			strHtml += "<tr><td style='text-align: right'><b>CPU占用百分比:" + bToObj.CPU + "</b></td></tr>";
			strHtml += "<tr><td style='text-align: right'><b>硬盘空间占用(GB):" + bToObj.Disk + "</b></td></tr>";
			strHtml += "<tr><td style='text-align: right'><b>内存占用(MB):" + bToObj.MEM + "</b></td></tr>";
			strHtml += "<tr><td style='text-align: left'><b>房间列表:<a onclick='showDetail(1)'>详情</a></b></td></tr>";
			strHtml += "</table>";
			var data=bToObj.MediaTransmissionServers;
			$("#curData").text(data);
			$("#hideDiv").hide();
			$("#showToolTip").html(strHtml);

	})
}




/**
 * 查看列表详情
 */
function showDetail(currenData) {
	var data = '';
	if (currenData == '1' || currenData == 1) {
		data = $("#curData").text();
	} else {
		data = $("#MediaServers").text();
	}
	if(data==''||data==null){
		$("#showDetail").text("暂无数据");
		$("#alertModal").modal();
		return false;
	}
	var jsonObj = JSON.parse(data);
	var strHtml = "<table class='table table-striped table-bordered table-hover'>";
	var server_type = $("#firsSelect").val();
	for (i = 0; i < jsonObj.length; i++) {
		if(server_type=='ScreenshotService'){
			strHtml += "<tr><td>截图房间号:" + jsonObj[i].RoomID + "</td><td>连麦标志:"
			+ jsonObj[i].UnionFlag + "</td><td>截图状态:" + jsonObj[i].captureStatus + "</td><td>输出状态:"
			+ jsonObj[i].outputStatus + "</td><td>主播ID:" + jsonObj[i].AnchorID + "</td><td>连麦者ID:"
			+ jsonObj[i].PartnerID + "</td></tr>";	
		}else if(server_type=='TranscodingService'){
			strHtml += "<tr><td>截图房间号:" + jsonObj[i].RoomID + "</td><td>连麦标志:"
			+ jsonObj[i].UnionFlag + "</td><td>转码状态:" + jsonObj[i].transcodeStatus + "</td><td>输出状态:"
			+ jsonObj[i].outputStatus + "</td><td>主播ID:" + jsonObj[i].AnchorID + "</td><td>连麦者ID:"
			+ jsonObj[i].PartnerID + "</td></tr>";
		}
		
	}
	strHtml += "</table>";
	$("#showDetail").html(strHtml);
	$("#alertModal").modal();
}
