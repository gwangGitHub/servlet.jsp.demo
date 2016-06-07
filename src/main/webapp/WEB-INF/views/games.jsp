<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> <!-- 改成这个能解决jsp etl表达式$不能解析的问题 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>games</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<!-- <link rel="stylesheet" href="css/mui.min.css"> -->
	<script src="../js/jquery.js" type="text/javascript" charset="utf-8"></script>
	<script>
		$(document).ready(function(){
		    initAreaOption();
		});
	
		function initAreaOption() {
		    $('#game').change(function(){
		        var gameId = $(this).children('option:selected').val();
		        $("#area").empty();
		        $.ajax({
		            dataType: 'json',
		            type : 'post',
		            url : "../area/list",
		            data: {
		            	game: gameId
		            },
		            success : function(data){
		                if(data.success){
		                	var areas = data.areas;
		                	for (var i in areas) {
		                    	$("#area").append("<option value =" + areas[i].id +">" + areas[i].name + "</option>");
		                	}
		                }else {
		                    alert(data.success);
		                }
		            },
		            error:function(XMLHttpRequest ,errMsg){
		                alert("网络连接失败");
		            }
		        });
		    });
		}
	</script>
  </head>
  
  <body>
    	<form action="/Game/list" method="post">
		Game: <select id="game">
				  <option value ="0"></option>
				  	<c:forEach var="game" items="${games}">
				  		<option value ="${game.id}">${game.name}</option>
					</c:forEach>
				</select><br />
		Area : <select id="area"></select><br/>
		pageNum: <input type="text" name="pageNum" /> <br />
		pageSize : <input type="text" name="pageSize" /> <br />
		<input type="submit" value="Search" />
	</form>
  </body>
</html>
