		
 
/*这段代码是利用websoc实时查询数据库	，更新工序监控*/	
		//这里地址要要正确，负责不能websocket，重要！！！！！！
//var webSocket = new WebSocket('ws://115.154.191.26:8080/rfid_monitor/websocket');
var webSocket = new WebSocket('ws://115.154.191.5:8080/rfid_monitor/websocket');
//var webSocket = new WebSocket('ws://192.168.1.131:8080/rfid_monitor/websocket');
//alert("启动ebsocket：");
webSocket.onerror = function(event) {
	onError(event)
};

webSocket.onopen = function(event) {
	console.log("开启websocket：");
	onOpen(event)
};

webSocket.onmessage = function(event) {
	console.log(event);
	onMessage(event)
	
	
};

		
	//用websocket方法，把工序追踪输出到展示层，而且还是预先写好div块，主要不想用js输出，觉得麻烦，就预先定义了7个div块，手动修改隐藏及数据内容
	/*	function onMessage(event) {
			//event比servlet获得json数据多了一层，data：{servlet的数据}
			for(var j=1;j<=7;j++){
				document.getElementById("process"+j).style.display="none";
				document.getElementById("processInfo"+j).innerHTML="无数据";
				document.getElementById("machInfo"+j).innerHTML="无数据";
				
				document.getElementById("time"+j).innerHTML="无数据";
				document.getElementById("startTime"+j).innerHTML="无数据";
				document.getElementById("machTime"+j).innerHTML="无数据";
				document.getElementById("inTime"+j).innerHTML="无数据";
				document.getElementById("outTime"+j).innerHTML="无数据";
				for (var i = 1; i <=6; i++) {
					document.getElementById("time"+j+""+i).innerHTML="";
					var div = document.getElementById(""+j+""+i);
					div.className = ''; 
				}
			}
			if(event["data"] == "0"){
				console.log("result['data']="+event["data"] );
				//document.getElementById("detialWorkpiece").innerHTML = "当前零件还未配置工序";
			}else{
				
				var data = event["data"]; //js中解析Json格式到result中
				//console.log(data);
				
				
				var result = eval("(" + data + ")");
				var json1 = result.rows; // rows是数组
				console.log(json1);
				for (var j = 0; j < json1.length; ) {
					var json = json1[j];
					j++;
					json["workpieceId"];
					json["workpieceName"];
					json["processId"];
					//alert(json["processId"]);
					json["processName"];
					json["machId"];
					json["machName"];
					json["state"];
					json["level"];
					document.getElementById("process"+j).style.display="";
					document.getElementById("processInfo"+j).innerHTML=json["processName"]+" --------编号："+json["processId"];
					document.getElementById("machInfo"+j).innerHTML=json["machName"]+" --------编号："+json["machId"];
					
					document.getElementById("time"+j).innerHTML=json["allTime"];
					document.getElementById("startTime"+j).innerHTML=json["startTime"];
					document.getElementById("machTime"+j).innerHTML=json["machTime"];
					document.getElementById("inTime"+j).innerHTML=json["inTime"];
					document.getElementById("outTime"+j).innerHTML=json["outTime"];
					
					var events =json.event;
							console.log(events);
						for (var i = 1; i <= json["state"]; i++) {
							var e =events[i];
							
							document.getElementById("time"+j+i).innerHTML=e;
							var div = document.getElementById(""+j+i); 
							if(i==6){
								div.className = 'complete'; 
							}else{
								div.className = 'active'; 
							}
						}
				}
			}
		}*/
		//新message，用js实时输出htnml到追踪展示div中
		function onMessage(event) {
			//event比servlet获得json数据多了一层，data：{servlet的数据}
			//console.log(typeof(event));
			/*var str =JSON.stringify(event);
			console.log(typeof(str));
			console.log(str);*/
			if(event["data"] == "0"){
				console.log("result['data']="+event["data"] );
				document.getElementById("content").innerHTML="";
				//document.getElementById("detialWorkpiece").innerHTML = "当前零件还未配置工序";
			}else{
				//content 为整体后html输出
				var content="";
				
				
				var data = event["data"]; //js中解析Json格式到result中
				//console.log(data);
				
				
				var result = eval("(" + data + ")");
				var json1 = result.rows; // rows是数组
				//console.log(json1);
				for (var j = 0; j < json1.length; ) {
					//没进入一次循环代表一个工序，样式表和时间要重置
					var cssStep=new Array();
					var time=new Array();
					cssStep=["","","","","","",""];
					time =[".....",".....",".....",".....",".....",".....",".....",".....",];
					var json = json1[j];
					j++;
					
					json["processId"];
					json["processName"];
					json["machId"];
					json["machName"];
					json["state"];
					json["level"];
					json["allTime"];
					json["startTime"];
					json["machTime"];
					json["inTime"];
					json["outTime"];
					var events =json.event;
					//console.log(events);
					for (var i = 1; i <= json["state"]; i++) {
						var e =events[i];
						
						time[i]=e;
						if(i==6){
							cssStep[i] = 'complete'; 
						}else{
							cssStep[i] = 'active'; 
						}
					}
					content+=	
					'<!-- 第'+j+'道工序 -->'+
					'<div class="col-xs-12 " id="process'+j+'" >'+
						'<div class="widget-box">'+
						'	<div class="widget-header">'+
								'<h4>第'+j+'道工序</h4>'+

							'	<span class="widget-toolbar"> <!-- <a href="#" data-action="settings">'+
							'					<i class="icon-cog"></i>'+
							'				</a> --> <a href="#" data-action="reload"> <i'+
							'			class="icon-refresh"></i>'+
							'	</a> <a href="#" data-action="collapse"> <i'+
							'			class="icon-chevron-up"></i>'+
							'	</a> <!-- <a href="#" data-action="close">'+
							'					<i class="icon-remove"></i>'+
							'				</a> -->'+
							'	</span>'+
							'</div>';
					
					
					content+= ' <div class="widget-body">'+
							'	<div class="widget-main">'+
				
							'	<div id="fuelux-wizard" class="row-fluid"'+
							'		data-target="#step-container">'+
				
							'		<ul class="wizard-steps">'+
							'			<li  id="'+j+'1" data-target="#step1" class="'+cssStep[1]+'"><span'+
							'				class="step" >1</span> <span class="title"  data-toggle="tooltip" data-placement="bottom" title="'+time[1]+'">进入入缓存</span> '+
							'				</li>'+
				
							'			<li id="'+j+'2" data-target="#step2" class="'+cssStep[2]+'"><span class="step">2</span>'+
							'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[2]+'">离开入缓存</span> '+
							'				</li>'+
				
							'			<li id="'+j+'3" data-target="#step3" class="'+cssStep[3]+'"><span class="step">3</span>'+
							'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[3]+'">进入机床</span> '+
							'				</li>'+
				
							'			<li id="'+j+'4" data-target="#step4" class="'+cssStep[4]+'"><span class="step">4</span>'+
							'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[4]+'">离开机床</span> '+
							'				</li>'+
							'			<li id="'+j+'5" data-target="#step5" class="'+cssStep[5]+'"><span class="step">5</span>'+
							'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[5]+'">进入出缓存</span> '+
							'				</li>'+
							'			<li id="'+j+'6" data-target="#step6" class="'+cssStep[6]+'"><span class="step">6</span>'+
							'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[6]+'">离开出缓存</span> '+
							'				</li>'+
							'		</ul>'+
				
				
							'	</div>'+
							'	<hr>';
					
					content+=	'<!-- 	//计算部分吧 开始-->'+
								'	<div class="row">'+
								'	<div class="col-md-6 col-md-offset-3">'+
						
								'		<div class="profile-user-info profile-user-info-striped">'+
								'			<div class="profile-info-row">'+
								'				<div class="profile-info-name">工序信息</div>'+
						
								'				<div class="profile-info-value">'+
								'					<span class="editable" id="processInfo'+j+'">'+json["processName"]+" --------编号："+json["processId"]+
								'						</span>'+
								'				</div>'+
								'			</div>'+
						
								'			<div class="profile-info-row">'+
								'				<div class="profile-info-name">机床信息</div>'+
						
								'				<div class="profile-info-value">'+
								'					<i class="icon-map-marker light-orange bigger-110"></i>'+
								'					<span class="editable" id="machInfo'+j+'">'+json["machName"]+" --------编号："+json["machId"]+'</span>'+
								'				</div>'+
								'			</div>'+
						
								'			<div class="profile-info-row">'+
								'				<div class="profile-info-name">工序总时长</div>'+
						
								'				<div class="profile-info-value">'+
								'					<span class="editable" id="time'+j+'">'+json["allTime"]+'</span>'+
								'				</div>'+
								'			</div>'+
						
						'					<div class="profile-info-row">'+
						
						'						<div class="profile-info-name">工序起始时间</div>'+
						
						'						<div class="profile-info-value">'+
						'							<span class="editable" id="startTime'+j+'">'+json["startTime"]+'</span>'+
						'						</div>'+
						'					</div>'+
						
						'					<div class="profile-info-row">'+
						'						<div class="profile-info-name">机床加工耗时</div>'+
						
						'						<div class="profile-info-value">'+
						'							<span class="editable" id="machTime'+j+'">'+json["machTime"]+
						'								</span>'+
						'						</div>'+
						'					</div>'+
						
						'					<div class="profile-info-row">'+
						'						<div class="profile-info-name">入缓存等待时间</div>'+
						
							'					<div class="profile-info-value">'+
							'						<span class="editable" id="inTime'+j+'">'+json["inTime"]+
							'							</span>'+
							'					</div>'+
							'				</div>'+
							'				<div class="profile-info-row">'+
							'					<div class="profile-info-name">出缓存等待时间</div>'+
						
							'					<div class="profile-info-value">'+
							'						<span class="editable" id="outTime'+j+'">'+json["outTime"]+
							'							</span>'+
							'					</div>'+
							'				</div>'+
							'			</div>'+
							'		</div>'+
							'	</div>'+
							'	<!-- 	//计算部分吧 -->';
				
				content+="</div></div></div></div>";
						
				}
				document.getElementById("content").innerHTML=content;
				
				$(function () { $("[data-toggle='tooltip']").tooltip(); });
			}
		}
		function onOpen (event) {
			//document.getElementById('messages').innerHTML 
				//= 'Connection established';
				
		}

		function onError(event) {
			alert(event.data);
		}

		function getInfo(workpieceId) {
			/*if(TrimAll(workpieceId)==""){
				document.getElementById("content").innerHTML="";
			}else{*/
				webSocket.send(workpieceId);
			/*}*/
		}
		function start(){
			getInfo($("#selectWorkpieceId").val());
			
		}
