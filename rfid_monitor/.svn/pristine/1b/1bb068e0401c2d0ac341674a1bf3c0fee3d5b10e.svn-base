function updata(){
			updataRfidList();//更新机床下拉框
			updataMachList();//更新系统下拉框
			updataWorkpieceList();//更新零件下拉框
			document.getElementById('processName').disabled = true;
			document.getElementById('processId').disabled = true;
			
}
function addMach(){
if ( $("#machId").val() == ""||  $("#machName").val() == "") {
	alert("不能为空！");
} else {
	
	if(checkMachId()==true){
		alert("唯一编码已存在");
		return;
	}else{
		$.ajax({
			type : "POST",
			url : '/mes_online/AddMachServlet',
			data :  
					"&machId="+ $("#machId").val() +
					"&machName="+ $("#machName").val() 
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("添加机床成功");
					updataMachList();
					location.reload();
				} else {
					alert("添加机床失败");
				}
			}
		});
	}
	}
	
}

function deleteMach(machId){
	
	$.ajax({
		type : "POST",
		url : '/mes_online/DeleteMachServlet',
		data :  
				"&machId="+machId
				,
		async : true,
		success : function(msg) {
			if (msg =="success") {
				//msg表示传递过来几个数据																	
				//在表格中显示	
				//配置成功后，这会扩充点击已配置信息的table表
				alert("删除机床成功");
				//重新加载，并显示模态
				location.reload();
				$('#machModal').modal('show');
				
			} else {
				alert("删除机床失败");
			}
		}
	});
	
}
//用来检测输入的机床唯一的id是否已存在 ，
	function checkMachId(){
		//这里曾经遇见过用 alert（）调用此函数，一直报错说undefined ，不知道为什么，不能再回调函数中进行return，把return 放到最外层
		var flag = false;
		var machId = document.getElementById("machId");
		if(isEmpty(machId.value)){
			document.getElementById("checkMachId").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/mes_online/MachIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{machId:machId.value}, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkMachId").innerHTML=" 编码已存在!!!!";
					var machId = document.getElementById("machId");
					//mach_Id.focus();
					flag = true;
				}else{
					document.getElementById("checkMachId").innerHTML=" 编码可用！";
					
				}	
			}
		});
		//放到这解决问题
		return flag;
	}
	
	/* 更新下拉框，机床的 ，主要是更新新加的机床和剔除已经和rfid系统绑定的机床，为绑定机床和rfid系统服务*/
	function updataMachList() {
		$.ajax({
			type : "POST",
			url : '/mes_online/UpdataMachListServlet',
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectMachId").innerHTML = "";
				var obj = document.getElementById("selectMachId");
				if(data == "0"){
					obj.add(new Option("--无可用机床--", ""));
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
	}
	
	function editMach(machId,machName){
		document.getElementById("machIdForEdit").value=machId;
		document.getElementById("machNameForEdit").value=machName;
		$('#editMachModal').modal('show');
		
	}
	function submitEditMach(){
		if ( $("#machIdForEdit").val() == ""||  $("#machNameForEdit").val() == "") {
			alert("不能为空！");
		} else {
				$.ajax({
					type : "POST",
					url : '/mes_online/EditMachServlet',
					data :  
							"&machId="+ $("#machIdForEdit").val() +
							"&machName="+ $("#machNameForEdit").val() 
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("修改成功");
							updataMachList();
							location.reload();
						} else {
							alert("修改失败");
						}
					}
				});
			
			
			
		}
		
	}
	//用来查看机床，通过ajax实时改变表格内容，不知道为什么出问题，还是模态吧，每次添加新的东西重新刷新界面
	function viewMach(){
	
		$.ajax({
			type : "POST",
			url : '/mes_online/ViewMachServlet',
		
			async : false,
			success : function(data) {
				
			/*	$("#sample-table-list1 tbody").innerHTML = "";
				var obj = $("#sample-table-list1 tbody");*/
				document.getElementById("machList").innerHTML="";
				//var obj = $("#machList");
				var show = "";
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					var other =" <a class='btn btn-sm btn-danger' onclick='alert(1)' href='#'> 删除设备</a><a class='btn btn-sm btn-info' href=''> 修改设备</a> "
					var addTr = "<tr><td>" + json["machId"] + "</td><td>"
					+ json["machName"]+ "</td><td>" + other
					+ "</td></tr>";
					show =show +addTr;
					
				}
				//alert(show);
				document.getElementById("machList").innerHTML=show;
				
			}
		});
	}
	
	
	
	//用来检测输入的rfid唯一的id是否已存在 ，
	function checkRfidId(){
		var flag =false;
		var rfidId = document.getElementById("rfidId");
		if(isEmpty(rfidId.value)){
			document.getElementById("checkRfidId").innerHTML=" 代码不能为空！";
			//rfid_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/mes_online/RfidIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{rfidId:rfidId.value}, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkRfidId").innerHTML=" 编码已存在!!!!";
					var rfidId = document.getElementById("rfidId");
					//mach_Id.focus();
					flag = true;;
				}else{
					document.getElementById("checkRfidId").innerHTML=" 编码可用！";
					
				}	
			}
		});	
		return flag;
	}
	
	function addRfid(){
		if ( $("#rfidId").val() == ""||  $("#rfidName").val() == ""||  $("#rfidIp").val() == "") {
			alert("不能为空！");
		} else {
			if(checkRfidId()==true){
				alert("唯一编码已存在");
				return;
			}else{
				$.ajax({
					type : "POST",
					url : '/mes_online/AddRfidSystemServlet',
					data :  
							"&rfidId="+ $("#rfidId").val() +
							"&rfidName="+ $("#rfidName").val() +
							"&rfidIp="+ $("#rfidIp").val() 
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加rfid成功");
							updataRfidList();
							location.reload();
						} else {
							alert("添加rfid失败");
						}
					}
				});
			}
		}
		
	}
	
	function editRfid(rfidId,rfidName,rfidIp){
		document.getElementById("rfidIdForEdit").value=rfidId;
		document.getElementById("rfidNameForEdit").value=rfidName;
		document.getElementById("rfidIpForEdit").value=rfidIp;
		$('#editRfidModal').modal('show');
		
	}
	function submitEditMach(){
		if ( $("#rfidIdForEdit").val() == ""||  $("#rfidNameForEdit").val() == ""||  $("#rfidIpForEdit").val() == "") {
			alert("不能为空！");
		} else {
				$.ajax({
					type : "POST",
					url : '/mes_online/EditRfidSystemServlet',
					data :  
							"&rfidId="+ $("#rfidIdForEdit").val() +
							"&rfidName="+ $("#rfidNameForEdit").val() +
							"&rfidIp="+ $("#rfidIpForEdit").val() 
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("修改成功");
							updataMachList();
							location.reload();
						} else {
							alert("修改失败");
						}
					}
				});
			
			
			
		}
		
	}
	function deleteRfidSystem(rfidId){
		
		$.ajax({
			type : "POST",
			url : '/mes_online/DeleteRfidSystemServlet',
			data :  
					"&rfidId="+rfidId
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("删除rfid系统成功");
					//重新加载，并显示模态
					location.reload();
					
					
				} else {
					alert("删除rfid系统失败");
				}
			}
		});
		
	}
	
	/* 更新下拉框，rfid系统的 ,主要是剔除已经绑定的rfid系统和更新新加入的系统，为绑定部分服务*/
	function updataRfidList() {
		$.ajax({
			type : "POST",
			url : '/mes_online/UpdataRfidListServlet',
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectRfidSysId").innerHTML = "";
				var obj = document.getElementById("selectRfidSysId");
				if(data == "0"){
					obj.add(new Option("--无可用rfid系统--", ""));
					//设置提交按钮失效
					//var machRfid = document.getElementById("machRfid");
					//machRfid.disabled=true; 
					
				}else{
					//设置提交按钮回复正常
					//var machrfid = document.getElementById("machRfid");
					//machRfid.disabled=false; 
					
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						obj.add(new Option("系统名称:"+json["rfidName"], json["rfidSysId"]),
								undefined);
					}
					//flag1 = 0;
				}
				
			}
		});
	}

	
