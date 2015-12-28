<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java" contentType="text/html; charset=gb2312"
     pageEncoding="gb2312"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Insert title here</title>
</head>
<body bgcolor="#E6E6FA">
	<font color="green"> 你好，${user.username }，<font size=4>你是第${count}位访问者；</font><font size=3>您的访问时间是：${user.time }!</font> <br>
	<br><td> 在您前面的访问者是:</td></br>
	<table width="60%" border="1" align="left">
<th>姓名信息</th><th>时间信息</th>
<c:forEach items="${map2}" var="node">  
<tr><td><c:out value="${node.key}"></c:out></td>  
 <td><c:out value="${node.value}"></c:out></td></tr> 
</c:forEach>
</table>
</body>
</html>
