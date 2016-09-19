var serie;
var time = (new Date()).getTime();
var refreshIntervalId = null;
var previousId = "NO";
$(function() {
	/**
	 * 页面加载初始化下拉菜单
	 */
	$.post("/mf/initFisData", {}, function(data) {
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
	if (server_type == 'AvServerControlService') {
		$("#AvServerTransmitService").hide();
		$("#AvServerControlService").show();
	} else {
		$("#AvServerTransmitService").show();
		$("#AvServerControlService").hide();
	}
	$.post("/mf/initOneData", {
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
	$.post("/mf/initSecData", {
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
	var f5 = $("#f5Select").val();

	var server_type = $("#firsSelect").val();
	if (server_type == 'AvServerTransmitService') {
		$("#AvServerTransmitService").show();
		$("#AvServerControlService").hide();
		
	}else{
		$("#AvServerTransmitService").hide();
		$("#AvServerControlService").show();
	}
	var datas = [];
	$.post("/mf/queryThityData", {
		server_type : server_type,
		ip : fisData,
		port : secData,
		time : (new Date()).getTime(),
		interval : f5
	}, function(data) {
		var bToObj = JSON.parse(data);
		var current = 0;
		for (var i = current; i < bToObj.length; i++) {
			datas.push({
				x : bToObj[i].CreateTime,
				y : bToObj[i]["" + thrData + ""],
				z : bToObj[i].id

			});
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
	$('#container')
			.highcharts(
					{
						chart : {
							type : 'scatter',
							margin : [ 70, 50, 60, 80 ],
							events : {
								load : function() {
									// set up the updating of the chart each
									// second
									series = this.series[0];
									refreshIntervalId = setInterval(function() {
										$.post("/mf/queryData", {
											fisData : fisData,
											secData : secData,
											server_type : server_type,
											previousId : previousId,
											interval : f5,
											time : (new Date()).getTime()
										}, function(resData) {
											var bToObj = JSON.parse(resData);
											/* 记录前一个ID */
											previousId = bToObj.id;
											var x = bToObj.CreateTime; // current
											// time
											var y = bToObj["" + thrData + ""];
											var z = bToObj.id;
											// alert(z);
											series.addPoint({
												x : x,
												y : y,
												z : z
											}, true, true);
											// 动态更新顶部数据
											if (server_type == 'AvServerTransmitService') {
												/*
												 * $("#writeBufferLength").text(bToObj.WriteBufferLength);
												 * $("#readBufferLength").text(bToObj.ReadBufferLength);
												 */
												$("#udxConnectionLength").text(bToObj.UdxConnectionLength);
												/*
												 * $("#readPacketCount").text(bToObj.ReadPacketCount);
												 * $("#writePacketCount").text(bToObj.WritePacketCount);
												 */
												$("#roomCount").text(bToObj.RoomCount);
												$("#CPU").text(bToObj.CPU);
												$("#Disk").text(bToObj.Disk);
												$("#MEM").text(bToObj.MEM);

											} else {
												$("#InputMessages").text(bToObj.InputMessages);
												$("#OutputMessages").text(bToObj.OutputMessages);
												$("#CreatedRoomCount").text(bToObj.CreatedRoomCount);
												$("#CsCPU").text(bToObj.CPU);
												$("#CsDisk").text(bToObj.Disk);
												$("#CsMEM").text(bToObj.MEM);
												$("#MediaServers").text(bToObj.MediaTransmissionServers);
											
											}

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
							// maxZoom:3600000,
							minTickInterval : 30000,
							// minRange : 120000,
							// minTickInterval:10,
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
								text : $("#threeSelect").find("option:selected").text() + '值'
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
											// alert(this.series[0].z);
											// $("#fuck").hide();
											// $("#alertModal").modal();
											showCurrentDetail(this.z, server_type);
										}
									}
								}
							}
						},

						tooltip : {
							shared : true, // 是否共享提示，也就是X一样的所有点都显示出来
							xDateFormat : '%Y-%m-%d %H:%M:%S',
							useHTML : true, // 是否使用HTML编辑提示信息
							headerFormat : '<div id="showToolTip"><div id="hideDiv"><small><b style="font-size:12px;">当前时间:{point.key}</b></small>',
							pointFormat : '<table><tr><td style="text-align: left"><b>实时数据:{point.y}</b></td></tr></table></div>',
							footerFormat : '</div>'
							
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
	$("#InputMessages").text("");
	$("#OutputMessages").text("");
	$("#CreatedRoomCount").text("");
	$("#CsCPU").text("");
	$("#CsDisk").text("");
	$("#CsMEM").text("");
	$("#MediaServers").text("");
	$("#udxConnectionLength").text("");
	$("#roomCount").text("");
	$("#CPU").text("");
	$("#Disk").text("");
	$("#MEM").text("");
	initChars(fisData, secData, thrData);

}

function showCurrentDetail(id, server_type) {
	// 异步请求获取数据详情
	if (id == null || id == '') {
		return false;
	}

	$
			.post(
					"/mf/queryCurrentData",
					{
						server_type : server_type,
						id : id
					},
					function(data) {
						var bToObj = JSON.parse(data);
						var strHtml = "<table >";
						if (server_type == 'AvServerTransmitService') {
							
							strHtml += "<tr><td style='text-align: right'><b>当前的连接列表长度:"
									+ bToObj.UdxConnectionLength + "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>房间数量:" + bToObj.RoomCount
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>CPU占用百分比:" + bToObj.CPU
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>硬盘空间占用(GB):" + bToObj.Disk
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>内存占用(MB):" + bToObj.MEM
									+ "</b></td></tr>";
							strHtml += "</table>";
							$("#hideDiv").hide();
							// $("#showToolTip").text();
							$("#showToolTip").html(strHtml);
							/*
							 * $("#showToolTip").css("background-color","#ECF5FF");
							 * $("#showToolTip").css("height","200px");
							 * $("#showToolTip").css("width","200px");
							 * $("#showToolTip").css("float","left");
							 */
						} else {
							strHtml += "<tr><td style='text-align: right'><b>输入消息数量:" + bToObj.InputMessages
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>输出消息数量:" + bToObj.OutputMessages
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>已创建房间数量:"
									+ bToObj.CreatedRoomCount + "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>CPU占用百分比:" + bToObj.CPU
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>硬盘空间占用(GB):" + bToObj.Disk
									+ "</b></td></tr>";
							strHtml += "<tr><td style='text-align: right'><b>内存占用(MB):" + bToObj.MEM
									+ "</b></td></tr>";
							// alert(bToObj.MediaTransmissionServers);
							var data = bToObj.MediaTransmissionServers;
							$("#curData").text(data);
							strHtml += "<tr><td style='text-align: left'><b>媒体转发服务器列表:<a onclick='showDetail(1)'>详情</a></b></td></tr>";
							strHtml += "</table>";
							$("#hideDiv").hide();
							$("#showToolTip").html(strHtml);
						}

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
	if (data == '' || data == null) {
		$("#showDetail").text("暂无数据");
		$("#alertModal").modal();
		return false;
	}

	var jsonObj = JSON.parse(data);
	var strHtml = "<table class='table table-striped table-bordered table-hover'>";
	for (i = 0; i < jsonObj.length; i++) {
		strHtml += "<tr><td>转发服务器IP:" + jsonObj[i].IP + "</td><td>转发服务器端口:" + jsonObj[i].port + "</td></tr>";
	}
	strHtml += "</table>";
	$("#showDetail").html(strHtml);
	$("#alertModal").modal();
}
