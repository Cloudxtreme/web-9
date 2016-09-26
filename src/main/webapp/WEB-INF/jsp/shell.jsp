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
        <input type="text" id="command" list="logs" size="128" class="form-control" placeholder="Your command here...">

    <button id="run" class="btn btn-default" type="button">Run</button>
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
		        stompClient.subscribe('/topic/shell', function (proto) {
		            $("#output").append("<li>" + JSON.parse(proto.response).content + "</li>");
		        });
		    });
		    
		$.getJSON('/v1/config/host.json',function(data) {
			$.each(data,function(key, val) {
				$("#host").append('<option value="' + val + '">' + val	+ "</option>");
			});
		});
		
		jQuery("#run").click(function() {
			var command = $("#command").val();
			var host = $("#host").val();
			var protocol = {
					'request': command
			};

			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");

			$.ajax({
		           type: "POST",
		           url: "/v1/shell/run/"+host+".json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (msg) {
		               if (msg.status) {
		            	   $('#error').html( "Sent" );
		               } else {
		                   alert("Cannot add to list !");
		               }
		           }
		       });
		});
	});
	</script>
	<%@ include file="footer.jsp" %>
</body>
</html>