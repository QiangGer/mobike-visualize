// 百度地图API功能

// 创建Map实例
let map = new BMap.Map("allmap"); 

//初始化地图,设置中心点坐标和地图缩放级别
map.centerAndZoom(new BMap.Point(120.41, 30.21), 12.5);

// 添加地图类型控件初始化地图,设置中心点坐标和地图缩放级别
map.addControl(new BMap.MapTypeControl({
	mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
}));

//设置地图显示的城市 此项是必须设置的
map.setCurrentCity("杭州"); 

//开启鼠标滚轮缩放
map.enableScrollWheelZoom(true);

let stations = null;
$.ajaxSetup({async:false});  
$.getJSON("mapInit",function(result){
	stations = result;
})

let opts = {
	width : 200,
	height : 130,
	title : "站点信息："
};

stations.forEach(function(item) {
	let point = new BMap.Point(item.x, item.y);
	let marker = new BMap.Marker(point); // 创建标注

	let infoWindow = new BMap.InfoWindow("ID是： " + item.stationID + "<br>"
			+ "车站名称是： " + item.stationName + "<br>" + "车站地址是： " + item.address,
			opts);

	marker.addEventListener("click", function() {
		let echart = document.getElementById('echart');
		map.openInfoWindow(infoWindow, point);
		echart.hidden = false;
		document.getElementById('allmap').style.height = "70%";
		echart.style.height = "30%";
		document.getElementById('stationID').innerHTML = item.stationID;
	});
	map.addOverlay(marker); // 将标注添加到地图中
})


