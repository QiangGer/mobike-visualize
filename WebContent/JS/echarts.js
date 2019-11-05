$(document).ready(function() {
	$('#draw').click(function() {
		let myChart = echarts.init(document.getElementById('echarts'));
		myChart.resize({
			width:document.getElementById('allmap').clientwidth,
			height:document.getElementById('allmap').clientHeight * 3 / 7,
		})
		
		let data = {
			date : document.getElementById('datepicker').value,
			id : document.getElementById('stationID').innerHTML,
		};
		$.getJSON("draw",// 产生JSON数据的服务端页面
		data,// 向服务器发出的查询字符串（此参数可选）
		function(json) {
			let time = new Array();
			let leaseNum = new Array();
			let returnNum = new Array();
			for (let i = 0; i < json.length; i++) {
				time.push(json[i].leaseTime);
				leaseNum.push(json[i].leaseNum);
				returnNum.push(json[i].returnNum);
			}
			let option = {
				title : {
					text : '车辆借还统计'
				},
				tooltip : {},
				// 图例组件，位于图标上方，可控制显示与否
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
	$('#hide').click(function() {
		echart.hidden = true;
		document.getElementById('allmap').style.height = "100%";
		document.getElementById('echart').style.height = "0%";
	});
	
});