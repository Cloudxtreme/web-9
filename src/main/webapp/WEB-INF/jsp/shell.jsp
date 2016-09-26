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
	<br />

<form class="form-inline">
    	<select id="host" name="host">
			<option value="">-- Host --</option>
		</select> 

        <label for="command">Command</label>
        <input type="text" id="command" list="logs" class="form-control" placeholder="Your command here...">

    <button id="send" class="btn btn-default" type="submit">Run</button>
</form>


	<fieldset>
		<legend>Screen output</legend>
		<ol id="output">
	
		</ol>
	</fieldset>
	
<pre>
${output}
</pre>


	<script>
	jQuery(document).ready(function() {
		
		   var socket = new SockJS('/screen');
		   var stompClient = Stomp.over(socket);
		   
		   stompClient.connect({}, function (frame) {
		        console.log('Connected: ' + frame);
		        stompClient.subscribe('/topic/shell', function (greeting) {
		            $("#output").append("<li>" + JSON.parse(greeting.body).content + "</li>");
		        });
		    });
		    
		$.getJSON('/v1/config/host.json',function(data) {
			$.each(data,function(key, val) {
				$("#host").append('<option value="' + val + '">' + val	+ "</option>");
			});
		});
	});
	</script>
	<%@ include file="footer.jsp" %>
</body>
</html>