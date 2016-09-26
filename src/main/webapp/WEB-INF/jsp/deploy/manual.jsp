<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <!-- <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
    <!-- <link href="/main.css" rel="stylesheet"> -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
<title>Log output</title>
<%-- <%@ include file="head.jsp" %> --%>

<script src="/js/deploy.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<%@ include file="deploy.jsp" %>
	
<pre>
${output}
</pre>
	<%@ include file="footer.jsp" %>
</body>
</html>