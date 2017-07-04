function editUser(userId,password,userName){
		document.getElementById("userId").value=userId;
		document.getElementById("password").value=password;
		document.getElementById("userName").value=userName;
		$('#editUserModal').modal('show');
		
	}
function deleteUser(userId){
	
	$.ajax({
		type : "POST",
		url : '/rfid_monitor/DeleteUserServlet',
		data :  
				"&userId="+userId
				,
		async : true,
		success : function(msg) {
			if (msg =="success") {
				
				alert("删除用户成功");
				//刷新当前页面
				window.self.location ="admin/admin4.jsp"
				//location.href=location.href;
				
			} else {
				alert("删除失败");
			}
		}
	});
	
}

function submitEditUser(){
	if ( $("#userId").val() == ""||  $("#password").val() == ""||  $("#userName").val() == "") {
		alert("不能为空！");
	} else {
			$.ajax({
				type : "POST",
				url : '/rfid_monitor/EditUserServlet',
				data :  
						"&userId="+ $("#userId").val() +
						"&password="+ $("#password").val() +
						"&userName="+ $("#userName").val() 
						,
				async : true,
				success : function(msg) {
					if (msg =="success") {
						
						alert("修改成功");
						//刷新当前页面
						location.href=location.href
					} else {
						alert("修改失败");
					}
				}
			});
		
		
		
	}
	
}