<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="xjtu.mes.util.*,xjtu.mes.model.*,xjtu.mes.manager.*"%>
<%@page import="java.util.*"%>
<%@page import="xjtu.mes.exception.*"%>
<%
User user =null;
String userId = request.getParameter("userId");
if(userId != null&&!userId.trim().equals("")){
		
		//带引userID和password，来检测获取输入是否存在问题
		//System.out.print(userId+password);
		try{
			user = UserManager.getInstance().checkUser(userId.trim());
			session.setAttribute("user_info", user);
			session.setMaxInactiveInterval(-1);
			 
			
		}catch(UserNotFoundException e){
			response.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		
	
}else{
	user = (User)session.getAttribute("user_info");
	if(null == user){
		response.sendRedirect("/rfid_monitor/errorPage.jsp");
		return ;
	}
	
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
	<link rel="stylesheet" href="assets/css/colorbox.css" />
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


</head>

<body onload="">
	<div class="navbar navbar-default" id="navbar">
		<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>


		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small><i class="icon-cog"></i>RFID工序监控
				</small>
				</a>
				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->

			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
			<!-- 			<li class="grey">
							<a data-toggle="" class="dropdown-toggle" href="admin/admin.jsp">
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
						<a data-toggle="" class="dropdown-toggle" href="admin/admin2.jsp">
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
							<a data-toggle="" class="dropdown-toggle" href="admin/admin3.jsp">
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
<!-- 
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
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
				</div> -->
				<!-- #sidebar-shortcuts -->

			<ul class="nav nav-list">
						<li class="active">
							<a href="admin/admin5.jsp">
								<i class="icon-dashboard"></i>
								<span class="menu-text"> app简介 </span>
							</a>
						</li>

						<li >
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
							<a href="http://115.154.191.5:8080/mesHome/">
								<i class="icon-home home-icon"></i>
								<span class="menu-text"> 课程主页</span>
							</a>
						</li>
						<% 
						if(user.getUserId().equals("admin")){
						%>
						<li >
							<a href="admin/admin4.jsp">
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
						<li><i class="icon-home home-icon"></i> <a>Home</a>
						</li>

						<li class="active">app简介</li>
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
							<div class="page-header">
								<h1>
									实验平台简介
									
								</h1>
							</div>

							<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="alert alert-block alert-success">
									<!-- <button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button> -->

									<i class="icon-ok green"></i>

									欢迎使用
									<strong class="green">
									RFID工序监控App
										<small>(v2016)</small>
									</strong>
									<br>
									<br>
									<h5><strong class="green">
									背景知识介绍
										
									</strong></h5>
									
									<span>1) RFID工序监控 App主要是结合rfid技术和web技术，实现车间内在制品的实时监控；</span>
									<br>
									<span>2) 对零件进行监控之前， 首先要配置RFID设备、机床等硬件， 然后在web端进行机床、RFID设备、零件等相关软配置，当零件工序配置完毕后便可
									自动实时追踪零件的加工情况；</span>
									<br>
									<span>3)现实中零件的工序及加工环境十分复杂，而实验室所提供的设备不能完全满足该需求，故下述案例中零件的工序不具备真实性，只是简单地展示RFID技术在零件工序跟踪方面中的应用。</span>
										
								</div>

								<div class="row">
								<div class="col-sm-7 col-sm-offset-3 infobox-container">
								<p class="lead">
								RFID工序监控App主要由三个功能模块
										</p>
										</div>
									<div class="space-6"></div>

									<div class="col-sm-7 col-sm-offset-3 infobox-container">
										<div class="space-6"></div>

										<div class="infobox infobox-green infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class=" icon-edit bigger-120"></i>
											</div>

											<div class="infobox-data">
											
												<div class="infobox-content">跟踪布局</div>
												<div class="infobox-content">部分</div>
											</div>
										</div>

										<div class="infobox infobox-blue infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class="icon-desktop"></i>
											</div>

											<div class="infobox-data">
												<div class="infobox-content">实时跟踪</div>
												<div class="infobox-content">部分</div>
											</div>
										</div>

										<div class="infobox infobox-grey infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class="icon-book"></i>
											</div>

											<div class="infobox-data">
												<div class="infobox-content">数据统计</div>
												<div class="infobox-content">部分</div>
											</div>
										</div>
									</div>

									<div class="vspace-sm"></div>

								
								</div><!-- /row -->

								<div class="hr hr32 hr-dotted"></div>

								<div class="row">
									<div class="col-sm-7 col-sm-offset-3">
										<div class="widget-box transparent" id="recent-box">
											<div class="widget-header">
												<h4 class="lighter smaller">
													<i class="icon-rss orange"></i>
													使用流程
												</h4>

												<div class="widget-toolbar no-border">
													<ul class="nav nav-tabs" id="recent-tab">
													<li class="active">
															<a data-toggle="tab" href="#tab1">硬件配置</a>
														</li>
														<li >
															<a data-toggle="tab" href="#task-tab">跟踪布局</a>
														</li>

														<li>
															<a data-toggle="tab" href="#member-tab">实时跟踪</a>
														</li>

														<li>
															<a data-toggle="tab" href="#comment-tab">数据统计</a>
														</li>
													</ul>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main padding-4">
													<div class="tab-content padding-8 overflow-visible">
													
														<div id="tab1" class="tab-pane active">
															<h4 class="smaller lighter green">
																<i class="icon-list"></i>
																该环节是整个实验的基础，只有当硬件配置完毕后，app才能正常工作。
															</h4>
															
															<div class="widget-main">
															<p class="alert alert-info">
															硬件配置主要涉及到RFID读写器、机床等相关设备，该部分已配置完毕，不作为实验的一部分，顾这里只给予简单说明。
															</p>
															</div>
															<div class="col-xs-12">
																<!-- PAGE CONTENT BEGINS -->
								
																<div class="row-fluid">
																	<ul class="ace-thumbnails">
																		<li>
																			<a href="image/example/big/0-1.jpg" title="Photo Title" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/0-1.jpg">
																				<div class="text">
																					<div class="inner">管理员</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第一步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">RFID设备实物配置</span>
																					</span>
																				</div>
																			</a>
								
																		
																		</li>
								
																		<li>
																			<a href="image/example/big/0-2.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/0-2.jpg">
																				<div class="text">
																					<div class="inner">管理员</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第二步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">RFID设备软配置</span>
																					</span>
																				</div>
																			</a>
																		</li>
																			<li>
																			<a href="image/example/big/0-3.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/0-3.jpg">
																				<div class="text">
																					<div class="inner">管理员</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第三步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">机床实物配置</span>
																					</span>
																				</div>
																			</a>
																		</li>
								
																		
																	</ul>
																</div><!-- PAGE CONTENT ENDS -->
															</div>

															<div class="hr hr-double hr8"></div>
														</div>
													
													
														<div id="task-tab" class="tab-pane ">
															<h4 class="smaller lighter green">
																<i class="icon-list"></i>
																该环节主要是对设备及零件的web端配置
															</h4>
															
															<div class="widget-main">
															<p class="alert alert-info">
															管理员负责实验室公共资源（机床和RFID设备）的信息录入、删除等操作，而使用者可利用这些公共资源进行绑定及查看，同时可以添加相关零件及工序信息，以便后续的零件实时监控
															</p>
															</div>
															<div class="col-xs-12">
																<!-- PAGE CONTENT BEGINS -->
								
																<div class="row-fluid">
																	<ul class="ace-thumbnails">
																		<li>
																			<a href="image/example/big/1-1.jpg" title="Photo Title" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-1.jpg">
																				<div class="text">
																					<div class="inner">管理员</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第一步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">添加机床</span>
																					</span>
																				</div>
																			</a>
								
																		
																		</li>
								
																		<li>
																			<a href="image/example/big/1-2.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-2.jpg">
																				<div class="text">
																					<div class="inner">管理员</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第二步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">添加RFID设备</span>
																					</span>
																				</div>
																			</a>
																		</li>
								
																		<li>
																			<a href="image/example/big/1-3.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-3.jpg">
																				<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第三步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">机床与RFID设备绑定及查看</span>
																					</span>
																				</div>
																			</a>
								
																		</li>
								
																		<li>
																			<a href="image/example/big/1-4.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-4.jpg">
																			<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第四步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">添加零件及添加工序</span>
																					</span>
																				</div>
																			</a>
								
																			
																		</li>
																			<li>
																			<a href="image/example/big/1-5.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-5.jpg">
																			<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第五步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">查看零件信息及管理</span>
																					</span>
																				</div>
																			</a>
								
																			
																		</li>
																									<li>
																			<a href="image/example/big/1-6.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/1-6.jpg">
																			<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">工具</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">RFID标签信息查看</span>
																					</span>
																				</div>
																			</a>
								
																			
																		</li>
																	</ul>
																</div><!-- PAGE CONTENT ENDS -->
														</div>

														
														</div>

														<div id="member-tab" class="tab-pane">
																<h4 class="smaller lighter green">
																<i class="icon-list"></i>
																该环节主要功能是实时监控零件的加工情况
															</h4>
															
															<div class="widget-main">
															<p class="alert alert-info">
															使用者选择自己相关零件后，便可查看相关信息，同时底部实时展示工序状态，并实时计算出相关数据，如出入缓存等待时间。
															</p>
															</div>
															<div class="col-xs-12">
																<!-- PAGE CONTENT BEGINS -->
								
																<div class="row-fluid">
																	<ul class="ace-thumbnails">
																		<li>
																			<a href="image/example/big/2-1.jpg" title="Photo Title" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/2-1.jpg">
																				<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第一步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">选择零件</span>
																					</span>
																				</div>
																			</a>
								
																		
																		</li>
								
																		<li>
																			<a href="image/example/big/2-2.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/2-2.jpg">
																				<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																				<span class="label-holder">
																					<span class="label label-warning arrowed-in">第二步</span>
																				</span>
																					<span class="label-holder">
																						<span class="label label-info">实时跟踪工序状态</span>
																					</span>
																				</div>
																			</a>
																		</li>
								
																	</ul>
																</div><!-- PAGE CONTENT ENDS -->
														</div>
														</div><!-- member-tab -->

														<div id="comment-tab" class="tab-pane">
																						<h4 class="smaller lighter green">
																<i class="icon-list"></i>
																该环节主要功能是完成相关的信息统计工作。
															</h4>
															
															<div class="widget-main">
															<p class="alert alert-info">
															分别以机床为中心和以零件为中心进行相关数据的统计。<br>1)在机床信息统计区域，用户可以查看与该机床相关的数据统计；<br>2)在零件信息统计区域，用户可以查看与零件相关的信息统计；
															</p>
															</div>
															<div class="col-xs-12">
																<!-- PAGE CONTENT BEGINS -->
								
																<div class="row-fluid">
																	<ul class="ace-thumbnails">
																		<li>
																			<a href="image/example/big/3-1.jpg" title="Photo Title" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/3-1.jpg">
																				<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																					<span class="label-holder">
																						<span class="label label-info">机床信息统计</span>
																					</span>
																				</div>
																			</a>
								
																		
																		</li>
								
																		<li>
																			<a href="image/example/big/3-2.jpg" data-rel="colorbox" class="cboxElement">
																				<img alt="150x150" src="image/example/small/3-2.jpg">
																				<div class="text">
																					<div class="inner">普通用户</div>
																				</div>
																				<div class="tags">
																					<span class="label-holder">
																						<span class="label label-info">零件信息统计</span>
																					</span>
																				</div>
																			</a>
																		</li>
								
																		
																	</ul>
																</div><!-- PAGE CONTENT ENDS -->
														</div>

															<div class="hr hr-double hr8"></div>
														</div>
													</div>
												</div><!-- /widget-main -->
											</div><!-- /widget-body -->
										</div><!-- /widget-box -->
									</div><!-- /span -->
								</div><!-- /row -->
								<!-- PAGE CONTENT ENDS -->
								</div><!-- /.col -->
								
								<div class="row">
								<br>
								<br>
								<div class="col-xs-2">
								</div>
								<div class="col-sm-offset-10 col-xs-offset-2">
										<a href="PDF/detail.pdf" class="btn btn-info" target="_blank">
												实验说明详情下载
												<i class="icon-print  align-top bigger-125 icon-on-right"></i>
											</a>
								</div>
								</div>
								</div><!-- /.row -->
			

			
					<!-- /.row -->
					
				</div>
				<!-- /.page-content -->
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

		<!--[if !IE]> -->

	<!-- 	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script> -->

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

		<script src="assets/js/jquery.colorbox-min.js"></script>

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			jQuery(function($) {
	var colorbox_params = {
		reposition:true,
		scalePhotos:true,
		scrolling:false,
		previous:'<i class="icon-arrow-left"></i>',
		next:'<i class="icon-arrow-right"></i>',
		close:'&times;',
		current:'{current} of {total}',
		maxWidth:'100%',
		maxHeight:'100%',
		onOpen:function(){
			document.body.style.overflow = 'hidden';
		},
		onClosed:function(){
			document.body.style.overflow = 'auto';
		},
		onComplete:function(){
			$.colorbox.resize();
		}
	};

	$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
	$("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon

	/**$(window).on('resize.colorbox', function() {
		try {
			//this function has been changed in recent versions of colorbox, so it won't work
			$.fn.colorbox.load();//to redraw the current frame
		} catch(e){}
	});*/
})
		</script>

	
</body>
</html>
