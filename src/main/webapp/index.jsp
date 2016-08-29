<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>

<meta charset="utf-8">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Mysql表结构兼容性校验</title>

<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<!-- <link href="assets/css/bootstrap-theme.min.css" rel="stylesheet"> -->
<link href="assets/css/bootstrap-dialog.min.css" rel="stylesheet">
<link href="assets/css/styles.css" rel="stylesheet">
<link href="assets/css/custom.css" rel="stylesheet">

<!--Icons-->
<script src="assets/js/lumino.glyphs.js"></script>

<!--[if lt IE 9]>

<script src="js/html5shiv.js"></script>

<script src="js/respond.min.js"></script>

<![endif]-->

<script src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="assets/js/jquery-ui-1.9.2-min.js"></script>
<script type="text/javascript" src="assets/js/jquery.form.js"></script>
<script type="text/javascript" src="assets/js/jquery-jtemplates.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-dialog.min.js"></script>
<script src="assets/js/validator.min.js"></script>
<script src="assets/js/jquery.toaster.js"></script>

</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>MySQL表结构兼容性校验</span></a>
			</div>
							
		</div><!-- /.container-fluid -->
	</nav>
		
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
		
			<li class="active"><a href="javascript:void(0);" onclick="menuActive(this, 'page/main')"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg> 首页</a></li>
			
			<!-- <li class="parent">
				<a href="#">
					<span data-toggle="collapse" href="#monitor-sub-item"><svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg></span> 监控 
				</a>
				<ul class="children collapse" id="monitor-sub-item">
					<li>
						<a href="javascript:void(0);" onclick="menuActive(this, 'page/monitor/mycatserver')">
							<svg class="glyph stroked monitor"><use xlink:href="#stroked-monitor"></use></svg> MyCat服务监控
						</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="menuActive(this, 'page/monitor/mycatnode')">
							<svg class="glyph stroked monitor"><use xlink:href="#stroked-monitor"></use></svg> MyCat节点监控
						</a>
					</li>
				</ul>
			</li>
			
			<li><a href="javascript:void(0);" onclick="menuActive(this, 'page/cmd')"><svg class="glyph stroked app-window"><use xlink:href="#stroked-app-window"></use></svg> 命令控制台</a></li>
			
			<li><a href="javascript:void(0);" onclick="menuActive(this, 'page/notfound')"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg> NOT FOUND</a></li> -->
		</ul>

	</div><!--/.sidebar-->
		
	<div id="main-container" class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
		
		<div id="main-panel">
		</div>
		
	</div>	<!--/.main-->

	<script>

		!function ($) {
		    $(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		        $(this).find('em:first').toggleClass("glyphicon-minus");      
		    }); 
		    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
		
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			// 加载首页
			$('#main-panel').load('page/main.jsp', function(response, status,  XMLHttpRequest) {
				if(status == "error") {
					$('#main-panel').html(response);
				}
			});
			
		});
		
		// 左边栏菜单被激活的动作
		function menuActive(obj, contentPath) {
			
			var _this = $(obj);
			// 之前active菜单变成unactive
			$('.menu').find('li.active').removeClass('active');
			// 当前点击的菜单变成active
			_this.parent().addClass('active');
			// 加载页面
			var loadUrl = contentPath + ".jsp";
			$('#main-panel').load(loadUrl, function(response, status,  XMLHttpRequest) {
				if(status == "error") {
					$('#main-panel').html(response);
				}
			});
			
		}
	</script>	
</body>



</html>

