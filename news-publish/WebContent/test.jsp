<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript"src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.min.js"></script>
</head>
<script>
function ajaxUpdate(){
	$.ajax({
		 url:"/news-publish/api/category?format=json",
	     type:"PUT",
	     dataType:"json",
	     data:{
	          "id" :"15",
	          "cName" : "121414",
	     },
	     success:function(requestData){
	     }
	});
}
function ajaxDelete(){
	$.ajax({
	    url:"/news-publish/api/category?format=json",
	    type:"delete",
	    dataType:"json",
	    data:{
	         "id" :"16",
	    },
	    success:function(requestData){
	    }
	});
}
</script>
<body>
    <input type="button" value="更新"  onclick="ajaxUpdate()"/>
    
    <input type="button" value="删除"  onclick="ajaxDelete()"/>
    
    
	<p>POST</p>
	<form action="/news-publish/api/category" method="post">
	       <input type="text" name="cName" >
	       <input type="submit" value="添加">
	</form>
	
	<p>更新</p>
    <form action="/news-publish/api/category" method="put">
           <input type="text" name="cId" >
           <input type="text" name="cName" >
           <input type="submit" value="更新">
    </form>
    
    <p>P删除</p>
    <form action="/news-publish/api/category" method="delete">
           <input type="text" name="cId" >
           <input type="submit" value="删除">
    </form>
    
	
</body>
</html>