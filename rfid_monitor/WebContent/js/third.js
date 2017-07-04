

function update(){
	
	resetMachChart();
	resetWorkpieceChart();
	
}
function resetMachChart(){
	//显示调用ajax获取数据，然后给图标函数传值
	if($("#selectMachId").val()==""){
		alert("没有选择机床");
		return;
	}else{
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateMachChartServlet',
			data :  
				"&machId="+ $("#selectMachId").val() 
				,
			async : true,
			success : function(data) {
				if(data == "0"){
					machChart(0,0,0,0,0,0,0,0,0);
					alert("当前机床未与任何工序进行配置")
				}else{
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					
					machChart(result["w1"],result["w2"],result["p0"],result["p1"],result["p2"],result["p3"],result["p4"],result["p5"],result["p6"]);
				}
			}
		});
	}
}
function resetWorkpieceChart(){
	//显示调用ajax获取数据，然后给图标函数传值
	//显示调用ajax获取数据，然后给图标函数传值
	if($("#selectWorkpieceName").val()==""){
		alert("没用选择零件");
		return;
	}else{
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateWorkpieceChartServlet',
			data :  
				"&workpieceName="+ $("#selectWorkpieceName").val() 
				,
			async : true,
			success : function(data) {
				if(data == "0"){
					workpieceChart(0,0);
					alert("没找到该类零件")
				}else{
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					console.log(result);
					workpieceChart(result["w1"],result["w2"],result["w_1"],result["w_2"]);
				}
			}
		});
	}
	
}