//提交rfid系统配置信息
	
	function machRfidsubmit() {
		if ( $("#selectMachId").val() == ""||$("#selectRfidSysId").val() == "") {
			alert("请选择要绑定的机床和rfid系统！");
		} else {
			/*if(checkMachId()==false){
				console.log("dao zhe ");
				return;
			}*/
			//alert(ids[0]);
			//	alert(ids[1]);		
			$.ajax({
				type : "POST",
				url : '/mes_online/MachRfidbBangdingServlet',
				data :  
						"&machId="+ $("#selectMachId").val() +
						"&rfidSysId="+ $("#selectRfidSysId").val() 
						,
				async : true,
				success : function(msg) {
					if (msg =="success") {
						//msg表示传递过来几个数据																	
						//在表格中显示	
						//配置成功后，这会扩充点击已配置信息的table表
						alert("配置rfid系统成功");
						updataRfidList();
						updataMachList();
						location.reload();
					} else {
						alert("配置rfid系统失败");
					}
				}
			});
		}
	}
	
	function deleteMachRfid(rfidId,machId){
		console.log(rfidId+machId);
		$.ajax({
			type : "POST",
			url : '/mes_online/DeleteMachRfidServlet',
			data :  
					"&rfidId="+rfidId+
					"&machId="+machId
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("删除成功");
					//重新加载，并显示模态
					location.reload();
					
					
				} else {
					alert("删除失败");
				}
			}
		});
		
	}
	
	//用来检测输入的零件唯一的id是否已存在 ，
	function checkWorkpieceId(){
		var flag = false;
		var workpieceId= document.getElementById("workpieceId");
		if(isEmpty(workpieceId.value)){
			document.getElementById("checkWorkpieceId").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/mes_online/WorkpieceIdIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{workpieceId:workpieceId.value}, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkWorkpieceId").innerHTML=" 编码已存在!!!!";
					var WorkpieceId = document.getElementById("WorkpieceId");
					//WorkpieceId.focus();
					flag = true;
				}else{
					document.getElementById("checkWorkpieceId").innerHTML=" 编码可用！";
					
				}	
			}
		});
		return flag;
	}
	
	//用来检测输入的零件唯一的id是否已存在 ，
	function checkWorkpieceRfId(){
		var flag = false;
		var workpieceRfId= document.getElementById("workpieceRfId");
		if(isEmpty(workpieceRfId.value)){
			document.getElementById("checkWorkpieceRfId").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/mes_online/WorkpieceRfIdIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{workpieceRfId:workpieceRfId.value}, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkWorkpieceRfId").innerHTML=" 编码已存在!!!!";
					var WorkpieceRfId = document.getElementById("WorkpieceRfId");
					//WorkpieceId.focus();
					flag = true;
				}else{
					document.getElementById("checkWorkpieceRfId").innerHTML=" 编码可用！";
					
				}	
			}
		});
		return flag;
	}
	//添加零件
	function addWorkpiece(){
		if ( $("#workpieceName").val() == ""||  $("#workpieceId").val() == ""||  $("#workpieceRfId").val() == "") {
			alert("不能为空！");
		} else {
			if(checkWorkpieceRfId()==true||checkWorkpieceId()==true){
				alert("唯一编码已存在");
				return;
			}else{
				$.ajax({
					type : "POST",
					url : '/mes_online/AddWorkpieceServlet',
					data :  
							"&workpieceName="+ $("#workpieceName").val() +
							"&workpieceId="+ $("#workpieceId").val() +
							"&workpieceRfId="+ $("#workpieceRfId").val() 
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加零件成功");
							
							location.reload();
						} else {
							alert("添加零件失败");
						}
					}
				});
			}
		}
		
	}
	
	function deleteWorkpiece(workpieceId){
		
		$.ajax({
			type : "POST",
			url : '/mes_online/DeleteWorkpieceServlet',
			data :  
					"&workpieceId="+workpieceId
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("删除成功");
					//重新加载，并显示模态
					location.reload();
					
					
				} else {
					alert("删除失败");
				}
			}
		});
		
	}
	function showWorkpieceDetial(workpieceId){
		var flag =false;
		$.ajax({
			type : "POST",
			url : '/mes_online/ShowWorkpieceDetialServlet',
			data :  
					"&workpieceId="+workpieceId
					,
			async : false,
			success : function(data) {
				if (data =="0") {
					flag =false;
					//alert("当前没有工序录入到该零件");
				} else {
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					var show="";
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						var title = "零件："+json["workpieceName"]+"————————编号："+json["workpieceId"];
						var addTr = "<tr>" +
								"<td>" + (j+1) + "</td>" +
								"<td>"+ json["processName"]+ "</td>" +
								"<td>" + json["processId"]+ "</td>" +
								"<td>" + json["machName"]+ "</td>" +
								"<td>" + json["state"]+ "</td>" +
								"</tr>";
						show =show +addTr;
						
					}
					
					document.getElementById("processList").innerHTML=show;	
					document.getElementById("workpieceDetialTitle").innerHTML=title;	
					flag=true;
				}
			}
		});
		if(flag){
			$('#workpieceDetialModal').modal('show');
		}else{
			alert("当前没有工序录入到该零件");
		}
		
		
		
	}
	/* 更新下拉框，零件的 */
	function updataWorkpieceList() {
		$.ajax({
			type : "POST",
			url : '/mes_online/UpdataWorkpieceListServlet',
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectWorkpieceId").innerHTML = "";
				var obj = document.getElementById("selectWorkpieceId");
				if(data == "0"){
					obj.add(new Option("--无可用零件--", ""));
					//设置提交按钮失效
					var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					workpieceProcessSubmit.disabled=true; 
					
				}else{
					obj.add(new Option("--零件列表--", ""));
					//设置提交按钮回复正常
					var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					workpieceProcessSubmit.disabled=false; 
					
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
	}
	
	//当选择的零件发生变化时，实时更新提示工序消息
	function getProcessCountByWorkpieceId(){
		if ( $("#selectWorkpieceId").val() == "") {
			document.getElementById("processConutOfWorkpiece").innerHTML = "选择零件并按工序优先级录入录入工序";
			document.getElementById('processName').disabled = true;
			document.getElementById('processId').disabled = true;
		} else {
					
			$.ajax({
				type : "POST",
				url : '/mes_online/GetProcessCountByWorkpieceIdServlet',
				data :  
						"&selectWorkpieceId="+ $("#selectWorkpieceId").val() 
						,
				async : true,
				success : function(msg) {
					document.getElementById('processName').disabled = false;
					document.getElementById('processId').disabled = false;
					document.getElementById("processConutOfWorkpiece").innerHTML = "";
					if (msg =="0") {
						document.getElementById("processConutOfWorkpiece").innerHTML = 
						"当前零件还未配置工序，请输入第一道工序";
					} else {
						document.getElementById("processConutOfWorkpiece").innerHTML = 
							"当前零件已配置第"+msg+
							"道工序，请输入下一道工序";
					}
				}
			});
		}
	}
	
	//用来检测输入的零件唯一的id是否已存在 ，
	function checkProcessId(){
		var flag = false;
		var processId= document.getElementById("processId");
		if(isEmpty(processId.value)){
			document.getElementById("checkProcessId").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			
			url:"/mes_online/ProcessIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:
	
		    	"&workpieceId="+ $("#selectWorkpieceId").val() +
		    	"&processId="+ $("#processId").val()
			, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkProcessId").innerHTML=" 编码已存在!!!!";
					//var processId= document.getElementById("processId");
					//processIdfocus();
					flag = true;
				}else{
					document.getElementById("checkProcessId").innerHTML=" 编码可用！";
					
				}	
			}
		});
		return flag;
	}
	
	function addWorkpieceProcess(){
		if ( $("#selectWorkpieceId").val() == ""||  $("#processName").val() == ""||  $("#processId").val() == ""||  $("#selectMachId2").val() == "") {
			alert("信息不完整，请输入！");
		} else {
			if(checkProcessId()==true){
				alert("工序唯一编码已存在");
				return;
			}else{
				$.ajax({
					type : "POST",
					url : '/mes_online/AddWorkpieceProcessServlet',
					data :  
							"&processName="+ $("#processName").val() +
							"&workpieceId="+ $("#selectWorkpieceId").val() +
							"&processId="+ $("#processId").val() +
							"&machId="+ $("#selectMachId2").val()
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加工序成功");
							
							location.reload();
						} else {
							alert("添加工序失败");
						}
					}
				});
			}
		}
		
		
	}
	
	
	
	
	