/*这段代码是利用websoc实时查询数据库	，更新工序监控*/	
function go(url){
	
	 webSocket.close();
	// alert("guan bi web");
	 
	
		 self.location=url; 
	 
	
}
function update(){
//	updateMachList();//更新机床下拉框
	updateWorkpieceList();
	setInterval(start,10000);

}


/*	 更新下拉框，机床的 ，
	function updateMachList() {
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/GetMachListServlet',
			async : false,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectMachId").innerHTML = "";
				var obj = document.getElementById("selectMachId");
				if(data == "0"){
					obj.add(new Option("--无机床--", ""));
					//设置提交按钮失效
					//var machRfid = document.getElementById("machRfid");
				//	machRfid.disabled=true; 
					
				}else{
					//设置提交按钮回复正常
					//var machRfid = document.getElementById("machRfid");
					//machRfid.disabled=false; 
					
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						obj.add(new Option("机床名称:"+json["machName"], json["machId"]),
								undefined);
					}
					
				}
				
			}
		});
	}*/
		
	
/*	 更新下拉框，零件的 
	function updateWorkpieceList() {
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateWorkpieceListByMachIdServlet',
			data :  
				"&machId="+ $("#selectMachId").val() 
				
				,
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectWorkpieceId").innerHTML = "";
				var obj = document.getElementById("selectWorkpieceId");
				if(data == "0"){
					obj.add(new Option("--无可用零件--", ""));
					//设置提交按钮失效
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					//workpieceProcessSubmit.disabled=true; 
					
				}else{
					//obj.add(new Option("--零件列表--", ""));
					//设置提交按钮回复正常
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					//workpieceProcessSubmit.disabled=false; 
					
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						obj.add(new Option(json["workpieceName"]+"【唯一标识号："+json["workpieceId"]+"】", json["workpieceId"]),
								undefined);
					}
					
				}
				
			}
		});
	}*/
	/* 更新下拉框，零件的 */
	function updateWorkpieceList() {
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateWorkpieceListServlet',
			
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectWorkpieceId").innerHTML = "";
				var obj = document.getElementById("selectWorkpieceId");
				if(data == "0"){
					obj.add(new Option("--无可用零件--", ""));
					//设置提交按钮失效
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					//workpieceProcessSubmit.disabled=true; 
					
				}else{
					obj.add(new Option("--零件列表--", ""));
					//设置提交按钮回复正常
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					//workpieceProcessSubmit.disabled=false; 
					
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						if(json["flag"]=="1"){
							obj.add(new Option(json["workpieceName"]+"【唯一标识号："+json["workpieceId"]+"】", json["workpieceId"]),
									undefined);
						}
						
					}
					
				}
				
			}
		});
	}
	
	function showDetialWorkpiece(){
		document.getElementById("workpieceId").innerHTML = "请选择零件";
		document.getElementById("workpieceName").innerHTML = "请选择零件";
		document.getElementById("workpieceRfid").innerHTML = "请选择零件";
		document.getElementById("processInfo").innerHTML = "请选择零件";
		
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/DetialWorkpieceServlet',
			data :  
				"&workpieceId="+ $("#selectWorkpieceId").val() 
				
				,
			async : true,
			success : function(data) {
				
				//更新下拉框
				//document.getElementById("detialWorkpiece").innerHTML = "";
				//var obj = document.getElementById("detialWorkpiece");
				if(data == "0"){
					//document.getElementById("detialWorkpiece").innerHTML = "当前零件还未配置工序";
					
				}else{
					
					
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						document.getElementById("workpieceId").innerHTML = json["workpieceId"];
					document.getElementById("workpieceName").innerHTML = json["workpieceName"];
					document.getElementById("workpieceRfid").innerHTML = json["workpieceRfid"];
					document.getElementById("processInfo").innerHTML = json["processInfo"];
						
					}
					
				}
				
			}
		});
	}
	//用servelt方法，把工序追踪输出到展示层，而且还是预先写好div块，主要不想用js输出，觉得麻烦，就预先定义了7个div块，手动修改隐藏及数据内容
	function showDetialWorkpieceProcess(){
		
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/DetialWorkpieceProcessServlet',
			data :  
				"&workpieceId="+ $("#selectWorkpieceId").val() 
				
				,
			async : true,
			success : function(data) {
				console.log(data);
				if(data== "0"){
					console.log("data="+data );
					document.getElementById("content").innerHTML="";
					//document.getElementById("detialWorkpiece").innerHTML = "当前零件还未配置工序";
				}else{
					//content 为整体后html输出
					var content="";
					
					
					//var data = event["data"]; //js中解析Json格式到result中
					//console.log(data);
					
					
					var result = eval("(" + data + ")");
					var json1 = result.rows; // rows是数组
					//console.log(json1);
					for (var j = 0; j < json1.length; ) {
						//没进入一次循环代表一个工序，样式表和时间要重置
						var cssStep=new Array();
						var time=new Array();
						cssStep=["","","","","","",""];
						time =[".....",".....",".....",".....",".....",".....",".....",".....",];
						var json = json1[j];
						j++;
						
						json["processId"];
						json["processName"];
						json["machId"];
						json["machName"];
						json["state"];
						json["level"];
						json["allTime"];
						json["startTime"];
						json["machTime"];
						json["inTime"];
						json["outTime"];
						var events =json.event;
						//console.log(events);
						for (var i = 1; i <= json["state"]; i++) {
							var e =events[i];
							
							time[i]=e;
							if(i==6){
								cssStep[i] = 'complete'; 
							}else{
								cssStep[i] = 'active'; 
							}
						}
						content+=	
						'<!-- 第'+j+'道工序 -->'+
						'<div class="col-xs-12 " id="process'+j+'" >'+
							'<div class="widget-box">'+
							'	<div class="widget-header">'+
									'<h4>第'+j+'道工序</h4>'+

								'	<span class="widget-toolbar"> <!-- <a href="#" data-action="settings">'+
								'					<i class="icon-cog"></i>'+
								'				</a> --> <a href="#" data-action="reload"> <i'+
								'			class="icon-refresh"></i>'+
								'	</a> <a href="#" data-action="collapse"> <i'+
								'			class="icon-chevron-up"></i>'+
								'	</a> <!-- <a href="#" data-action="close">'+
								'					<i class="icon-remove"></i>'+
								'				</a> -->'+
								'	</span>'+
								'</div>';
						
						
						content+= ' <div class="widget-body">'+
								'	<div class="widget-main">'+
					
								'	<div id="fuelux-wizard" class="row-fluid"'+
								'		data-target="#step-container">'+
					
								'		<ul class="wizard-steps">'+
								'			<li  id="'+j+'1" data-target="#step1" class="'+cssStep[1]+'"><span'+
								'				class="step" >1</span> <span class="title"  data-toggle="tooltip" data-placement="bottom" title="'+time[1]+'">进入入缓存</span> '+
								'				</li>'+
					
								'			<li id="'+j+'2" data-target="#step2" class="'+cssStep[2]+'"><span class="step">2</span>'+
								'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[2]+'">离开入缓存</span> '+
								'				</li>'+
					
								'			<li id="'+j+'3" data-target="#step3" class="'+cssStep[3]+'"><span class="step">3</span>'+
								'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[3]+'">进入机床</span> '+
								'				</li>'+
					
								'			<li id="'+j+'4" data-target="#step4" class="'+cssStep[4]+'"><span class="step">4</span>'+
								'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[4]+'">离开机床</span> '+
								'				</li>'+
								'			<li id="'+j+'5" data-target="#step5" class="'+cssStep[5]+'"><span class="step">5</span>'+
								'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[5]+'">进入出缓存</span> '+
								'				</li>'+
								'			<li id="'+j+'6" data-target="#step6" class="'+cssStep[6]+'"><span class="step">6</span>'+
								'				<span class="title" data-toggle="tooltip" data-placement="bottom" title="'+time[6]+'">离开出缓存</span> '+
								'				</li>'+
								'		</ul>'+
					
					
								'	</div>'+
								'	<hr>';
						
						content+=	'<!-- 	//计算部分吧 开始-->'+
									'	<div class="row">'+
									'	<div class="col-md-6 col-md-offset-3">'+
							
									'		<div class="profile-user-info profile-user-info-striped">'+
									'			<div class="profile-info-row">'+
									'				<div class="profile-info-name">工序信息</div>'+
							
									'				<div class="profile-info-value">'+
									'					<span class="editable" id="processInfo'+j+'">'+json["processName"]+" --------编号："+json["processId"]+
									'						</span>'+
									'				</div>'+
									'			</div>'+
							
									'			<div class="profile-info-row">'+
									'				<div class="profile-info-name">机床信息</div>'+
							
									'				<div class="profile-info-value">'+
									'					<i class="icon-map-marker light-orange bigger-110"></i>'+
									'					<span class="editable" id="machInfo'+j+'">'+json["machName"]+" --------编号："+json["machId"]+'</span>'+
									'				</div>'+
									'			</div>'+
							
									'			<div class="profile-info-row">'+
									'				<div class="profile-info-name">工序总时长</div>'+
							
									'				<div class="profile-info-value">'+
									'					<span class="editable" id="time'+j+'">'+json["allTime"]+'</span>'+
									'				</div>'+
									'			</div>'+
							
							'					<div class="profile-info-row">'+
							
							'						<div class="profile-info-name">工序起始时间</div>'+
							
							'						<div class="profile-info-value">'+
							'							<span class="editable" id="startTime'+j+'">'+json["startTime"]+'</span>'+
							'						</div>'+
							'					</div>'+
							
							'					<div class="profile-info-row">'+
							'						<div class="profile-info-name">机床加工耗时</div>'+
							
							'						<div class="profile-info-value">'+
							'							<span class="editable" id="machTime'+j+'">'+json["machTime"]+
							'								</span>'+
							'						</div>'+
							'					</div>'+
							
							'					<div class="profile-info-row">'+
							'						<div class="profile-info-name">入缓存等待时间</div>'+
							
								'					<div class="profile-info-value">'+
								'						<span class="editable" id="inTime'+j+'">'+json["inTime"]+
								'							</span>'+
								'					</div>'+
								'				</div>'+
								'				<div class="profile-info-row">'+
								'					<div class="profile-info-name">出缓存等待时间</div>'+
							
								'					<div class="profile-info-value">'+
								'						<span class="editable" id="outTime'+j+'">'+json["outTime"]+
								'							</span>'+
								'					</div>'+
								'				</div>'+
								'			</div>'+
								'		</div>'+
								'	</div>'+
								'	<!-- 	//计算部分吧 -->';
					
					content+="</div></div></div></div>";
							
					}
					document.getElementById("content").innerHTML=content;
					
					$(function () { $("[data-toggle='tooltip']").tooltip(); });
				}
				
			}
		});
	}
	
	