<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SSL</title>
	<%@ include file="../head.jsp" %>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<br />
	<fieldset>
		<legend>SSL Check</legend>
		<input id="host" name="host" size="16" placeholder="Your host here..."/>
		<input id="port" name="port" size="2" value="443" />
    	<button id="alpn" name="alpn" type="button">ALPN</button>
    	<button id="alpn" name="alpn" type="button">NPN</button>
    	<button id="dns" name="dns" type="button">DNS</button>
    
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
	        //console.log('Connected: ' + frame);
	        stompClient.subscribe('/topic/shell', function (protocol) {
	            $("#output").append("" + JSON.parse(protocol.body).response + "\r\n");
	        });
	    });

		
		jQuery("#alpn").click(function() {
			
			var host = $("#host").val();
			var port = $("#port").val();
			var command =  String.format("openssl s_client -alpn h2 -servername %s -connect %s:%d < /dev/null | grep 'ALPN'", host, host, port) ;
			var protocol = {
					'request': command
			};

			//console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");
			$.ajax({
		           type: "POST",
		           url: "/v1/system/shell/localhost.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (data) {
		               if (data.true) {
		            	   $('#error').html( "OK" );
		               } else {
		            	   $('#error').html( "没有数据" );
		               }
		           }
		       });
		});
		jQuery("#dns").click(function() {
			
			var host = $("#host").val();
			var port = $("#port").val();
			var command =  String.format("openssl s_client -servername %s -connect %s:%d | openssl x509 -noout -text | grep DNS", host, host, port) ;
			var protocol = {
					'request': command
			};

			//console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");
			$.ajax({
		           type: "POST",
		           url: "/v1/system/shell/localhost.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (data) {
		               if (data.true) {
		            	   $('#error').html( "OK" );
		               } else {
		            	   $('#error').html( "没有数据" );
		               }
		           }
		       });
		});
		jQuery("#npn").click(function() {
			
			var host = $("#host").val();
			var port = $("#port").val();
			var command =  String.format("openssl s_client -nextprotoneg h2 -servername %s -connect %s:%d | openssl x509 -noout -text | grep DNS", host, host, port) ;
			var protocol = {
					'request': command
			};

			//console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");
			$.ajax({
		           type: "POST",
		           url: "/v1/system/shell/localhost.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (data) {
		               if (data.true) {
		            	   $('#error').html( "OK" );
		               } else {
		            	   $('#error').html( "没有数据" );
		               }
		           }
		       });
		});
				
	});
	</script>
	<%@ include file="../footer.jsp" %>
</body>
</html>