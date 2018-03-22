<%@ page session="true" language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>

<%
	//判断是否登陆
	if(session.getAttribute("login")==null){response.sendRedirect("/candyandfeelingstory/login.jsp");
	}else{
		String login=session.getAttribute("login").toString();
		if(!login.equals("yes")){response.sendRedirect("/candyandfeelingstory/login.jsp");}
	}
%>

<html>
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>candy&feeling哒记忆时光机</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link href="https://fonts.googleapis.com/css?family=Work+Sans:300,400,500,700,800" rel="stylesheet">
	
	<link rel="stylesheet" href="css/animate.css">
	<link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!-- [if lt IE 9] 
	<script src="js/respond.min.js"></script>
	-->

	</head>
	<body>
	
	<!-- 加载图标 -->
	<div class="fh5co-loader"></div>
	
	<!-- 页面主体 -->
	<div id="page">
		<div id="fh5co-aside" style="background-image: url(images/image_1.jpg)">
			<div class="overlay"></div>
			<!-- 添加按钮 -->
			<nav role="navigation"><ul><li><a onclick="addmsg()"><i class="icon-plus"></i></a></li></ul></nav>
			<div class="featured">
				<span>♡好好好爱你哒♡</span>
				<h3><a>棠哥仔🍥同埋Feeling哒记忆时光机</a></h3>
			</div>
		</div>
		
		<div id="fh5co-main-content">
			<div class="fh5co-post" id="contentid"> 
					
			</div>
		</div>
	</div>
	
	<!-- 添加数据modal -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="addModalLabel">添加记忆</h4>
				</div>
				<form id="form_data" name="form_data" enctype="multipart/form-data;charset=utf-8">
				<div class="modal-body">
                	<div class="form-group">
    					<label for="addorder" class="col-sm-2 control-label">序号:</label><input type="text" class="form-control" id="addorder" name="addorder" />
      					<label for="adddate" class="col-sm-2 control-label">日期</label><input type="text" class="form-control" id="adddate" name="adddate" />
      					<label for="addcontent" class="col-sm-2 control-label">内容</label><textarea class="form-control" rows="3" id="addcontent" name="addcontent" style="resize:none"></textarea>
    					<label for="addimg" class="col-sm-2 control-label">图片</label><input type="file" id="addimg" name="addimg" onchange="showimg()" />
      					<img id="show" width="100" src="" style="display: none" />
  					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="add_info()">确定</button>
				</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 修改数据modal -->
	<div class="modal fade" id="edModal" tabindex="-1" role="dialog" aria-labelledby="edModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="edModalLabel">编辑记忆</h4>
				</div>
				<form id="edform_data" name="edform_data" enctype="multipart/form-data;charset=utf-8">
				<div class="modal-body">
                	<div class="form-group">
                		<input type="hidden" id="edid" name="edid" />
                		<input type="hidden" id="edimgtxt" name="edimgtxt" />
    					<label for="edorder" class="col-sm-2 control-label">序号:</label><input type="text" class="form-control" id="edorder" name="edorder" />
      					<label for="eddate" class="col-sm-2 control-label">日期</label><input type="text" class="form-control" id="eddate" name="eddate" />
      					<label for="edcontent" class="col-sm-2 control-label">内容</label><textarea class="form-control" rows="3" id="edcontent" name="edcontent" style="resize:none"></textarea>
    					<label for="edimg" class="col-sm-2 control-label">图片</label><input type="file" id="edimg" name="edimg" onchange="edshowimg()" />
      					<img id="edshow" width="100" src="" style="display: none" />
  					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="edit_info()">确定</button>
				</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 返回到页面顶部按钮 -->
	<div class="gototop js-top"><a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a></div>
	
	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<!-- Stellar Parallax -->
	<script src="js/jquery.stellar.min.js"></script>
	<!-- Main -->
	<script src="js/main.js"></script>
	
	<script>
	$(function(){	
		refreshdata();}
	);
	
	//刷新数据
	function refreshdata(){
		$.ajax({
			type:"get",
            url:"story/lovestory.xml",
            dataType:"xml",
            timeout:1000,
            cache:false,
			success:function(xml){
				var app = [];
                $(xml).find("memory").each(function(i){
					var id = $(this).children("id").text();
					var order = $(this).children("order").text();
					var img = $(this).children("img").text();
                    var content = $(this).children("content").text();
					var date = $(this).children("date").text();
					var str="";var arr=new Array();arr=content.split(';');
					for(var i=0;i<arr.length;i++){if(i!=arr.length-1){str+="✓"+arr[i]+";<br />"}else{str+="✓"+arr[i];}}
					app.push("<!--"+id+"-->"
					+"<div class=\"fh5co-entry padding\" style=\"padding: 20px;\" id=\""+id+"\">"
					+"<img src=\"images/"+img+"\" >"
					+"<div>"
					+"<span class=\"fh5co-post-date\">"+order+"</span>"
					+"<h3 style=\"line-height:30px; margin:0 0 10px 0\">"
					+str
					+"</h3>"
					+"<p style=\"margin-bottom: 10px;\">"+date+"</p>"
					+"<button type=\"button\" class=\"btn btn-primary\" id=\"edit\" onclick=\"editmsg("+id+")\">编辑</button><button type=\"button\" class=\"btn btn-warning\" onclick=\"delmsg("+id+")\">删除</button>"
					+"</div></div>");
				});
				
				for(var x=parseInt(app.length)-parseInt(1);x>=0;x--){$("#contentid").append(app[x]);}
				$("#contentid").append("<footer><div>&copy; 2018 The love story about candy and feeling</div></footer>");
            }
		});
	}
	
	//打开添加modal
	function addmsg(){
		$("#addorder").val("");$("#adddate").val("");$("#addcontent").val("");$("#addimg").val("");$("#show").css("display","none");$('#addModal').modal({keyboard: true});
	}
	
	//打开添加编辑
	function editmsg(ids){
		$.ajax({
			type:"get",url:"story/lovestory.xml",dataType:"xml",timeout:1000,cache:false,
			success:function(xml){var app = [];
                $(xml).find("memory").each(function(i){
					var id = $(this).children("id").text();
					if(ids==id){
						var order = $(this).children("order").text();var img = $(this).children("img").text();var content = $(this).children("content").text();var date = $(this).children("date").text();
						$("#edid").val(ids);$("#edimgtxt").val(img);$("#edorder").val(order);$("#eddate").val(date);$("#edcontent").val(content);document.getElementById('edshow').style.display="inline";document.getElementById('edshow').src="images/"+img;$('#edModal').modal({keyboard: true});
					}
                });
            },error:function(){alert('记忆编辑失败啦呀!');}
		});	
	}
	
	//添加modal图片展示
	function showimg(){
		var r= new FileReader();f=document.getElementById('addimg').files[0];
        r.readAsDataURL(f);r.onload=function(e){document.getElementById('show').style.display="inline";document.getElementById('show').src=this.result;};
	}
	
	//编辑modal图片展示
	function edshowimg(){
		var r= new FileReader();f=document.getElementById('edimg').files[0];
        r.readAsDataURL(f);r.onload=function(e){document.getElementById('edshow').style.display="inline";document.getElementById('edshow').src=this.result;};
	}
	
	//添加
	function add_info(){
        var formData = new FormData();
    	formData.append("addorder", encodeURIComponent($("#addorder").val()));
    	formData.append("adddate", encodeURIComponent($("#adddate").val()));
    	formData.append("addcontent", encodeURIComponent($("#addcontent").val()));
    	formData.append("addimg", $('#addimg')[0].files[0]);
    	$.ajax({  
       		url:'addmsg.do',type:'post',data:formData,  
         	cache:false,processData:false,contentType:false,async:false
    	}).done(function(res) {
    		$("#addModal").modal('hide');
    		$("#contentid").empty();
    		refreshdata();
    	}).fail(function(res) {
        	alert('记忆添加失败啦呀!同棠哥仔讲哈。');
    	});
    }
	
	//编辑
	function edit_info(){
        var formData = new FormData();
        formData.append("edid", encodeURIComponent($("#edid").val()));
        formData.append("edimgtxt", encodeURIComponent($("#edimgtxt").val()));
    	formData.append("edorder", encodeURIComponent($("#edorder").val()));
    	formData.append("eddate", encodeURIComponent($("#eddate").val()));
    	formData.append("edcontent", encodeURIComponent($("#edcontent").val()));
    	formData.append("edimg", $('#edimg')[0].files[0]);
    	$.ajax({  
       		url:'edmsg.do',type:'post',data:formData,  
         	cache:false,processData:false,contentType:false,async:false
    	}).done(function(res) {
    		$("#edModal").modal('hide');
    		$("#contentid").empty();
    		refreshdata();
    	}).fail(function(res) {
        	alert('记忆添加失败啦呀!同棠哥仔讲哈。');
    	});
    }
	
	//删除
	function delmsg(ids){
		var msg = "素不素要删除这条记忆呀？"; 
 		if (confirm(msg)==true){ 
  			$.ajax({url:'delmsg.do?id='+ids,type:'post',cache:false,processData:false,contentType:false,async:false
    		}).done(function(res) {
    		alert('记忆删除成功啦呀!');$("#contentid").empty();
    		$.ajax({
    			type:"get",url:"story/lovestory.xml",dataType:"xml",timeout:1000,cache:false,
    			success:function(xml){
    				var app = [];
                    $(xml).find("memory").each(function(i){
    					var id = $(this).children("id").text();
    					if(ids!=id){	
    						var order = $(this).children("order").text();var img = $(this).children("img").text();
                        	var content = $(this).children("content").text();var date = $(this).children("date").text();
    						var str="";var arr=new Array();arr=content.split(';');
    						for(var i=0;i<arr.length;i++){if(i!=arr.length-1){str+="✓"+arr[i]+";<br />"}else{str+="✓"+arr[i];}}
    						app.push("<!--"+id+"-->"+"<div class=\"fh5co-entry padding\" style=\"padding: 20px;\" id=\""+id+"\">"
    						+"<img src=\"images/"+img+"\" >"+"<div>"+"<span class=\"fh5co-post-date\">"+order+"</span>"
    						+"<h3 style=\"line-height:30px; margin:0 0 10px 0\">"+str+"</h3>"+"<p style=\"margin-bottom: 10px;\">"+date+"</p>"
    						+"<button type=\"button\" class=\"btn btn-primary\" id=\"edit\" onclick=\"editmsg("+id+")\">编辑</button><button type=\"button\" class=\"btn btn-warning\" onclick=\"delmsg("+id+")\">删除</button>"
    						+"</div></div>");
    					}
    				});
    				for(var x=parseInt(app.length)-parseInt(1);x>=0;x--){$("#contentid").append(app[x]);}
    				$("#contentid").append("<footer><div>&copy; 2018 The love story about candy and feeling</div></footer>");
                }
    		});
    		}).fail(function(res) {
        	alert('记忆删除失败啦呀!同棠哥仔讲哈。');
    		});
 		}else{ 
  			return false; 
 		}
	}
	
	</script>
	</body>
</html>

