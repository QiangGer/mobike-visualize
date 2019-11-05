// 创建Map实例
let map = new BMap.Map("allmap"); 
// 初始化地图,设置中心点坐标和地图缩放级别
map.centerAndZoom(new BMap.Point(120.28, 30.28), 12.5);
// 添加地图类型控件初始化地图,设置中心点坐标和地图缩放级别
map.addControl(new BMap.MapTypeControl({
	mapTypes : [ BMAP_NORMAL_MAP, BMAP_HYBRID_MAP ]
}));
// 设置地图显示的城市 此项是必须设置的
map.setCurrentCity("杭州"); 
// 开启鼠标滚轮缩放
map.enableScrollWheelZoom(true);
let arrow = [];
let redraw = [];
let oceanPoints = [];
$(document).ready(function() {
	$.getJSON("newMapInit").done(function(stations){
		addOceanPoints(stations);
	}).done(function(stations){
		$("#submit").click(function(){
			drawLines(stations);
		});
		$("#heatmap").click(function(){
			drawHeatmap(stations);
		})
	});
	
	$("#clear").click(function(){
		arrow = [];
		redraw = [];
		oceanPoints = [];
		$('.ruler').css("visibility","false");
		$('.ruler .ruler_content').css("visibility","hidden");
		map.clearOverlays();
		$.getJSON("newMapInit").done(function(stations){
			addOceanPoints(stations);
		})
		
	})
	
	
	$(document).on("mousewheel DOMMouseScroll", function (e) {
		if(e.originalEvent.wheelDelta){
			arrow.forEach(function(Arrow){		//存放已绘的箭头
				map.removeOverlay(Arrow);
			})
			arrow = [];
			redraw.forEach(function(obj){		//存放弧线
				addArrow(obj.Curveline,obj.Opt);
			})
			
		}

		});
})

//let cleanExtraPoints = function(pointsNotNeedToClean,stations){
//	let allPoints = stations.map(function (temp) {return temp.stationID; }); 
//	let allPointsSet = new Set(allPoints);
//	let pointsNotNeedToCleanSet = new Set(pointsNotNeedToClean);
//	let difference = Array.from(new Set(allPoints.concat(pointsNotNeedToClean).filter(v => !allPointsSet.has(v) || !pointsNotNeedToCleanSet.has(v))));
//	console.log(oceanPoints);
//	difference.forEach(x =>{
//
//		map.removeOverlay(x);
//	})
//	
//}


let addOceanPoints = function(stations){
	let options = {
	        size: BMAP_POINT_SIZE_SMALL,
	        shape: BMAP_POINT_SHAPE_STAR,
	        color: '#d340c3'
	    	}

	let points = []; 

	stations.forEach(function(item) {
		points.push(new BMap.Point(item.x, item.y));
	})
		pointCollection = new BMap.PointCollection(points, options);
		pointCollection.addEventListener('click', function (e) {
	        alert('单击点的坐标为：' + e.point.lng + ',' + e.point.lat);  // 监听点击事件
	      });
		oceanPoints.push(pointCollection);
		map.addOverlay(pointCollection); // 将标注添加到地图中
}

let drawLines = function(stations){
	let curveline;
	let minNum = document.getElementById('minNum').value;
	let pointsNotNeedToClean = [];
	$.getJSON("line",$("form").serialize(),function(relation){
		let nums = relation.map(function (temp) {return temp.BIKENUM; }); // 获取
		let Max = Math.max.apply(Math, nums); // 找出最大值
		$('.ruler_min').html(minNum);
		$('.ruler_max').html(Max);
		$('.ruler').css("visibility","visible");
		$('.ruler .ruler_content').css("visibility","visible");
		relation.forEach(function (rel){
			let from = getXY(rel.LEASESTATION,stations,pointsNotNeedToClean); // 根据站点 id 获取坐标
			let to = getXY(rel.RETURNSTATION,stations,pointsNotNeedToClean);
			if(to !== null && from !== null){
				let num = rel.BIKENUM;
				let weight = ((num - minNum)/(Max-minNum)).toFixed(2); // 求权重（当前值除以最大值）
				const opt = { // 弧线配置
						strokeOpacity: 0.8,
						strokeColor: getColor(weight), // 根据权重取颜色
						strokeWeight: getStrokeWeight(weight) // 根据权重取弧线粗细大小
						};
				from = new BMap.Point(from.x, from.y);
				to = new BMap.Point(to.x, to.y);

				curveline = new BMapLib.CurveLine([from, to], opt); // 构建弧线对象
				map.addOverlay(curveline); // 增加弧线到地图上
				let obj = {
						Curveline:curveline,
						Opt:opt,
				}
				redraw.push(obj);
				addArrow(curveline,opt);
			}
		});
	}).done(function(){
		let options = {
		        size: BMAP_POINT_SIZE_SMALL,
		        shape: BMAP_POINT_SHAPE_STAR,
		        color: '#d340c3'
		    	}
		map.removeOverlay(oceanPoints[0]);		//数组的第一值是所有海量点的集合
		pointCollection = new BMap.PointCollection(pointsNotNeedToClean, options);		//传入新数组
		pointCollection.addEventListener('click', function (e) {
	        alert('单击点的坐标为：' + e.point.lng + ',' + e.point.lat);  
	      });
		oceanPoints.push(pointCollection);
		map.addOverlay(pointCollection); // 将标注添加到地图中
	});

}

