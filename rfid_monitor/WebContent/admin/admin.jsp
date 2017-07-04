<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="xjtu.mes.util.*,xjtu.mes.model.*,xjtu.mes.manager.*"%>
<%@page import="java.util.*"%>
<%@page import="xjtu.mes.exception.*"%>
<%
User user =null;
user = (User)session.getAttribute("user_info");
if(null == user){
	response.sendRedirect("/rfid_monitor/errorPage.jsp");
	return ;
}
	List<Machine> machines = new ArrayList<Machine>();
	machines = MachManager.getInstance().getMachines();
	
	

%>

<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>RFID</title>
		<!-- 这里设置使用绝对路径 -->
		<%@ include file="../common/meta.jsp" %> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

		<!-- ace styles -->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->



		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
				 <style type="text/css">
    /* 针对性的修改css */
    
    .page-header {
    
    border-bottom: 1px dotted #d15b47;
   
		}
		#imgRFID {
    width: 100%;
    height: 100%;
}
</style>
	</head>

	<body onload="update()">
		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-cog"></i>
							RFID工序监控
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<!-- <li class="grey">
							<a data-toggle="" class="dropdown-toggle" href="1.jsp">
							<a data-toggle="dropdown" class="dropdown-toggle" href="1.jsp">
								<i class=" icon-edit"></i>
								<span class="badge badge-grey">跟踪布局</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-ok"></i>
									4 Tasks to complete
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">Software Update</span>
											<span class="pull-right">65%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:65%" class="progress-bar "></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">Hardware Upgrade</span>
											<span class="pull-right">35%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:35%" class="progress-bar progress-bar-danger"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">Unit Testing</span>
											<span class="pull-right">15%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:15%" class="progress-bar progress-bar-warning"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">Bug Fixes</span>
											<span class="pull-right">90%</span>
										</div>

										<div class="progress progress-mini progress-striped active">
											<div style="width:90%" class="progress-bar progress-bar-success"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										See tasks with details
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="purple">
						<a data-toggle="" class="dropdown-toggle" href="2.jsp">
							<a data-toggle="dropdown" class="dropdown-toggle" href="2.jsp">
								<i class="icon-desktop"></i>
								<span class="badge badge-grey">实时跟踪</span>
							</a>

							<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-warning-sign"></i>
									8 Notifications
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-pink icon-comment"></i>
												New Comments
											</span>
											<span class="pull-right badge badge-info">+12</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<i class="btn btn-xs btn-primary icon-user"></i>
										Bob just signed up as an editor ...
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-success icon-shopping-cart"></i>
												New Orders
											</span>
											<span class="pull-right badge badge-success">+8</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-info icon-twitter"></i>
												Followers
											</span>
											<span class="pull-right badge badge-info">+11</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										See all notifications
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="green">
							<a data-toggle="" class="dropdown-toggle" href="3.jsp">
								<a data-toggle="dropdown" class="dropdown-toggle" href="3.jsp">
									<i class="icon-book"></i>
								<span class="badge badge-grey">数据统计</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-envelope-alt"></i>
									5 Messages
								</li>

								<li>
									<a href="#">
										<img src="assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Alex:</span>
												Ciao sociis natoque penatibus et auctor ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>a moment ago</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Susan:</span>
												Vestibulum id ligula porta felis euismod ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>20 minutes ago</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Bob:</span>
												Nullam quis risus eget urna mollis ornare ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>3:15 pm</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="inbox.html">
										See all messages
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> -->

						<li class="light-blue">
							 <a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small><%=user.getUserName() %>,</small>
									<%=user.getUserId() %>
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a >
										<i class="icon-cog"></i>
										Settings
									</a>
								</li>

								<li>
									<a >
										<i class="icon-user"></i>
										Profile
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="LogoutServlet">
										<i class="icon-off"></i>
										Logout
									</a>
								</li>
							</ul> 
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<!-- <div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success">
								<i class="icon-signal"></i>
							</button>

							<button class="btn btn-info">
								<i class="icon-pencil"></i>
							</button>

							<button class="btn btn-warning">
								<i class="icon-group"></i>
							</button>

							<button class="btn btn-danger">
								<i class="icon-cogs"></i>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div>#sidebar-shortcuts -->

					<ul class="nav nav-list">
						<li >
							<a href="admin/admin5.jsp">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> app简介</span>
							</a>
						</li>

						<li class="active">
							<a href="admin/admin.jsp">
								<i class=" icon-edit"></i>
								<span class="menu-text"> 跟踪布局 </span>
							</a>
						</li>

						

						<li>
							<a href="admin/admin2.jsp" >
								<i class="icon-desktop"></i>
								<span class="menu-text"> 实时跟踪</span>

								
							</a>
						</li>
						<li>
							<a href="admin/admin3.jsp">
								<i class="icon-book"></i>
								<span class="menu-text"> 数据统计 </span>
							</a>
						</li>
						<li>
							<a href="http://115.154.191.26:8080/mesHome/">
								<i class="icon-home home-icon"></i>
								<span class="menu-text"> 课程主页</span>
							</a>
						</li>
						<% 
						if(user.getUserId().equals("admin")){
						%>
						<li>
							<a href="admin/admin4.jsp">
								<i class="icon-user"></i>
								<span class="menu-text"> 用户管理 </span>
							</a>
						</li>
						<% 
						}
						%>
				
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a >Home</a>
							</li>
							<li class="active">跟踪布局</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="row">
						
						
						
						<div class="col-xs-1  col-md-1 ">
						</div>
						<!-- 之下是整体内容部分 -->
						<!-- 主体部分 -->
						<div class="col-xs-10  col-md-10">
							<!-- PAGE CONTENT BEGINS -->
							<!----------------第一大部分----------------------->
							<!--第一 标题 -->
							<div class="page-header">
								<h1>
									机床及在制品缓存布局
									<small>
										<i class="icon-double-angle-right"></i>
										
									</small>
								</h1>
							</div>
							<!-- 第一部分正文 -->
							<div class="row">
								<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									<!-- 正文 -->
									<div class="row">
									<!-- 小标题 -->
										<div class="col-sm-12 col-xs-12 " >
											<h4>机床 &amp; RFID设备--绑定</h4>
										</div><!-- /span -->
										
										
										<div class="row">
											<% 
												if(user.getUserId().equals("admin")){
											%>
											<div class="row">
											<!----------------第一节--1.1--------------------->
											<div class="col-md-1 col-xs-2">
											</div>
											<!-- 添加机床 -->
											<div class="col-md-5 col-xs-9">
												<div class="row">
													<br>
													<div class="col-md-12">
														<label class="label label-xlg label-primary arrowed arrowed-right">添加机床</label>
													</div>
													<br>
													<div class="form form-overlay">
														<div class="form-group col-md-8 col-xs-12">
															<span class="wpcf7-form-control-wrap FULLNAME"><input
																type="text" name="FULLNAME" value="" size="40"
																name="machName" id="machName"
																class="wpcf7-form-control wpcf7-text wpcf7-validates-as-required form-control fullname"
																aria-required="true" aria-invalid="false" placeholder="机床名称"></span>
														</div>
														<div class="form-group  col-md-8 col-xs-12">
															<span class="wpcf7-form-control-wrap EMAIL"><input
																type="text" name="EMAIL" value="" size="40"
																name="machId" id="machId" 
																onblur="checkMachId()"
																class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																aria-required="true" aria-invalid="false"
																placeholder="唯一标识号，格式:m+数字(非0)，例：m1"></span><span id="checkMachId" name="checkMachId" style="color: red"></span>
														</div>
													</div>
													<div class="col-md-10 ">
														<br><br>
														<button id ="addMach" name ="addMach" type="button" class="btn btn-primary">添加</button>
														<!-- data-toggle="modal" data-target="#machModal" -->
														<button onclick="viewMach()" type="button" class="btn btn-success" >查看已有机床</button>
													</div>
						
												</div>
											</div>
											
											<!-- 添加rifig系统-->
											<div class=" col-xs-9 col-xs-offset-2 col-md-5 col-md-offset-0">
											<!----------------第二节----1.2------------------->
							
												<div class="row">
													<br>
													<div class="col-md-12">
														<label class="label label-xlg label-primary arrowed arrowed-right">添加RFID设备</label>
													</div>
													<br>
													<div class="form form-overlay">
														<div class="form-group col-md-8 col-xs-12">
															<span class="wpcf7-form-control-wrap FULLNAME"><input
																type="text" name="FULLNAME" value="" size="40"
																name="rfidName" id="rfidName"
																class="wpcf7-form-control wpcf7-text wpcf7-validates-as-required form-control fullname"
																aria-required="true" aria-invalid="false" placeholder="rfid设备名称"></span>
														</div>
														<div class="form-group  col-md-8 col-xs-12">
															<span class="wpcf7-form-control-wrap EMAIL"><input
																type="text" name="EMAIL" value="" size="40"
																name="rfidId" id="rfidId" 
																onblur="checkRfidId()"
																class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																aria-required="true" aria-invalid="false"
																placeholder="唯一标识号，格式:s+数字(非0)，例：s1"></span><span id="checkRfidId" name="checkRfidId" style="color: red"></span>
														</div>
														<div class="form-group  col-md-8 col-xs-12">
															<span class="wpcf7-form-control-wrap EMAIL"><input
																type="text" name="EMAIL" value="" size="40"
																name="rfidIp" id="rfidIp" 
													
																class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																aria-required="true" aria-invalid="false"
																placeholder="rfid设备ip"></span>
														</div>
													</div>
													<div class="col-md-12 ">
														
														<button type="button" id ="addRfid" name ="addRfid" class="btn btn-primary">添加</button>
						
													<!-- 	<button type="button" class="btn btn-success" data-toggle="modal" data-target="#rfidModal">查看已有rfid系统</button> -->
														<button type="button" class="btn btn-success" onclick="viewRfid()">查看已有rfid设备</button>
													</div>
						
						
												</div>
											<!----------------第二节结束--1.2--------------------->
											</div>
											
											<!-- Modal框显示已经配置的内容--系统信息 -->
											<div class="modal fade" id="rfidModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
											
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
															<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>RFID设备列表</h2><br>
														</div>
														<div class="modal-body">
														
														
															<table id="sample-table-list2"	class="table table-striped table-bordered table-hover">
																<thead>
																	<tr>
																		<th>设备编号</th>
																		<th>设备名称</th>
																		<th>设备所在ip</th>
																		<th>操作</th>
																		<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																	</tr>
																</thead>
															
																<tbody id ='rfidTable'>
																</tbody>
															</table>
														</div>
														<div class="modal-footer">
												      	  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
												        	<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
												     	 </div>
											
													</div>
												</div>
											</div>
											<!-- Modal框显示已经配置的内容--修改rfid系统 -->
											
											<div class="modal fade" id="editRfidModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
											
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
															<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>RFID设备修改</h2><br>
														</div>
														<div class="modal-body">
														<div class= "row">
															<br>
															<div class="col-md-12 has-success">
															 	<label for="female">设备编号：</label>
																<input  readonly="true" type="text" value="" size="40" name="rfidIdForEdit" id="rfidIdForEdit">
															</div>
															<br><br><br>
															
															<div class="col-md-12">
															 	<label for="female">设备名称：</label>
																<input type="text" name="FULLNAME" value="" size="40" name="rfidNameForEdit" id="rfidNameForEdit">
															</div>
															<br><br><br>
															
															<div class="col-md-12">
															 	<label for="female">设备IP：</label>
																<input type="text" name="FULLNAME" value="" size="40" name="rfidNIporEdit" id="rfidIpForEdit">
															</div>
														</div>
															
					  											
															
														</div>
														<div class="modal-footer">
												      	  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
												        	<button type="button" class="btn btn-primary" onclick="submitEditRfid()">确定</button> 
												     	 </div>
											
													</div>
												</div>
											</div>
											
											
											
											
											<!-- Modal框显示已经配置的内容--机床信息 -->
											<div class="modal fade" id="machModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
											
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
															<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>机床列表</h2><br>
														</div>
														<div class="modal-body">
														
														
															<table id="sample-table-list1"	class="table table-striped table-bordered table-hover">
																<thead>
																	<tr>
																		<th>机床编号</th>
																		<th>机床名称</th>
																		<th>操作</th>
																		<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																	</tr>
																</thead>
																
																<tbody id="machTable">
															
																</tbody>
															</table>
														</div>
														<div class="modal-footer">
												      	  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
												        	<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
												     	 </div>
											
													</div>
												</div>
											</div>
											
											<!-- Modal框显示已经配置的内容--修改机床 -->
											
											<div class="modal fade" id="editMachModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
											
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
															<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>机床修改</h2><br>
														</div>
														<div class="modal-body">
														<div class= "row">
															<br>
															<div class="col-md-12 has-success">
															 	<label for="female">机床编号：</label>
																<input  readonly="true" type="text" name="FULLNAME" value="" size="40" name="machIdForEdit" id="machIdForEdit">
															</div>
															<br><br><br>
															
															<div class="col-md-12">
															 	<label for="female">机床名称：</label>
																<input type="text" name="FULLNAME" value="sss" size="40" name="machNameForEdit" id="machNameForEdit">
															</div>
															
														</div>
															
					  											
															
														</div>
														<div class="modal-footer">
												      	  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
												        	<button type="button" class="btn btn-primary" onclick="submitEditMach()">确定</button>
												     	 </div>
											
													</div>
												</div>
											</div>
											<!----------------第一节结束--1.1--------------------->				
											</div>
											<% }%>
										
											<div class="row">
											<br>
											<br>
											</div>		
											<div class="row">
												<div   class="col-xs-3 col-md-1 ">
												</div>
												<!-- 选择区 -->
												<div class ="col-xs-7  col-md-4 " >
														<div class="row">
															<label class="label label-xlg label-primary arrowed arrowed-right">请选择可用机床</label>
															<select class="form-control" name="selectMachId" id="selectMachId">
															</select>
														</div>
												</div>
												
												<div   class="col-xs-3 col-md-1 ">
												</div>
												<!-- 选择区 -->
												<div class="col-xs-7 col-md-4 ">
													<div class="row">
														<label class="label label-xlg label-primary arrowed arrowed-right">请选择可用RFID设备</label>
																<select class="form-control" name="selectRfidSysId" id="selectRfidSysId">
														</select>
													</div>
												</div>
												
												
											</div>
											
											<div class="row">
												<br>
											</div>
											
											<div class="row">
												<div   class="col-xs-1 col-md-1 col-sm-2">
												</div>
												<!-- 图区 -->
												<div class="col-xs-9 col-xs-offset-1 col-md-4 col-sm-4 ">
													<li class="text-warning bigger-110 orange">
																	绑定模式
																</li>
													<img src="image/model1.png">
												</div>
												
												
												<!-- 按钮-->
												<div class="col-xs-10  col-md-4 col-sm-4">
													<div class="row">
													<div   class="col-xs-3 ">
													</div>
													<div class="col-xs-9  col-md-12 ">
														<button id="machRfid" name="machRfid" type="button" class="btn btn-primary">提交</button>
														<!-- 这里用模态比较  -->
												<!-- 		<button  type="button" class="btn btn-success" data-toggle="modal" data-target="#machRifdModal">查看已有配置</button> -->
														<!-- 这里采用js主动调用模态比 -->
														<button  type="button" class="btn btn-success" onclick="viewMachRfid()">查看已有配置</button>
													</div>
													</div>
												</div>
											</div>
											
											
												<!-- Modal框显示已经配置的内容 -->
												<div class="modal fade" id="machRifdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
												
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
																<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>机床与RFID设备绑定列表</h2><br>
															</div>
															<div class="modal-body">
																<table id="sample-table-list3"	class="table table-striped table-bordered table-hover">
																	<thead>
																		<tr>
																			<th>机床编号</th>
																			<th>机床名称</th>
																			<th>设备编号</th>
																			<th>设备名称</th>
																			<th>操作</th>
																			<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																		</tr>
																	</thead>
																
																	<tbody id="machRfidTable">
																	</tbody>
																</table>
												
															</div>
															<div class="modal-footer">
													      	  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
													        	 <!-- <button type="button"  >导出数据</button>  -->
													     	 </div>
												
														</div>
													</div>
												</div>
												<!-- Modal框显示已经配置的内容 -->
										</div>
									</div>
								</div>
							</div>
							
							<!----------------第一大部分结束----------------------->
							
							<!---------------第二部分------------------------>
							<!--第二 标题 -->
							<hr>
							<hr>
							<div class="page-header">
								<h1>
									定义跟踪零件信息
									<small>
										<i class="icon-double-angle-right"></i>
										
									</small>
								</h1>
							</div>
							
							<div class="row" id="content">
									<div class="row">
							<!------------------第一节-2.1-------------------->	
											
												<br>
												<!-- 图区1 -->
												<div class="row">
												
													<div class="col-md-1 col-xs-1 ">
													</div>
													<div class="col-md-10 col-xs-10 ">
														<div class="widget-box">
															<div class="widget-header">
																<h4>零件和RFID标签绑定</h4>
					
																<span class="widget-toolbar"> <!-- <a href="#" data-action="settings">
																				<i class="icon-cog"></i>
																			</a> --> <a href="#" data-action="reload"> <i
																		class="icon-refresh"></i>
																</a> <a href="#" data-action="collapse"> <i
																		class="icon-chevron-up"></i>
																</a> <!-- <a href="#" data-action="close">
																				<i class="icon-remove"></i>
																			</a> -->
																</span>
															</div>
					
															<div class="widget-body">
																<div class="widget-main">
																	<!-- 三个填空 -->
																	<div class="row">
																		<div class="form form-overlay">
																			<div style="margin-bottom:20px;" class="center">
																			资源类别：<select id="select_c" style="width:140px;">      <option value="03" stringcode="03">工件 03</option></select>
																			资源大类： <select id="select_l" style="width:140px;" ><option value="03" stringcode="03">工件（毛坯、在制品） 03</option></select>
																			资源小类：<select id="select_s" style="width:140px;" ><option value="02" stringcode="02">锻件 02</option></select>
																			资源组别： <select id="select_g" style="width:140px;" ><option value="01" stringcode="01">火电锻件01</option><option value="02" stringcode="01">水电锻件02</option><option value="03" stringcode="01">船用锻件</option></select>

																			</div>
																			<div class="form-group col-sm-2 col-xs-12">
																				</div>
																			<div class="form-group col-sm-4 col-xs-12">
																				<span class="wpcf7-form-control-wrap FULLNAME"><input
																					type="text" name="FULLNAME" value="" size="40"
																					name="workpieceName" id="workpieceName"
																					class="wpcf7-form-control wpcf7-text wpcf7-validates-as-required form-control fullname"
																					aria-required="true" aria-invalid="false" placeholder="零件名称"></span>
																			</div>
																			<div class="form-group  col-sm-4 col-xs-12">
																				<span class="wpcf7-form-control-wrap EMAIL"><input
																					type="text" name="EMAIL" value="" size="40"
																					name="workpieceIdTail" id="workpieceIdTail"
																					onblur="checkWorkpieceId()"
																					class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																					aria-required="true" aria-invalid="false"
																					placeholder="资源串号，例：CL001(长度保持奇数位)"></span><span id="checkWorkpieceId" name="checkWorkpieceId" style="color: red"></span>
																			</div>
																		<!-- 	<div class="form-group  col-sm-4 col-xs-12">
																				<span class="wpcf7-form-control-wrap EMAIL"><input
																					type="text" name="EMAIL" value="" size="40"
																					onblur="checkWorkpieceRfid()"
																					name="workpieceRfid" id="workpieceRfid"
																					class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																					aria-required="true" aria-invalid="false" placeholder="rfid编码"></span>
																					<span id="checkWorkpieceRfid" name="checkWorkpieceRfid" style="color: red"></span>
																			</div> -->
									
																		</div>
																	</div>
																	<!-- 三个填空 -->
																	<!-- 按钮 -->
																	<div class="row">
																		<div class="col-md-6 col-md-offset-4">
																				<br> <br>
																			<button  id="addWorkpiece" type="button" class="btn btn-primary">提交配置</button>
																			<!-- 原模态方法 -->
																			<!-- <button type="button" class="btn btn-success" data-toggle="modal" data-target="#workpieceModal">查看已有配置</button> -->
																			<button type="button" class="btn btn-success" onclick="viewWorkpiece()">查看已有配置</button>
																		</div>
																	</div>
																	<!-- 按钮 -->
																</div>
															</div>
														</div>
													</div>
												</div>
													<!-- Modal框显示已经配置的内容 -->
												<div class="modal fade" id="workpieceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
												
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
																<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>零件列表</h2><br>
															</div>
															<div class="modal-body">
																<table id="sample-table-list4"	class="table table-striped table-bordered table-hover">
																	<thead>
																		<tr>
																			<th>零件编号</th>
																			<th>零件名称</th>
																			<th>RFID标签绑定状态</th>
																			<th>添加时间</th>
																			<th>工序状态</th>
																			<!-- <th>操作</th> -->
																			<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																		</tr>
																	</thead>
																	
																	<tbody id="workpieceTable">
																	</tbody>
																</table>
												
															</div>
															<div class="modal-footer">
													      	  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
													        	<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
													     	 </div>
												
														</div>
													</div>
												</div>
												<!-- 图区1 -->
												<br>
												
												<br>
												<!-- 图区2 -->
												<div class="row">
												
													<div class="col-md-1 col-xs-1 ">
													</div>
													<div class="col-md-10 col-xs-10 ">
														<div class="widget-box">
															<div class="widget-header">
																<h4>零件添加工序</h4>
					
																<span class="widget-toolbar"> <!-- <a href="#" data-action="settings">
																				<i class="icon-cog"></i>
																			</a> --> <a href="#" data-action="reload"> <i
																		class="icon-refresh"></i>
																</a> <a href="#" data-action="collapse"> <i
																		class="icon-chevron-up"></i>
																</a> <!-- <a href="#" data-action="close">
																				<i class="icon-remove"></i>
																			</a> -->
																</span>
															</div>
					
															<div class="widget-body">
																<div class="widget-main">
																	<!-- 三个填空 -->
																	<div class="row">
																		<div class="form form-overlay">
																			<div class="col-md-10 ">
																			<li class="text-danger"><p>请选择尚未配置完成的零件</p></li>
																				<!-- 这里应该只用名字 -->
																				<select class="form-control" id="selectWorkpieceId" name="selectWorkpieceId">
																				</select>
																			</div>
																			
																			<div class="form-group col-sm-10 col-xs-12">
																			<li class="text-danger"><p id="processConutOfWorkpiece">选择零件并按工序优先级录入工序</p></li>
																				
																			</div>
																			<div class="form-group col-sm-10 col-xs-12">
																				<span class="wpcf7-form-control-wrap FULLNAME"><input
																					id ="processName" name="processName"
																					type="text" name="FULLNAME" value="" size="40"
																					class="wpcf7-form-control wpcf7-text wpcf7-validates-as-required form-control fullname"
																					aria-required="true" aria-invalid="false" placeholder="工序名称"></span>
																					
																			</div>
																			<!-- 工序id自动生成，不再让用户添加 -->
																			<div style="display:none;" class="form-group  col-sm-10 col-xs-12">
																				<span class="wpcf7-form-control-wrap EMAIL"><input
																					id ="processId" name="processId"
																					onblur="checkProcessId()"
																					type="text" name="EMAIL" value="" size="40"
																					class="wpcf7-form-control wpcf7-text wpcf7-email wpcf7-validates-as-required wpcf7-validates-as-email form-control email"
																					aria-required="true" aria-invalid="false"
																					placeholder="唯一标识号，格式:p+数字(非0)，例：p1"></span>
																					<span id="checkProcessId" name="checkProcessId" style="color: red"></span>
																			</div> 
																			<div class="col-md-10 ">
																				<p>请选择机床</p>
																				<select class="form-control" id="selectMachId2" name="selectMachId2">
																				
																				<% 
																					for (Machine m :machines){
																				%>
																				
																				<option value="<%=m.getMachId()%>"><%=m.getMachName() %></option>
																				
																				<%} %>
																					
																				</select>
											
																			</div>
																		</div>
																	</div>
																	<!-- 4个填空 -->
																	<!-- 1按钮 -->
																	<div class="row">
																		<div class="col-md-5 col-md-offset-5 col-xs-5 col-xs-offset-5">
																			<br> <br>
																			<!-- <button id="workpieceProcessSubmit" type="button" class="btn btn-primary">提交配置</button> -->
																			<button id="addWorkpieceProcess" type="button" class="btn btn-primary">录入工序</button>
															
																			 <button id = "workpieceProcessFinished"type="button" class="btn btn-success" >配置完毕</button> 
																			 <!-- <button type="button" class="btn btn-success" data-toggle="modal" data-target="#workpieceProcessModal">查看已有配置</button> --> 
																		</div>
																	</div>
																	<!-- 按钮 -->
																</div>
															</div>
														</div>
													</div>
												</div>
											<!-- 图区2 -->
								</div>
							</div>
							<hr>
							<hr>
							<hr>
							<!---------------第三部分------------------------>
							<!--第三标题 -->
							<div class="page-header">
								<h1>
									已定义零件信息管理
									<small>
										<i class="icon-double-angle-right"></i>
										
									</small>
								</h1>
							</div>
							
							<div class="row" id="content">
													<!---------------第三部分------------------------>	
									<div class="row">
										
										
										<div class="row"
											style="width: 90%; margin-right: auto; margin-left: auto; ">
											<br>
											<table id="sample-table-list5"	class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<th>零件编号</th>
																	<th>零件名称</th>
																	<th>RDID绑定状态</th>
																	<th>添加时间</th>
																	 <th>操作</th> 
																	<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																</tr>
															</thead>
														
															<tbody id="workpieceTable2">
															</tbody>
														</table>
											</div>
										
									</div>
									
									<!-- Modal框显示已经配置的内容 -->
									<div class="modal fade" id="workpieceDetialModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
									
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
													<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>零件工序列表</h2><br>
												</div>
												<div class="modal-body">
												
													<div class ="row">
													
														<div class="col-md-12">
															<h4 id="workpieceDetialTitle"align="center" class="blue">零件：车轮———————— 编号：99</h4>
															
														</div>
													
														<div class="col-md-12">
														<table id="sample-table-list6"	class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<th>序号</th>
																	<th>工序名称</th>
																	<th>工序编号</th>
																	<th>机床名称</th>
														
																	<!-- <th>操作</th> -->
																	<!-- 其他的还包括：我的访问令牌 、传感器ID 、设备状态   -->
																</tr>
															</thead>
													
															<tbody id="processList">
															</tbody>
														</table>
														</div>
														<div class="col-md-12" id="workpieceFlag">
															<!-- <p id="workpieceFlag" align="center"></p> -->
															
														</div>
													</div>
												</div>
													<div class="modal-footer" id="confirmWorkpiece" >
										     		 </div>
									
											</div>
										</div>
									</div>
									
											<!-- Modal框显示已经配置的内容,绑定过程 -->
									<div class="modal fade" id="bandingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
									
												<div class="modal-header">
													<!-- <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> -->
													<h2 align="center"  class="modal-title" id="myModalLabel"><i class="icon-th-large"></i>RFID标签绑定</h2><br>
												</div>
												<div class="modal-body">
												
													<div class ="row">
													
														<div class="col-md-12">
															<h4 id="time"align="center" class="blue">剩余时间：15s</h4>
														</div>
														<div class="col-md-1">
            												
														</div>
														<div class="col-md-6">
            												<img src="image/RFID.gif">
														</div>
														
													</div>
												</div>
													<div class="modal-footer" id="isBanding" >
										     		 </div>
									
											</div>
										</div>
									</div>
								
							</div>
								<!---------------第三部分结束------------------------>	
							
								<!---------------<!-- 第四部分 -->
						<hr>
							<hr>
							
							<div class="page-header">
								<h1>
									RFID标签查看工具
									<small>
										<i class="icon-double-angle-right"></i>
										
									</small>
								</h1>
							</div>
							
							<div class="row" id="content">
													<!---------------第四部分------------------------>	
									<div class="row">
										
										
										<div class="row"
											style="width: 90%; margin-right: auto; margin-left: auto; ">
											<br>
										<div class="col-md-6">
            									<img id="imgRFID" src="image/RFID2.jpg">
										</div>
										<div class="col-md-6">
            								<div class="widget-box ui-sortable-handle" id="widget-box-5">
												<div class="widget-header">
													<h5 class="widget-title smaller">标签信息查询</h5>

													<div class="widget-toolbar">
														<span class="label label-success">
															success
															<i class="ace-icon fa fa-arrow-up"></i>
														</span>
													</div>
												</div>

												<div class="widget-body">
													<button onclick="getTagInfo()"class="btn btn-app btn-warning">
														<i class="ace-icon fa fa-undo bigger-230"></i>
														查询
													</button>
													<div class="widget-main padding-6">
														<div id="tagInformation" class="alert alert-info"> Hello World! </div>
													</div>
												</div>
											</div>
										</div>
										</div>
										
									</div>
							</div>
							<!-------------<!-- 第四部分 -->
					</div><!-- 主体部分 -->
				</div><!-- /.row -->
			</div><!-- /.page-content -->
		</div><!-- /.main-content -->

				<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; Choose Skin</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
							<label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								Inside
								<b>.container</b>
							</label>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script> -->

		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->

		
		<script language="javascript" type="text/javascript">
	/* 这里是导航定位用的 */
	function go(num) {
		var oDiv = document.getElementById(num);
		oDiv.scrollIntoView();
	}

	$(document).ready(function() {
		//这部分全是添加单击事件响应的
		
		//添加机床
		$("#addMach").click(function() {
			
			addMach();
			
		});
		$("#viewMach").click(function() {
		//viewMach();
			//$('#machModal').modal('show');
			
		});
		//添加Rfid
		$("#addRfid").click(function() {
			addRfid();
			
		});
		//机床和系统绑定
		$("#machRfid").click(function() {
			addMachRfid();
		});
		
		//添加零件
		$("#addWorkpiece").click(function() {
			addWorkpiece();
			
		});
		
		//切换零件程序	
		$("#selectWorkpieceId").change(function() {
			getProcessCountByWorkpieceId();
			getAvailableMachine4workpieceProcess();
		});
		
		//录入工序
		$("#addWorkpieceProcess").click(function() {
			
			addWorkpieceProcess2();
		});
		//工序配置完毕
		$("#workpieceProcessFinished").click(function() {
			var workpieceName =  $("#selectWorkpieceId").val()
			if(workpieceName!=''){
				isworkpieceProcessFinished(workpieceName);
			}else{
				alert("请选择零件");
			}
			
		});
		
		//提交零件工序信息
		$("#workpieceProcessSubmit").click(function() {
			addWorkpieceProcess();
		});
	});

	/////////////////////////////////////////////////
</script>


		<script src="js/jquery.dataTables.min.js"></script>
		<script src="js/jquery.dataTables.bootstrap.js"></script>
	
<script src="tableOut/xlsx.core.min.js"></script>
<script src="tableOut/blob.js"></script>
<script src="tableOut/FileSaver.min.js"></script>
<script src="tableOut/tableexport.js"></script>


<script type="text/javascript">
$(function(){
	
	/* $("#sample-table-list6").tableExport({
		formats:["xlsx"],
		fileName: 'tableName', 
		ignoreCols: null,
		position: "bottom" 
		
	}); */
	
})



</script>


<script src="js/validate.js" type="text/javascript"></script>
<script src="js/first.js" type="text/javascript"></script>
</body>
</html>
