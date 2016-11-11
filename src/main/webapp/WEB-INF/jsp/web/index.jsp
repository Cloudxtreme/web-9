<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Web server</title>
	<%@ include file="../head.jsp" %>
<script src="/js/deploy.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<%@ include file="deploy.jsp" %>
	
<pre>
${output}
</pre>
	<%@ include file="../footer.jsp" %>
</body>
</html>