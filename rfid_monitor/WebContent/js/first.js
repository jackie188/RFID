
function update(){
			updateRfidList();//更新rfid下拉框
			updateMachList();//更新机床下拉框
			updateMachList2();//更新机床下拉框
			updateWorkpieceList();//更新零件下拉框
			updateWorkpieceTable2();
			document.getElementById('processName').disabled = true;
			document.getElementById('processId').disabled = true;
			
}
function addMach(){
if ( isEmpty($("#machId").val())||isEmpty($("#machName").val())) {
	alert("不能为空！");
	return;
} else {
	var rs=/^m[1-9][0-9]*$/;
	if(!rs.test($("#machId").val())){
		alert("请输入标准格式");
		return ;
	}
	
	if(checkMachId()==true){
		alert("唯一编码已存在");
		return;
	}else{
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/AddMachServlet',
			data :  
					"&machId="+ TrimAll($("#machId").val()) +
					"&machName="+ TrimAll($("#machName").val())
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("添加机床成功");
					updateMachList();
					updateMachList2();
					$("#machId").val("");
					$("#machName").val("");
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
		url : '/rfid_monitor/DeleteMachServlet',
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
				updateMachList();
				updateMachList2();
				updateMachTable();
				
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
	var rs=/^m[1-9][0-9]*$/;
	if(!rs.test($("#machId").val())){
		document.getElementById("checkMachId").innerHTML=" 请输入标准格式！";
		return ;
	}

	$.ajax({ 
		type:"post", 
		//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
		//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
		
		//当加上/=http://localhost:8080/
		//注解的方式
		url:"/rfid_monitor/MachIdValidateServlet",
		
		//xml配置方式
		//url:"../servlet/ClientIdValidateServlet",
	    data:{machId:TrimAll(machId.value)}, 
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
function updateMachList() {
	$.ajax({
		type : "POST",
		url : '/rfid_monitor/UpdateMachListServlet',
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

/* 更新下拉框，机床的 ，绑定工序*/
function updateMachList2() {
	$.ajax({
		type : "POST",
		url : '/rfid_monitor/UpdateMachList2Servlet',
		async : true,
		success : function(data) {
			
			//更新下拉框
			document.getElementById("selectMachId2").innerHTML = "";
			var obj = document.getElementById("selectMachId2");
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
	if ( isEmpty($("#machIdForEdit").val())|| isEmpty( $("#machNameForEdit").val())) {
		alert("不能为空！");
		return ;
	} else {
			$.ajax({
				type : "POST",
				url : '/rfid_monitor/EditMachServlet',
				data :  
						"&machId="+ TrimAll($("#machIdForEdit").val()) +
						"&machName="+ TrimAll($("#machNameForEdit").val()) 
						,
				async : true,
				success : function(msg) {
					if (msg =="success") {
						//msg表示传递过来几个数据																	
						//在表格中显示	
						//配置成功后，这会扩充点击已配置信息的table表
						alert("修改成功");
						updateMachList();
						updateMachList2();
						updateMachTable();
						$('#editMachModal').modal('hide');
					} else {
						alert("修改失败");
					}
				}
			});
		
		
		
	}
	
}
//用来查看机床，通过ajax实时改变表格内容，不知道为什么出问题，还是模态吧，每次添加新的东西重新刷新界面
function viewMach(){
	updateMachTable();
	$('#machModal').modal('show');
}
function updateMachTable(){
	$.ajax({
		type : "POST",
		url : '/rfid_monitor/UptateMachTableServlet',
	
		async : false,
		success : function(data) {
			if(data=="0"){
				
			}else{
				document.getElementById("machTable").innerHTML="";
				var show = "";
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					var other =" <a class=\"btn btn-sm btn-danger\" onclick=\"deleteMach('"+json["machId"]+"')\" > 删除</a>"+
								"<a class=\"btn btn-sm btn-info\"  onclick=\"editMach('"+json["machId"]+"','"+json["machName"] +"')\"> 修改</a>";
					var addTr = "<tr><td>" + json["machId"] + "</td><td>"
					+ json["machName"]+ "</td><td>" + other
					+ "</td></tr>";
					show =show +addTr;
					
				}
				//alert(show);
				document.getElementById("machTable").innerHTML=show;
				//第二步调到自动生成table的函数
				$('#sample-table-list1').dataTable(); 
				//第三步生成新的导出按钮（更新数据）
				$("#sample-table-list1").tableExport({
					formats:["xlsx"],
					fileName: "机床表", 
					ignoreCols: 2,
					position: "bottom" 
					
				});
				
			}
		
			
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
	var rs=/^s[1-9][0-9]*$/;
	if(!rs.test($("#rfidId").val())){
		document.getElementById("checkRfidId").innerHTML=" 请输入标准格式！";
		return ;
	}

	$.ajax({ 
		type:"post", 
		//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
		//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
		
		//当加上/=http://localhost:8080/
		//注解的方式
		url:"/rfid_monitor/RfidIdValidateServlet",
		
		//xml配置方式
		//url:"../servlet/ClientIdValidateServlet",
	    data:{rfidId:TrimAll(rfidId.value)}, 
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
	if ( isEmpty($("#rfidId").val())||isEmpty($("#rfidName").val())||  isEmpty($("#rfidIp").val())) {
		alert("不能为空！");
		return ;
	} else {
		var rs=/^s[1-9][0-9]*$/;
		if(!rs.test($("#rfidId").val())){
			alert("请输入标准格式");
			return ;
		}
		if(checkRfidId()==true){
			alert("唯一编码已存在");
			return;
		}else{
			$.ajax({
				type : "POST",
				url : '/rfid_monitor/AddRfidSystemServlet',
				data :  
						"&rfidId="+ TrimAll($("#rfidId").val()) +
						"&rfidName="+ TrimAll($("#rfidName").val()) +
						"&rfidIp="+ TrimAll($("#rfidIp").val())
						,
				async : true,
				success : function(msg) {
					if (msg =="success") {
						//msg表示传递过来几个数据																	
						//在表格中显示	
						//配置成功后，这会扩充点击已配置信息的table表
						alert("添加rfid成功");
						updateRfidList();
						$("#rfidId").val("");
						$("#rfidName").val("");
						$("#rfidIp").val("");
					} else {
						alert("添加rfid失败");
					}
				}
			});
		}
	}
	
}
function viewRfid(){
	updateRfidTable();
	$('#rfidModal').modal('show');
}
function updateRfidTable(){
	$.ajax({
		type : "POST",
		url : '/rfid_monitor/UpdateRfidTableServlet',
	
		async : false,
		success : function(data) {
			
			if(data=="0"){
				document.getElementById("rfidTable").innerHTML="";
			}else{
				document.getElementById("rfidTable").innerHTML="";
				var show = "";
				var result = eval("(" + data + ")"); //js中解析Json格式到result中
				var json1 = result.rows; // rows是数组
				for (var j = 0; j < json1.length; j++) {
					var json = json1[j];
					var other =" <a class=\"btn btn-sm btn-danger\" onclick=\"deleteRfidSystem('"+json["rfidId"]+"')\" > 删除</a>"+
								"<a class=\"btn btn-sm btn-info\"  onclick=\"editRfid('"+json["rfidId"]+"','"+json["rfidName"] +"','"+json["rfidIp"]+"')\"> 修改</a>";
					var addTr = "<tr><td>" + json["rfidId"] + "</td><td>"
					+ json["rfidName"]+ "</td><td>" 
					+ json["rfidIp"]+ "</td><td>" +other
					+ "</td></tr>";
					
					show =show +addTr;
					
				}
				//alert(show);
				document.getElementById("rfidTable").innerHTML=show;
				//第二步调到自动生成table的函数
				$('#sample-table-list2').dataTable(); 
				//第三步生成新的导出按钮（更新数据）
				$("#sample-table-list2").tableExport({
					formats:["xlsx"],
					fileName: "rfid设备表", 
					ignoreCols: 3,
					position: "bottom" 
					
				});
				
			}
		
			
		}
	});
}
function editRfid(rfidId,rfidName,rfidIp){
	document.getElementById("rfidIdForEdit").value=rfidId;
	document.getElementById("rfidNameForEdit").value=rfidName;
	document.getElementById("rfidIpForEdit").value=rfidIp;
	$('#editRfidModal').modal('show');
	
}
	function submitEditRfid(){
		if ( isEmpty($("#rfidIdForEdit").val())||  isEmpty($("#rfidNameForEdit").val())||isEmpty($("#rfidIpForEdit").val())) {
			alert("不能为空！");
		} else {
				$.ajax({
					type : "POST",
					url : '/rfid_monitor/EditRfidSystemServlet',
					data :  
							"&rfidId="+ TrimAll($("#rfidIdForEdit").val() )+
							"&rfidName="+ TrimAll($("#rfidNameForEdit").val()) +
							"&rfidIp="+ TrimAll($("#rfidIpForEdit").val() )
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("修改成功");
							//刷新当前页面
							updateRfidList();
							updateRfidTable();
							$('#editRfidModal').modal('hide');
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
			url : '/rfid_monitor/DeleteRfidSystemServlet',
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
					//刷新当前页面
					updateRfidList();//更新机床下拉框
					updateRfidTable();
					
				} else {
					alert("删除rfid系统失败");
				}
			}
		});
		
	}
	
	/* 更新下拉框，rfid系统的 ,主要是剔除已经绑定的rfid系统和更新新加入的系统，为绑定部分服务*/
	function updateRfidList() {
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateRfidListServlet',
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectRfidSysId").innerHTML = "";
				var obj = document.getElementById("selectRfidSysId");
				if(data == "0"){
					obj.add(new Option("--无可用rfid设备--", ""));
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
	
function addMachRfid() {
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
			url : '/rfid_monitor/MachRfidbBangdingServlet',
			data :  
					"&machId="+ TrimAll($("#selectMachId").val()) +
					"&rfidSysId="+ TrimAll($("#selectRfidSysId").val()) 
					,
			async : true,
			success : function(msg) {
				if (msg =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("配置rfid系统成功");
					updateRfidList();
					updateMachList();
					
				} else {
					alert("配置rfid系统失败");
				}
			}
		});
	}
}
	function viewMachRfid(){
		updateMachRfidTable();
		$('#machRifdModal').modal('show');
		
	}
	function updateMachRfidTable(){
		//第一步：先更新table的数据
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateMachRfidTableServlet',
		
			async : false,
			success : function(data) {
				document.getElementById("machRfidTable").innerHTML="";
				if(data=="0"){
					return;
				}else{
					var show = "";
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						var other =" <a class='btn btn-sm btn-danger' onclick=\"isDeleteMachRfid('"+json["machId"]+"','"+json["rfidId"]+"')\"> 删除</a>";
						var addTr = "<tr><td>" + json["machId"] + "</td><td>"
						+ json["machName"]+ "</td><td>" 
						
						+ json["rfidId"]+ "</td><td>" 
						+ json["rfidName"]+ "</td><td>" 
						+ other
						+ "</td></tr>";
						show =show +addTr;
						
					}
					//alert(show);
					document.getElementById("machRfidTable").innerHTML=show;
					//第二步调到自动生成table的函数
					$('#sample-table-list3').dataTable(); 
					//第三步生成新的导出按钮（更新数据）
					$("#sample-table-list3").tableExport({
						formats:["xlsx"],
						fileName: "机床与RFID设备绑定表", 
						ignoreCols: 4,
						position: "bottom" 
						
					});
				}
				
			}
		});
	}
	function isDeleteMachRfid(machId,rfidId){
		var con;
		con =confirm("确定要解除该绑定关系?")
		if(con == true){
			deleteMachRfid(machId,rfidId);
		}
		return;
	}
	function deleteMachRfid(machId,rfidId){
		console.log(rfidId+machId);
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/DeleteMachRfidServlet',
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
					//删除成功后刷新与之相关的东西
					viewMachRfid();
					updateRfidList();//更新机床下拉框
					updateMachList();//更新系统下拉框
					
				} else {
					alert("删除失败，因为"+msg);
				}
			}
		});
		
	}
	
	//用来检测输入的零件唯一的id是否已存在 ，
	function checkWorkpieceId(){
		var flag = false;
		var workpieceIdTail= document.getElementById("workpieceIdTail");
		if(isEmpty(workpieceIdTail.value)){
			document.getElementById("checkWorkpieceId").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
		//var rs=/^w[1-9][0-9]*$/;
		L= TrimAll(workpieceIdTail.value).length;
		if(L%2==0){
			document.getElementById("checkWorkpieceId").innerHTML=" 请输入标准格式！长度为奇数";
			return ;
		}

		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/rfid_monitor/WorkpieceIdIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{
		    	 workpieceId:TrimAll($("#select_c").val()+
		    			 			 $("#select_l").val()+
		    			 			 $("#select_s").val()+
		    			 			 $("#select_g").val()+
		    			 			 workpieceIdTail.value)}, 
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
	function checkWorkpieceRfid(){
		var flag = false;
		var workpieceRfid= document.getElementById("workpieceRfid");
		if(isEmpty(workpieceRfid.value)){
			document.getElementById("checkWorkpieceRfid").innerHTML=" 代码不能为空！";
			//mach_Id.focus();
			return ;
		}
	
		$.ajax({ 
			type:"post", 
			//注意这里直接写ClientIdValidateServlet，默认的url为当前路径加上此字符，使用的是相对路径
			//http://localhost:8080/DRP1.8/basedata/ClientIdValidateServlet
			
			//当加上/=http://localhost:8080/
			//注解的方式
			url:"/rfid_monitor/WorkpieceRfidIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:{workpieceRfid:TrimAll(workpieceRfid.value)}, 
			async : false,// 设置为同步操作就可以给全局变量赋值成功
			success:function(data){
				//alert(data);
				if(data=="true"){
					document.getElementById("checkWorkpieceRfid").innerHTML=" 编码已存在!!!!";
					var WorkpieceRfid = document.getElementById("WorkpieceRfid");
					//WorkpieceId.focus();
					flag = true;
				}else{
					document.getElementById("checkWorkpieceRfid").innerHTML=" 编码可用！";
					
				}	
			}
		});
		return flag;
	}
	//添加零件
	function addWorkpiece(){
		if ( isEmpty($("#workpieceName").val())||isEmpty($("#workpieceIdTail").val())) {
			alert("用户或串号不能为空！");
			return ;
		} else {
			L= TrimAll($("#workpieceIdTail").val()).length;
			if(L%2==0){
				document.getElementById("checkWorkpieceId").innerHTML=" 请输入标准格式！长度为奇数";
				return ;
			}
			else{
				$.ajax({
					type : "POST",
					url : '/rfid_monitor/AddWorkpieceServlet',
					data :  { workpieceName:$("#workpieceName").val(),
							  workpieceId:TrimAll(  $("#select_c").val()+
													$("#select_l").val()+
													$("#select_s").val()+
													$("#select_g").val()+
													$("#workpieceIdTail").val()
													)
								
							},
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加零件成功");
							updateWorkpieceList();//更新零件下拉框
							updateWorkpieceTable2();//更新零件table
							$("#workpieceName").val("");
							$("#workpieceIdTail").val("");
						
						} else {
							alert("添加零件失败");
						}
					}
				});
			}
		}
		
	}
	function viewWorkpiece(){
		updateWorkpieceTable();
		$('#workpieceModal').modal('show');
		
	}
	function updateWorkpieceTable(){
		//第一步：先更新table的数据
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateWorkpieceTableServlet',
		
			async : false,
			success : function(data) {
				document.getElementById("workpieceTable").innerHTML="";
				if(data=="0"){
					return;
				}else{
					var show = "";
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						var addTr = "<tr><td>" + json["workpieceId"] + "</td><td>"
						+ json["workpieceName"]+ "</td><td>" 
						+ json["isBanding"]+ "</td><td>" 
						+ json["time"]+ "</td><td>" 
						+json["processState"] + "</td>" 
						+ "</tr>";
						show =show +addTr;
						
					}
					//alert(show);
					document.getElementById("workpieceTable").innerHTML=show;
					//第二步调到自动生成table的函数
					$('#sample-table-list4').dataTable(); 
					//第三步生成新的导出按钮（更新数据）

					$("#sample-table-list4").tableExport({
						formats:["xlsx"],
						fileName: "零件信息表", 
						ignoreCols: null,
						position: "bottom" 
						
					});
				}
				
			}
		});
	}
	function updateWorkpieceTable2(){
		//第一步：先更新table的数据
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/UpdateWorkpieceTableServlet',
		
			async : false,
			success : function(data) {
				document.getElementById("workpieceTable2").innerHTML="";
				if(data=="0"){
					return;
				}else{
					var show = "";
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						var other ="&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a class='btn btn-sm btn-danger' onclick=\"isDeleteWorkpiece('"+json["workpieceId"] +"')\"> 删除</a>"+
						"<a class='btn btn-sm btn-info'  onclick=\"showWorkpieceDetial('"+json["workpieceId"] +"')\">工序信息</a>"+
						"<a class='btn btn-sm btn-success'  onclick=\"showBanding('"+json["workpieceRfid"] +"')\">绑定标签</a>";
						var addTr = "<tr><td>" + json["workpieceId"] + "</td><td>"
						+ json["workpieceName"]+ "</td><td>" 
						+ json["isBanding"]+ "</td><td>" 
						+ json["time"]+ "</td><td>" 
						+other
						+ "</td></tr>";
						show =show +addTr;
						
					}
					//alert(show);
					document.getElementById("workpieceTable2").innerHTML=show;
					//第二步调到自动生成table的函数
					$('#sample-table-list5').dataTable(); 
				}
				
			}
		});
	}
	function isDeleteWorkpiece(workpieceId){
		var con;
		con =confirm("确定要删除该零件?")
		if(con == true){
			deleteWorkpiece(workpieceId);
		}
		return;
	}
	
	function deleteWorkpiece(workpieceId){
		
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/DeleteWorkpieceServlet',
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
					//刷新与零件相关的表
					updateWorkpieceList();//更新零件下拉框
					updateWorkpieceTable2();//更新零件table
				} else {
					alert("删除失败");
				}
			}
		});
		
	}
	
	
	function showWorkpieceDetial(workpieceId){
		var flag =false;
		var tableName='';
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/ShowWorkpieceDetialServlet',
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
					var flag2="";//查看零件的flag字段，0未确定，1已确定
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						flag2 = json["flag"];
						var title = "零件："+json["workpieceName"]+"————————编号："+json["workpieceId"];
						tableName="零件："+json["workpieceName"]+"—编号："+json["workpieceId"]+"（工序信息）";
						var addTr = "<tr>" +
								"<td>" + (j+1) + "</td>" +
								"<td>"+ json["processName"]+ "</td>" +
								"<td>" + json["processId"]+ "</td>" +
								"<td>" + json["machName"]+ "</td>" +
								/*"<td>" + json["state"]+ "</td>" +*/
								"</tr>";
						show =show +addTr;
						
					}
					if(flag2 =="0"){
						
						//document.getElementById("workpieceFlag").innerHTML='零件工序配置尚未完成';
						document.getElementById("workpieceFlag").innerHTML='<li>'+
						'零件工序配置尚未完成'+'<i class="icon-warning-sign"></i>'+
						'</li>';
						document.getElementById("confirmWorkpiece").innerHTML='';
						$("#confirmWorkpiece").append ('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
						//$("#confirmWorkpiece").append("<button type='button' class='btn btn-primary' onclick=\"IsConfirmWorkpiece('"+workpieceId+"')\">确定所有工序</button>");
						
					}else{
						//document.getElementById("workpieceFlag").innerHTML='零件工序配置已完成';
						document.getElementById("workpieceFlag").innerHTML='<li>'+
						'零件工序配置已完成'+'<i class="icon-ok bigger-110 green"></i>'+
					'</li>';
						document.getElementById("confirmWorkpiece").innerHTML='';
						$("#confirmWorkpiece").append ('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
						//$("#confirmWorkpiece").append("<button type='button' disabled='disabled' class='btn btn-primary' onclick=\"IsConfirmWorkpiece('"+workpieceId+"')\">确定所有工序</button>");
					}
					document.getElementById("processList").innerHTML=show;	
					document.getElementById("workpieceDetialTitle").innerHTML=title;	
					flag=true;
				}
			}
		});
		if(flag){
			
			$('#workpieceDetialModal').modal('show');
			
			//导出数据，要等数据生成后最好。此方式有问题会不断给table添加导出按钮（已经修复，更改插件内部代码实现）
			$("#sample-table-list6").tableExport({
				formats:["xlsx"],
				fileName: tableName, 
				ignoreCols: null,
				position: "bottom" 
				
			});
		}else{
			alert("当前没有工序录入到该零件");
		}
		
		
		
	}
	
	function showBanding(workpieceRfid){
		var flag =false;
		var tableName='';
		$('#bandingModal').modal({
			backdrop:false,
			keyboard: false
			})
		$('#bandingModal').modal('show');
		banding(workpieceRfid,15);			
	}
	
	function banding(workpieceRfid,count){
		document.getElementById("isBanding").innerHTML='';
		var flag=false;
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/BandingRFIDServlet',
			data :  
					"&workpieceRfid="+workpieceRfid
					,
			async : false,
			success : function(data) {
				if (data =="ok") {
					flag=true;
				} else {
					flag= false;
				}
			}
		});
		if(flag==false&&count!=0){
			count--;
			document.getElementById("time").innerHTML="剩余时间："+count+"s";
			t=setTimeout("banding('"+workpieceRfid+"',"+count+")",1000);//设置每秒运行一次函数	
			console.log("一秒一次")
		}else{
			if(flag){
				document.getElementById("time").innerHTML="绑定成功";	
				document.getElementById("isBanding").innerHTML='';
				$("#isBanding").append ('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
				$("#isBanding").append("<button type='button' disabled='disabled'  class='btn btn-primary' onclick=\"banding('"+workpieceRfid+"','15')\">重试</button>");
				updateWorkpieceTable2();
			}else{
				document.getElementById("time").innerHTML="绑定失败！";	
				document.getElementById("isBanding").innerHTML='';
				$("#isBanding").append ('<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>');
				$("#isBanding").append("<button type='button' class='btn btn-primary' onclick=\"banding('"+workpieceRfid+"','15')\">重试</button>");
			}
		}
		
	}
	function getTagInfo(){
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/GetTagInfoServlet',
			async : false,
			success : function(data) {
				if(data!="no"){
					var show="";
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						show+="标签的id："+json["RfidId"]+"<br>";
					}
					document.getElementById("tagInformation").innerHTML=show;	
				}else{
					document.getElementById("tagInformation").innerHTML="无标签";		
				}
				
			}
		});
		
		
		
		
		
	}
	function IsConfirmWorkpiece(workpieceId){
		var con;
		con =confirm("确定工序后则无法再进行添加，确定?")
		if(con == true){
			confirmWorkpiece(workpieceId);
		}
		return;
	}
	function confirmWorkpiece(workpieceId){
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/ConfirmWorkpieceServlet',
			async : true,
			data :  
				"&workpieceId="+workpieceId
				,
			success : function(data) {
				if (data =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("零件确定成功");
					updateWorkpieceList();//更新零件下拉框
					$('#workpieceDetialModal').modal('hide');
				} else {
					alert("失败");
				}
				
			}
		});
	}
	/* 更新下拉框，零件的 */
	function updateWorkpieceList() {
		$.ajax({
			type : "POST",
			//old method
			//url : '/rfid_monitor/UpdateWorkpieceListServlet',
			url : '/rfid_monitor/UpdateWorkpieceListServlet2',
			async : true,
			success : function(data) {
				
				//更新下拉框
				document.getElementById("selectWorkpieceId").innerHTML = "";
				var obj = document.getElementById("selectWorkpieceId");
				if(data == "0"){
					obj.add(new Option("--无可用零件--", ""));
					//设置提交按钮失效
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					var addWorkpieceProcess= document.getElementById("addWorkpieceProcess");
					//workpieceProcessSubmit.disabled=true; 
					addWorkpieceProcess.disabled=true; 
					
				}else{
					obj.add(new Option("--零件列表--", ""));
					//设置提交按钮回复正常
					//var workpieceProcessSubmit= document.getElementById("workpieceProcessSubmit");
					//workpieceProcessSubmit.disabled=false; 
					
					var addWorkpieceProcess= document.getElementById("addWorkpieceProcess");
					addWorkpieceProcess.disabled=false; 
					var result = eval("(" + data + ")"); //js中解析Json格式到result中
					var json1 = result.rows; // rows是数组
					for (var j = 0; j < json1.length; j++) {
						var json = json1[j];
						if(json["flag"]=="0"){
							//old method
							//obj.add(new Option(json["workpieceName"]+"【唯一标识号："+json["workpieceId"]+"】", json["workpieceId"]),undefined);
							obj.add(new Option(json["workpieceName"], json["workpieceName"]),undefined);
						}
						
					}
					
				}
				
			}
		});
	}
	
	//当选择的零件发生变化时，实时更新提示工序消息
	function getProcessCountByWorkpieceId(){
		if ( $("#selectWorkpieceId").val() == "") {
			document.getElementById("processConutOfWorkpiece").innerHTML = "选择零件并按工序优先级录入工序";
			document.getElementById('processName').disabled = true;
			document.getElementById('processId').disabled = true;
		} else {
					
			$.ajax({
				type : "POST",
				//原方法
				//url : '/rfid_monitor/GetProcessCountByWorkpieceIdServlet',
				url : '/rfid_monitor/GetProcessCountByWorkpieceNameServlet',
				data :  
						//原方法传递id，给当个零件录入工序
						//"&selectWorkpieceId="+ $("#selectWorkpieceId").val() 
					//	新方法传递零件名，给一类零件录入工序
						"&selectWorkpieceName="+ $("#selectWorkpieceId").val() 
						,
				async : true,
				success : function(msg) {
					document.getElementById("processConutOfWorkpiece").innerHTML = "";
					if(msg =="fail"){
						document.getElementById('processName').disabled = true;
						document.getElementById('processId').disabled = true;
						document.getElementById("processConutOfWorkpiece").innerHTML = "零件工序已配置完毕，不可再添加！！";
					}else{
						document.getElementById('processName').disabled = false;
						document.getElementById('processId').disabled = false;
						if (msg =="0") {
							document.getElementById("processConutOfWorkpiece").innerHTML = "当前零件还未配置工序，请输入第一道工序";
						} else {
							document.getElementById("processConutOfWorkpiece").innerHTML = "当前零件已配置第"+msg+"道工序，请输入下一道工序";
						}
					}
					
				}
			});
		}
	}
	//当给零件配置工序时，机床和工序是一对一关系，同一个机床不能被选中两次
	function getAvailableMachine4workpieceProcess(){
		if ( $("#selectWorkpieceId").val() == "") {
			return ;
		} else {
					
			$.ajax({
				type : "POST",
				//原方法
				//url : '/rfid_monitor/GetProcessCountByWorkpieceIdServlet',
				url : '/rfid_monitor/GetAvailableMachine4workpieceProcessServlet',
				data :  
						//原方法传递id，给当个零件录入工序
						//"&selectWorkpieceId="+ $("#selectWorkpieceId").val() 
					//	新方法传递零件名，给一类零件录入工序
						"&selectWorkpieceName="+ $("#selectWorkpieceId").val() 
						,
				async : true,
				success : function(data) {
					//更新下拉框
					document.getElementById("selectMachId2").innerHTML = "";
					var obj = document.getElementById("selectMachId2");
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
		var rs=/^p[1-9][0-9]*$/;
		if(!rs.test($("#processId").val())){
			document.getElementById("checkProcessId").innerHTML=" 请输入标准格式！";
			return ;
		}

		$.ajax({ 
			type:"post", 
			
			url:"/rfid_monitor/ProcessIdValidateServlet",
			
			//xml配置方式
			//url:"../servlet/ClientIdValidateServlet",
		    data:
	
		    	"&workpieceId="+ TrimAll($("#selectWorkpieceId").val()) +
		    	"&processId="+ TrimAll($("#processId").val())
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
		if ( isEmpty($("#selectWorkpieceId").val())||  isEmpty($("#processName").val())|| isEmpty( $("#processId").val())||isEmpty($("#selectMachId2").val())) {
			alert("信息不完整，请输入！");
			return ;
		} else {
			var rs=/^p[1-9][0-9]*$/;
			if(!rs.test($("#processId").val())){
				alert("请输入标准格式");
				return ;
			}
			if(checkProcessId()==true){
				alert("工序唯一编码已存在");
				return;
			}else{
				$.ajax({
					type : "POST",
					url : '/rfid_monitor/AddWorkpieceProcessServlet',
					data :  
							"&processName="+ TrimAll($("#processName").val()) +
							"&workpieceId="+ TrimAll( $("#selectWorkpieceId").val()) +
							"&processId="+ TrimAll($("#processId").val()) +
							"&machId="+ TrimAll($("#selectMachId2").val())
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加工序成功");
							getProcessCountByWorkpieceId();
							$("#processId").val("");
							$("#processName").val("");
						} else {
							alert("添加工序失败");
						}
					}
				});
			}
		}
		
		
	}
	//零件工序id是自动生成的，不需要再用户输入了
	function addWorkpieceProcess2(){
		if ( isEmpty($("#selectWorkpieceId").val())||  isEmpty($("#processName").val())||isEmpty($("#selectMachId2").val())) {
			alert("信息不完整，请输入！");
			return ;
		} else {
			
				$.ajax({
					type : "POST",
					url : '/rfid_monitor/AddWorkpieceProcessServlet2',
					data :  
							"&processName="+ TrimAll($("#processName").val()) +
							"&workpieceName="+ TrimAll( $("#selectWorkpieceId").val()) +
							"&processId=none"+
							"&machId="+ TrimAll($("#selectMachId2").val())
							,
					async : true,
					success : function(msg) {
						if (msg =="success") {
							//msg表示传递过来几个数据																	
							//在表格中显示	
							//配置成功后，这会扩充点击已配置信息的table表
							alert("添加工序成功");
							getProcessCountByWorkpieceId();
							getAvailableMachine4workpieceProcess();
							$("#processId").val("");
							$("#processName").val("");
						} else {
							alert("添加工序失败");
						}
					}
				});
			
		}
		
		
	}
	function isworkpieceProcessFinished(workpieceName){
		var con;
		con =confirm("确定后将无法再对该类零件进行工序配置，确定?")
		if(con == true){
			workpieceProcessFinished(workpieceName);
		}
		return;
	}
	function workpieceProcessFinished(workpieceName){
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/ConfirmWorkpieceServlet2',
			async : true,
			data :  
				"&workpieceName="+workpieceName
				,
			success : function(data) {
				if (data =="success") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("零件确定成功");
					updateWorkpieceList();//更新零件下拉框
					$('#workpieceDetialModal').modal('hide');
				} else {
					alert("失败");
				}
				
			}
		});
	}
	