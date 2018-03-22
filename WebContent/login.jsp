<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
	//判断是否登陆
	if(session.getAttribute("login")==null){
	}else{
		String login=session.getAttribute("login").toString();
		if(login.equals("yes")){response.sendRedirect("/candyandfeelingstory/index.jsp");}
	}
%>
<!DOCTYPE HTML>
<html>
	<head>
	<title>candy&feeling哒记忆时光机</title>
	
	<link href="css/login.css" rel="stylesheet" type="text/css" media="all"/>
	<link rel="stylesheet" href="css/bootstrap.css">
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	</head>

<body>
	<div class="login">
		<div class="login-top">
			<h1>❤记忆时光机❤</h1>
			<form>
				<input id="lovepass" name="lovepass" type="password" >
	    	</form>
	    	<div class="forgot">
	    		<input type="submit" value="确定" onclick="login()">
	    	</div>
		</div>
		<div class="login-bottom">
			<!-- <h3>candy love for feeling.</h3> -->
		</div>
	</div>
	
	<script src="js/jquery.min.js"></script>
	<script>
		$("#lovepass").keypress(function(event){
    			if(event.which === 13) { 
        			return login();
     			}
		});

		function login(){
			$.ajax({  
	       		url:'login.do?lovepass='+$("#lovepass").val(),type:'post',  
	         	cache:false,processData:false,contentType:false,async:false
	    	}).done(function(res) {
	    		if(res.toString()=="yes"){
	    			var newurl=('http://'+window.location.host+'/candyandfeelingstory/index.jsp').toString();
	    			window.location.href=newurl;
	    		}else{
	    			alert('登陆失败啦呀!同棠哥仔讲哈。');
	    		}
	    	}).fail(function(res) {
	        	alert('登陆失败啦呀!同棠哥仔讲哈。');
	    	});
		}
	</script>
</body>
</html>
