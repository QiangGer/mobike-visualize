<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="oracle.dao.IStationInfoDao"%>
<%@page import="oracle.dao.impl.StationInfoDaoImpl"%>
<%@page import="oracle.domain.StationInfo"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

<!-- 引入 ECharts 文件 -->
<script src="JS/dependecesecharts.min.js"></script>
<!-- 引入 jQuery 文件 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>


<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}
</style>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=BAM9UXl74sBKhgOD0lxq65oUilsComx7"></script>

<title>地图展示</title>
</head>

<body>
	<div id="allmap" style="width: 100%; height: 60%;"></div>

	<div id="select" style="width: 100%; height: 10%;">
		Station name: <a id="stationName"> </a> </br> </br> <a id="date">日期：<input
			type="date" id="datepicker"></a>
		<button id="draw">提交</button>
	</div>
	<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
	<div id="echarts" style="width: 100%; height: 30%;"></div>
</body>

</html>
<script>
	
<%
			IStationInfoDao dao = new StationInfoDaoImpl();
			List<StationInfo> all = dao.getAll();
			ArrayList<JSONObject> points = new ArrayList<JSONObject>();

			for (StationInfo k : all) {
				JSONObject js = JSONObject.fromObject(k);
				points.add(js);
			}
%>
	var info = <%=points%>;

	// 百度地图API功能
	var map = new BMap.Map("allmap"); // 创建Map实例
	map.centerAndZoom(new BMap.Point(120.41, 30.21), 12.5); // 初始化地图,设置中心点坐标和地图缩放级别
	//添加地图类型控件
	map.addControl(new BMap.MapTypeControl({
		mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
	}));
	map.setCurrentCity("杭州"); // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放

	var opts = {
		width : 200,
		height : 130,
		title : "站点信息"
	};

	info.forEach(function(item) {
		var point = new BMap.Point(item.x, item.y);
		var marker = new BMap.Marker(point); // 创建标注

		var infoWindow = new BMap.InfoWindow("id是" + item.stationID + "<br>"
				+ "name是" + item.stationName + "<br>" + "address是"
				+ item.address, opts);

		marker.addEventListener("click", function() {
			map.openInfoWindow(infoWindow, point);
			document.getElementById('stationName').innerHTML = item.stationID;
		});
		map.addOverlay(marker); // 将标注添加到地图中
	})

	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echarts'));

	$(document).ready(function() {
		$('#draw').click(function() {
			var data = {
				date : document.getElementById('datepicker').value,
				id : document.getElementById('stationName').innerHTML,
			};
			$.getJSON("draw",//产生JSON数据的服务端页面
			data,//向服务器发出的查询字符串（此参数可选）
			function(json) {
				console.log(json);
				var time = new Array();
				var leaseNum = new Array();
				var returnNum = new Array();
				for (var i = 0; i < json.length; i++) {
					time.push(json[i].leaseTime);
					leaseNum.push(json[i].leaseNum);
					returnNum.push(json[i].returnNum);
				}

				var option = {
					title : {
						text : 'ECharts 入门示例'
					},
					tooltip : {},
					//图例组件，位于图标上方，可控制显示与否
					legend : {
						data : [ 'leaseNum', 'returnNum' ]
					},
					xAxis : {
						data : time
					},
					yAxis : {},
					series : [ {
						name : 'leaseNum',
						type : 'line',
						areaStyle : {},
						label : {
							normal : {
								show : true,
								position : 'top'
							}
						},
						data : leaseNum
					}, {
						name : 'returnNum',
						type : 'line',
						areaStyle : {},
						label : {
							normal : {
								show : true,
								position : 'top'
							}
						},
						data : returnNum
					}, ]
				};
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			})

		});
	});
</script>

