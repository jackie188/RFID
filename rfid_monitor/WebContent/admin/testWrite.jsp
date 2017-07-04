<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="../assets/js/jquery-2.0.3.min.js"></script>
<script language="javascript" type="text/javascript">
	function addMach() {
		console.log($("#workpieceId").val());
		//return ;
		$.ajax({
			type : "POST",
			url : '/rfid_monitor/BandingRFIDServlet',
			data :  
					"&workpieceId="+ $("#workpieceId").val() 
					,
			async : true,
			success : function(msg) {
				if (msg =="ok") {
					//msg表示传递过来几个数据																	
					//在表格中显示	
					//配置成功后，这会扩充点击已配置信息的table表
					alert("添加机床成功");
				} else {
					alert("添加机床失败");
				}
			}
		});
		
	}

	</script>
<body>
	<div class="form-group  col-md-8 col-xs-12">
	<input
		name="workpieceId" id="workpieceId" 
		placeholder="唯一标识号，格式:m+数字(非0)，例：m1">
		<button onclick="addMach()" type="button" >添加</button>
</div>
</body>


	<!--[if !IE]> -->




	
</html>