let drawHeatmap = function(stations){
	let a = [];	
	var points =[];
		let minNum = document.getElementById('minNum').value;
		let total=0,avg=0,Max = 0;
		$.getJSON("heatmap",$("form").serialize(),function(result){
			result.forEach(function (rel){
				let point = getXY(rel.STATIONID,stations,a); // 根据站点 id 获取坐标
					points.unshift({
						"lng":parseFloat(point.x),
						"lat":parseFloat(point.y),
						"count":parseInt(rel.BIKENUM)
					});
					Max = Max > parseInt(rel.BIKENUM) ? Max : parseInt(rel.BIKENUM);
					total += parseInt(rel.BIKENUM);

			});
			avg = total / result.length;
		}).done(function(){
			points.forEach( x => {
				let weight = ((x.count - minNum)/(Max-minNum)).toFixed(2);
				let point = new BMap.Point(x.lng, x.lat);
				let circle = new BMap.Circle(point,20,{strokeColor:getColor(weight), strokeWeight:getStrokeWeight(weight)+5, strokeOpacity:1}); //创建圆
				map.addOverlay(circle);        
			})
		});
}

let addArrow = function addArrow(lines,line_style) {
    let length = 14;
    let angleValue = Math.PI/7;
    let linePoint = lines.getPath();
    let arrowCount = linePoint.length;
    let middle = arrowCount / 2;
    let pixelStart = map.pointToPixel(linePoint[Math.floor(middle)]);
    let pixelEnd = map.pointToPixel(linePoint[Math.ceil(middle)]);
    let angle = angleValue;
    let r = length;
    let delta = 0;
    let param = 0;
    let pixelTemX, pixelTemY;
    let pixelX, pixelY, pixelX1, pixelY1;
    if (pixelEnd.x - pixelStart.x == 0) {
        pixelTemX = pixelEnd.x;
        if (pixelEnd.y > pixelStart.y) {
            pixelTemY = pixelEnd.y - r;
        } else {
            pixelTemY = pixelEnd.y + r;
        }
        pixelX = pixelTemX - r * Math.tan(angle);
        pixelX1 = pixelTemX + r * Math.tan(angle);
        pixelY = pixelY1 = pixelTemY;
    } else {
        delta = (pixelEnd.y - pixelStart.y) / (pixelEnd.x - pixelStart.x);
        param = Math.sqrt(delta * delta + 1);
        if ((pixelEnd.x - pixelStart.x) < 0) {
            pixelTemX = pixelEnd.x + r / param;
            pixelTemY = pixelEnd.y + delta * r / param;
        } else {
            pixelTemX = pixelEnd.x - r / param;
            pixelTemY = pixelEnd.y - delta * r / param;
        }
        pixelX = pixelTemX + Math.tan(angle) * r * delta / param;
        pixelY = pixelTemY - Math.tan(angle) * r / param;
        pixelX1 = pixelTemX - Math.tan(angle) * r * delta / param;
        pixelY1 = pixelTemY + Math.tan(angle) * r / param;
    }
    let pointArrow = map.pixelToPoint(new BMap.Pixel(pixelX, pixelY));
    let pointArrow1 = map.pixelToPoint(new BMap.Pixel(pixelX1, pixelY1));
    let Arrow = new BMap.Polyline([pointArrow, linePoint[Math.ceil(middle)], pointArrow1], line_style);
    arrow.push(Arrow);
    map.addOverlay(Arrow);

}

let getColor = function(weight) { // 求颜色插值
	if(weight < 0.5){
		const compute = d3.interpolate('#4caf50','#ffff00'); // 获取绿色到红色的
		return compute(weight);
	}else{
		const compute = d3.interpolate('#ffff00','#f44336'); // 获取绿色到红色的
		return compute(weight);
	}
}
let getStrokeWeight = function(weight) { // 求粗细大小
	const Max = 10; // 固定最大值为 10
	return Math.round(weight*Max);
}
let getXY = function(id,stations,pointsNotNeedToClean) { // 根据 id 获取具体站点数据
	var i = 0;
	for(i = 0;i<stations.length;i++){
		if(id == stations[i].stationID){
			pointsNotNeedToClean.push(new BMap.Point(stations[i].x, stations[i].y))
			return stations[i];
		} 
	}
	return null;
}


	