/*	
	var tagcount2 = 0;
	//提交配置零件RFID信息
	function workpiece_rfid() {
		var workpieceRfId = $("#workpieceRfId").val();
		var workpieceName = $("#workpieceName").val();
		var workpieceId = $("#workpieceId").val();
		var apikey = $("#apikey").val();
		if (workpieceRfId.length < 24 || workpieceId == ""
				|| workpieceName == "") {
			alert("请输入完整数据！");
		} else {

			 			alert("工件名称"+workpieceName);
			 alert("工件rfid"+workpieceRfId);
			 alert("工件标志号"+workpieceId); 
			$.ajax({
				type : "POST",
				url : 'WorkpieceConfig',
				data : "workpieceRfId=" + workpieceRfId + "&workpieceName="
						+ workpieceName + "&apikey=" + apikey + "&workpieceId="
						+ workpieceId,//+"&machName="+machName+"&processName="+processName+"&machId="+machId+"&processId="+processId
				success : function(msg) {
					if (msg != 0) {
						alert("配置零件RFID信息成功");
						//								$(".inputtag").append(
						//										"标签ID：" + $("#tagid").val() + "<br>"
						//												+ "标签名称：" + $("#tagname").val()
						//												+ "<br>" + "APIKEY:"
						//												+ $("#apikey").val());

						//实现多次添加
						tagcount2++;
						var addTr = "<tr><td>" + tagcount2 + "</td><td>"
								+ workpieceName + "</td><td>" + workpieceRfId
								+ "</td></tr>";
						$("#tag_table_workpiece tbody").append(addTr);
						getworkpieceName();
					} else {
						alert("配置工件信息失败");
					}

				}
			});
		}

	}

	//提交配置零件工序信息
	var process_mach = 0;
	function workpiece_process() {
		//@jiang 总共6个信息，名字和id
		var workpieceName = $("#workpieceName2").find("option:selected").text();
		var workpieceId = $("#workpieceName2").val();
		var machName = $("#machName_p").find("option:selected").text();
		var machId = $("#machName_p").val();
		var process_Name = $("#processName").find("option:selected").text();
		var processId = $("#processName").val();
		console.log("工序号码为：" + processId);
		console.log("工序名称为：" + process_Name);
		var apikey = $("#apikey").val();
		if (process_Name == "" || workpieceId == "" || machId == "") {
			alert("请输入完整数据！");
		} else {
			$.ajax({
				type : "POST",
				url : 'ProcessConfig',
				data : "&apikey=" + apikey + "&workpieceId=" + workpieceId
						+ "&machName=" + machName + "&processName="
						+ process_Name + "&machId=" + machId + "&processId="
						+ processId + "&workpieceName=" + workpieceName,
				success : function(msg) {
					if (msg != 0) {
						alert("配置工序信息成功");
						//								$(".inputtag").append(
						//										"标签ID：" + $("#tagid").val() + "<br>"
						//												+ "标签名称：" + $("#tagname").val()
						//												+ "<br>" + "APIKEY:"
						//												+ $("#apikey").val());

						//实现多次添加							
						process_mach++;
						var addTr = "<tr><td>" + process_mach + "</td><td>"
								+ workpieceName + "</td><td>" + process_Name
								+ "</td><td>" + machName + "</td></tr>";
						$("#tag_table_process tbody").append(addTr);
					} else {
						alert("配置工件信息失败");
					}

				}
			});
		}

	}

	//异步提交状态信息	
	function workpieceState() {
		var stateName = $("#statename").val();
		if (stateName == "") {
			alert("请输入数据！");
		} else {
			$.ajax({
				type : "POST",
				url : 'RfidStateConfig',
				data : "statename=" + stateName + "&apikey="
						+ $("#apikey").val(),
				success : function(msg) {
					if (msg != 0) {
						alert("状态配置成功");
						$("#statename").val('');
					} else {
						alert("配置失败");
					}
					flag4 = 1;
				}
			});
		}
	}

	//异步提交天线信息
	var devicecount = 0;
	function deviceConfig() {
		var rfidSystem = $("#rfidSystem").val();
		var deviceName = $("#deviceName").val();
		var eventname = $("#eventname").val();
		var eventstate = $("#eventstate").val();
		if (rfidSystem == "" || deviceName == "" || eventname == ""
				|| eventstate == "") {
			alert("请输入完整数据！");
		} else {
			$.ajax({
				type : "POST",
				url : 'Device_config',
				data : "rfidSystem=" + rfidSystem + "&deviceName=" + deviceName
						+ "&eventname=" + eventname + "&eventstate="
						+ eventstate + "&apikey=" + $("#apikey").val(),
				success : function(msg) {
					if (msg != 0) {
						alert("状态配置成功");
						devicecount++;
						var addTr_device = "<tr><td>"
								+ devicecount
								+ "</td><td>"
								+ msg
								+ "</td><td>"
								+ deviceName
								+ "</td><td>"
								+ $("#eventstate").find("option:selected")
										.text() + "</td></tr>";
						$("#device_table tbody").append(addTr_device);

					} else {
						alert("配置失败");
					}

				}
			});
		}
	}

	 提交输入的工序信息 
	var ProcessWriteCount = 0;
	function ProcessWrite() {
		var workpieceName_process = $("#workpieceName_process").val();
		var workpieceName = $("#workpieceName_process").find("option:selected")
				.text();
		//上面两个其实是同一个东西，第一个是value值（数字代码），第二个是选项的内容值
		alert(workpieceName_process + workpieceName);
		var workpieceProcessId = $("#workpieceProcessId").val();
		var workpieceProcessName = $("#workpieceProcessName").val();
		if (workpieceName_process == "" || workpieceProcessId == ""
				|| workpieceProcessName == "") {
			alert("请输入完整工序信息！");
		} else {
			$.ajax({
				type : "POST",
				url : 'ProcessWrite',
				data : "workpieceName_process=" + workpieceName_process
						+ "&workpieceProcessName=" + workpieceProcessName
						+ "&workpieceProcessId=" + workpieceProcessId,
				success : function(msg) {
					if (msg != 0) {
						alert("工序信息输入成功");
						ProcessWriteCount++;
						var addTr_ProcessWrite = "<tr><td>" + ProcessWriteCount
								+ "</td><td>" + workpieceName + "</td><td>"
								+ workpieceProcessId + "</td><td>"
								+ workpieceProcessName + "</td></tr>";
						$("#ProcessWrite_table tbody").append(
								addTr_ProcessWrite);

					} else {
						alert("配置失败");
					}

				}
			});
		}
	}

	//提示未绑定输入信息
	
	function ProcessWriteShow() {
		if (ProcessWriteCount == 0) {
			alert("还未提交工序信息");
		}
	}

	//提示未绑定工件输入信息
	function workpiece_rfidshow() {
		if (tagcount2 == 0) {
			alert("还未提交信息");
		}

	}
	//提示未绑定工件工序输入信息
	function workpiece_processShow() {
		if (process_mach == 0) {
			alert("还未提交信息");
		}

	}

	function deviceConfig_show() {
		if (devicecount == 0) {
			alert("还未提交信息");
		}

	}

	 //异步获得机床名称
	 var flag1=1;
	 function getmachName() {			
	 $.ajax({
	 type : "POST",
	 url : 'GetMachName',
	 success : function(data) {
	 //更新下拉框
	 document.getElementById("machName").innerHTML = "";			
	 var obj = document.getElementById("machName");
	 obj.add(new Option("--请选择机床--", ""));				
	 var result = eval("(" + data + ")"); //js中解析Json格式到result中
	 var json1 = result.rows; // rows是数组
	 for ( var j = 0; j < json1.length; j++) {
	 var json = json1[j];
	 obj.add(new Option(json["machname"]+"---"+json["machid"], json["machid"]),
	 undefined);
	 }
	 flag1 = 0;
	 }
	 });
	 } 

	var flag5 = 1;
	function getmachName_p() {
		$.ajax({
			type : "POST",
			url : 'GetMachName_p',
			success : function(data) {
				//更新下拉框
				document.getElementById("machName_p").innerHTML = "";
				var obj = document.getElementById("machName_p");
				obj.add(new Option("--请选择机床--", ""));
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					obj.add(new Option(json["machname"], json["machid"]),
							undefined);
				}
				flag1 = 0;
			}
		});
	}

	//异步获得工件名称,用于与工序相绑定
	var flag2 = 1;
	function getworkpieceName() {
		$
				.ajax({
					type : "POST",
					url : 'GetWorkpieceName',
					success : function(data) {
						//更新下拉框
						document.getElementById("workpieceName_process").innerHTML = "";
						var obj = document
								.getElementById("workpieceName_process");
						obj.add(new Option("--请选择工件--", ""));
						var result = eval("(" + data + ")"); //js中解析Json格式到result中
						var json1 = result.rows; // rows是数组
						for (var j = 0; j < json1.length; j++) {
							var json = json1[j];
							obj.add(new Option(json["workpiecename"],
									json["workpieceid"]), undefined);
						}
						flag2 = 0;
					}
				});
	}

	//异步获得工件名称2
	function getworkpieceName2() {
		$.ajax({
			type : "POST",
			url : 'GetWorkpieceName2',
			success : function(data) {
				//更新下拉框
				document.getElementById("workpieceName2").innerHTML = "";
				var obj = document.getElementById("workpieceName2");
				obj.add(new Option("--请选择工件--", ""));
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					obj.add(new Option(json["workpiecename"],
							json["workpieceid"]), undefined);
				}
			}
		});
	}

	//异步获得rfid系统的名称

	var flag3 = 1;
	function getrfidSystem() {
		$.ajax({
			type : "POST",
			url : 'GetrfidSystem',
			success : function(data) {
				//更新下拉框
				document.getElementById("rfidSystem").innerHTML = "";
				var obj = document.getElementById("rfidSystem");
				obj.add(new Option("--请选择rfid系统--", ""));
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					obj.add(new Option(json["rfidsysid"] + "---"
							+ json["rfidname"], json["rfidsysid"]), undefined);
				}
				flag3 = 0;
			}
		});
	}

	//异步获得可选用的状态
	var flag4 = 1;
	function geteventstate() {
		$.ajax({
			type : "POST",
			url : 'GetTagState',
			success : function(data) {
				//更新下拉框
				document.getElementById("eventstate").innerHTML = "";
				var obj = document.getElementById("eventstate");
				obj.add(new Option("--可选工件状态--", ""));
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					obj.add(new Option(json["statename"], json["stateid"]),
							undefined);
				}
				flag4 = 0;
			}
		});
	}

	//异步获得指定零件的工序名称
	var flag6 = 1;
	function getprocessName() {
		var workpieceId = $("#workpieceName2").val();

		if (workpieceId == "") {
			alert("请先选择工件");
		} else {
			$.ajax({
				type : "POST",
				url : 'GetProcessName',
				data : "workpieceId=" + workpieceId,
				success : function(data) {
					//更新下拉框
					console.log("test");
					document.getElementById("processName").innerHTML = "";
					var obj = document.getElementById("processName");
					obj.add(new Option("--可选工序为--", ""));
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						obj.add(new Option(json["processname"],
								json["processid"]), undefined);
					}
					flag6 = 0;
				}
			});
		}

	}

	//这个函数在数据库管理中用来删除信息
	function deleteInfo(deleteid) {
		if (confirm("确定删除此零件？")) {
			var Id = deleteid;
			//alert(Id);
			$.ajax({
				type : "POST",
				url : 'DeleteInfo',
				data : "deleteid=" + Id,
				success : function(data) {
					//更新下拉框
					alert("删除信息成功");
					location.reload();
				}
			});

		}

	}
	//这个是获取工序信息的函数
	function showInfo(showid) {
		var ids = showid.split(",");
		var Id = ids[0];
		var name = ids[1];
		//alert(Id);
		var ProcessShowCount = 0;
		$("#workpieceShowN").html(name);
		$("#ProcessTable").empty();
		$.ajax({
			type : "POST",
			url : 'ShowWorkpiece',
			data : "showid=" + Id,
			success : function(data) {
				//更新下拉框
				document.getElementById("processName").innerHTML = "";
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					ProcessShowCount++;
					var addTr_ProcessWrite = "<tr><td>" + ProcessShowCount
							+ "</td><td>" + json["processid"] + "</td><td>"
							+ json["processname"] + "</td></tr>";
					$("#table_processshow tbody").append(addTr_ProcessWrite);

				}
			}
		});

	}

	//这里只让点击一次传递数据，防止多次点击，每次都要传递数据
	function getrfid() {
		if (flag3) {
			getrfidSystem();
			flag3 = 0;
		}
	}

	function getevent() {
		if (flag4) {
			geteventstate();
			flag4 = 0;
		}
	}
	 			function getmach() {
	 if (flag1) {
	 getmachName();
	 flag1 = 0;
	 }
	 } 

	function getworkpiece() {
		flag6 = 1;
		if (flag2) {
			getworkpieceName();
			flag2 = 0;
		}
	}

	function getmach_p() {
		if (flag5) {
			getmachName_p();
			flag5 = 0;
		}
	}

	function getprocess() {
		if (flag6) {
			getprocessName();
			flag6 = 0;
		}

	}

	//这是对数据库进行清空的方法；
	function clearDatebase() {

		$.ajax({
			type : "POST",
			url : 'ClearDateBase',
			success : function(data) {
				alert("删除成功");

			}
		});

	}
	//测试系统的默认输入;
	function Default1() {
		$("#mach_Name").val("YX-龙门铣床");
		$("#mach_Id").val("100256");
		$("#rfidName").val("ALIEN—9980S");
	}
	function Default2() {
		$("#workpieceName").val("锥齿轮");
		$("#workpieceId").val("56128");
		$("#workpieceRfId").val("E200 9002 5310 0094 1700 66AB");
	}
	function Default3() {
		$("#workpieceProcessName").val("铣齿");
		$("#workpieceProcessId").val("56128-4");
		}
*/	