function machChart(w1,w2,p0,p1,p2,p3,p4,p5,p6){
	
	var temp =p0*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_0 =parseFloat(temp.toFixed(2));
	
	temp =p1*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_1 =parseFloat(temp.toFixed(2));
	
	temp =p2*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_2 =parseFloat(temp.toFixed(2));
	
	temp =p3*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_3 =parseFloat(temp.toFixed(2));
	
	temp =p4*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_4 =parseFloat(temp.toFixed(2));
	
	temp =p5*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_5 =parseFloat(temp.toFixed(2));
	
	temp =p6*100/(p0+p1+p2+p3+p4+p4+p5+p6);
	var p_6 =parseFloat(temp.toFixed(2));
	
	   var brandsData = [{ name: '已完成',  y: p_6, drilldown: null}, 
		                   { name: '未完成',  y:(p_0+p_1+p_2+p_3+p_4+p_5), drilldown: 'nofinish' }
	                	 ];
	    
		var drilldownSeries = [ { name: 'nofinish',
							id: 'nofinish',
							data: [ [ '初始状态', p_0],['进入如缓存',p_1],['离开入缓存', p_2],  ['进入机床',p_3],['离开机床',p_4 ],['进入出缓存',p_5] ]
	                  	 }
	                 ];
	temp = w1*100/(w1+w2);
	var w1_print = "已完成"+temp.toFixed(2)+"%";
	temp =w2*100/(w1+w2);
	var w2_print = "未完成"+temp.toFixed(2)+"%";
	
	//设置全局配置
	 Highcharts.setOptions({
	     lang: {
	         printChart:"打印图表",
	           downloadJPEG: "下载JPEG 图片" , 
	           downloadPDF: "下载PDF文档"  ,
	           downloadPNG: "下载PNG 图片"  ,
	           downloadSVG: "下载SVG 矢量图" , 
	           exportButtonTitle: "导出图片" 
	     }
	 });

	//零件统计
	$('#container1').highcharts({
	   chart: {
	       type: 'pie',
	       options3d: {
	           enabled: true,
	           alpha: 45
	       }
	   },
	   credits: {
		   enabled: false,
		   href: "http://www.highcharts.com",
		   position: null,
		   style: null,
		   text: "Highcharts.com",
		   },
	   colors: ['#90ed7d','#7cb5ec', '#f7a35c', '#8085e9', 
	            '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
	   title: {
	       text: '零件信息统计'
	   },
	   subtitle: {
	       text: '零件总计：'+(w1+w2)
	   },
	   plotOptions: {
	       pie: {
	           innerSize: 50,
	           depth: 25
	       }
	   },
	   series: [{
	       name: '零件个数：',
	       data: [
	           [w1_print, w1],
	           [w2_print, w2],
	       
	       ]
	   }]
	});
	//工序图表        
	// console.log( brandsData);
	$('#container2').highcharts({
	   chart: {
	       type: 'column'
	   },
	   credits: {
		   enabled: false,
		   href: "http://www.highcharts.com",
		   position: null,
		   style: null,
		   text: "Highcharts.com",
		   },
	   colors: ['#90ed7d','#7cb5ec', '#f7a35c', '#8085e9', 
	         '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
	   title: {
	       text: '工序信息统计'
	   },
	   subtitle: {
	       text: '总工序数：'+(p0+p1+p2+p3+p4+p4+p5+p6)
	   },
	   xAxis: {
	       type: 'category'
	   },
	   yAxis: {
	       title: {
	           text: '百分比'
	       }
	   },
	   legend: {
	       enabled: false
	   },
	   plotOptions: {
	       series: {
	           borderWidth: 0,
	           dataLabels: {
	               enabled: true,
	               format: '{point.y:.1f}%'
	           }
	       }
	   },
	   tooltip: {
	       headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	       pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	   },
	   series: [{
	       name: '百分比',
	       colorByPoint: true,
	       data: brandsData
	   }],
	   drilldown: {
	       series: drilldownSeries
	   }
	});
}

function workpieceChart(w1,w2,w_1,w_2){
	 var temp = w1*100/(w1+w2);
	 var w1_print = "已完成"+temp.toFixed(2)+"%";
	temp =w2*100/(w1+w2);
	var w2_print = "未完成"+temp.toFixed(2)+"%";
	
	
	  temp = w_1*100/(w_1+w_2);
	 var w_1_print = "已完成"+temp.toFixed(2)+"%";
	temp =w_2*100/(w_1+w_2);
	var w_2_print = "未完成"+temp.toFixed(2)+"%";
	//设置全局配置
	 Highcharts.setOptions({
	     lang: {
	         printChart:"打印图表",
	           downloadJPEG: "下载JPEG 图片" , 
	           downloadPDF: "下载PDF文档"  ,
	           downloadPNG: "下载PNG 图片"  ,
	           downloadSVG: "下载SVG 矢量图" , 
	           exportButtonTitle: "导出图片" 
	     }
	 });

	//同类零件统计
	$('#container3').highcharts({
	   chart: {
	       type: 'pie',
	       options3d: {
	           enabled: true,
	           alpha: 45
	       }
	   },
	   credits: {
		   enabled: false,
		   href: "http://www.highcharts.com",
		   position: null,
		   style: null,
		   text: "Highcharts.com",
		   },
	   colors: ['#90ed7d','#7cb5ec', '#f7a35c', '#8085e9', 
	            '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
	   title: {
	       text: '零件信息统计'
	   },
	   subtitle: {
	       text: '零件总计：'+(w1+w2)
	   },
	   plotOptions: {
	       pie: {
	           innerSize: 100,
	           depth: 45
	       }
	   },
	   series: [{
	       name: '零件个数：',
	       data: [
	           [w1_print, w1],
	           [w2_print, w2],
	       
	       ]
	   }]
	});
	
	//零件统计
	$('#container4').highcharts({
	   chart: {
	       type: 'pie',
	       options3d: {
	           enabled: true,
	           alpha: 45
	       }
	   },
	   credits: {
		   enabled: false,
		   href: "http://www.highcharts.com",
		   position: null,
		   style: null,
		   text: "Highcharts.com",
		   },
	   colors: ['#90ed7d','#7cb5ec', '#f7a35c', '#8085e9', 
	            '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
	   title: {
	       text: '零件信息统计'
	   },
	   subtitle: {
	       text: '零件总计：'+(w_1+w_2)
	   },
	   plotOptions: {
	       pie: {
	           innerSize: 100,
	           depth: 45
	       }
	   },
	   series: [{
	       name: '零件个数：',
	       data: [
	           [w_1_print, w_1],
	           [w_2_print, w_2],
	       
	       ]
	   }]
	});
}
