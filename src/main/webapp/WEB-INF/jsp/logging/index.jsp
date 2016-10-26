<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>System</title>
	<%@ include file="../head.jsp" %>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<br />

		<input id="host" list="hostList" name="host" size="16" />
    	<datalist id="hostList">
			<option value="127.0.0.1">localhost</option>
		</datalist> 
    
	<fieldset>
		<legend>System setup</legend>
    	<button id="personalise" class="btn btn-default" type="button">Personalise</button>
	</fieldset>

	<fieldset>
		<legend>Screen output</legend>
		<pre id="output">
	
		</pre>
	</fieldset>
	
	<pre id="error">
	
	</pre>

	<script>
	jQuery(document).ready(function() {
		
	   var socket = new SockJS('/screen');
	   var stompClient = Stomp.over(socket);
	   
	   stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        stompClient.subscribe('/topic/shell', function (protocol) {
	            $("#output").append(JSON.parse(protocol.body).response + "\r\n");
	        });
	    });
		    
		$.getJSON('/v1/config/host.json',function(data) {
			$.each(data,function(key, val) {
				$("#hostList").append('<option value="' + val + '">' + val	+ "</option>");
			});
		});
		

	</script>
	<%@ include file="../footer.jsp" %>
</body>
</html>