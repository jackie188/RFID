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

%>


<html lang="en">
<head>
<meta charset="utf-8" />
<title>RFID</title>
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

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

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
</style>

</head>

<body onload="update()" >
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small> <i
						class="icon-cog"></i> RFID工序监控
				</small>
				</a>
				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->

			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<!-- 	<li class="grey">
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
				</ul>
				<!-- /.ace-nav -->
			</div>
			<!-- /.navbar-header -->
		</div>
		<!-- /.container -->
	</div>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">
				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'fixed')
					} catch (e) {
					}
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
						<span class="btn btn-success"></span> <span class="btn btn-info"></span>

						<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
					</div>
				</div>
				#sidebar-shortcuts -->

					<ul class="nav nav-list">
						<li >
							<a href="admin/admin5.jsp">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> app简介 </span>
							</a>
						</li>

						<li >
							<!-- <a href="admin/admin.jsp"> -->
							<a onclick ="go('admin/admin.jsp')" >
								<i class=" icon-edit"></i>
								<span class="menu-text"> 跟踪布局 </span>
							</a>
						</li>

						

						<li class="active">
							<!-- <a href="admin/admin2.jsp" > -->
							<a onclick ="go('admin/admin2.jsp')" >
								<i class="icon-desktop"></i>
								<span class="menu-text"> 实时跟踪</span>

								
							</a>

							
						</li>
						<li>
							<a onclick ="go('admin/admin3.jsp')" >
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
							<!-- <a href="admin/admin4.jsp"> -->
							<a onclick ="go('admin/admin4.jsp')" href="">
								<i class="icon-user"></i>
								<span class="menu-text"> 用户管理 </span>
							</a>
						</li>
						<% 
						}
						%>
				
					</ul><!-- /.nav-list -->
				<!-- /.nav-list -->

				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>

				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'collapsed')
					} catch (e) {
					}
				</script>
			</div>

			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a >Home</a>
						</li>

						<li class="active">实时跟踪</li>
					</ul>
					<!-- .breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="icon-search nav-search-icon"></i>
							</span>
						</form>
					</div>
					<!-- #nav-search -->
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
									选择待跟踪零件
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
											<h4>零件详情 &amp; 工序详情</h4>
										</div><!-- /span -->
										
										<!-- 选择区 -->
										<div class="row">
											<div   class="col-xs-2 col-md-1 ">
											</div>
											<div class ="col-xs-9  col-md-4 " >
												
													<div class="row">
													<label class="label label-xlg label-primary arrowed arrowed-right">请选择零件</label>
													<select class="form-control" id="selectWorkpieceId"
														name="selectWorkpieceId">
													</select>
													</div>
											</div>
											<div   class="col-xs-1">
											</div>
											<div   class="col-xs-1 ">
											</div>
											<!-- 图区 -->
											<div class="col-md-6 col-md-offset-1 col-xs-12 ">
												<div class="widget-box">
													<div class="widget-header">
														<h4>零件基本信息</h4>
			
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
															<div class="profile-info-row">
																<div class="profile-info-name">零件编号</div>
			
																<div class="profile-info-value">
																	<span id="workpieceId">请选择零件&nbsp;</span>
																</div>
															</div>
			
															<div class="profile-info-row">
																<div class="profile-info-name">零件名称</div>
			
																<div class="profile-info-value">
																	<span id="workpieceName">请选择零件 &nbsp;</span>
																</div>
															</div>
			
															<div class="profile-info-row">
																<div class="profile-info-name">零件RFID标签号</div>
			
																<div class="profile-info-value">
																	<span id="workpieceRfid">请选择零件&nbsp;</span>
																</div>
															</div>
			
															<div class="profile-info-row">
																<div class="profile-info-name">工艺信息</div>
			
																<div class="profile-info-value">
																	<span id="processInfo">请选择零件 &nbsp</span> </span>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<!----------------第一大部分结束----------------------->
							
							<!---------------第二部分------------------------>
							<!----------------第一大部分----------------------->
							<!--第二 标题 -->
							<div class="page-header">
								<h1>
									工序实时跟踪
									<small>
										<i class="icon-double-angle-right"></i>
										
									</small>
								</h1>
							</div>
							
							<div class="row" id="content">
							
							</div>
							
							
						</div><!-- 主体部分 -->
					</div>	<!-- /.row -->
				</div>	<!-- /.page-content -->	
			</div>
			<!-- /.main-content -->
		

		<div class="ace-settings-container" id="ace-settings-container">
			<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
				id="ace-settings-btn">
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
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-navbar" /> <label class="lbl"
						for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-sidebar" /> <label class="lbl"
						for="ace-settings-sidebar"> Fixed Sidebar</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-breadcrumbs" /> <label class="lbl"
						for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-rtl" /> <label class="lbl"
						for="ace-settings-rtl"> Right To Left (rtl)</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-add-container" /> <label class="lbl"
						for="ace-settings-add-container"> Inside <b>.container</b>
					</label>
				</div>
			</div>
		</div>
		<!-- /#ace-settings-container -->
	</div>
	<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->

	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script> -->

	<!-- <![endif]-->

	<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->

	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/typeahead-bs2.min.js"></script>

	<!-- page specific plugin scripts -->

	<!-- ace scripts -->

	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>

	<!-- inline scripts related to this page -->
	
	<script src="js/validate.js" type="text/javascript"></script>
	<script src="js/second.js" type="text/javascript"></script>

	<script language="javascript" type="text/javascript">
	$(document).ready(function() {
		//这部分全是添加单击事件响应的

		//切换零件程序	
		/* $("#selectMachId").change(function() {
			updateWorkpieceList();
		}); */
		$("#selectWorkpieceId").change(function() {
			showDetialWorkpiece();
			showDetialWorkpieceProcess();
			getInfo($("#selectWorkpieceId").val());
		
		});

	});
	</script>


	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/jquery.dataTables.bootstrap.js"></script>
</body>
</html>
