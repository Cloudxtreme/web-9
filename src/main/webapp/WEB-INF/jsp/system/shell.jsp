<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Log output</title>
	<%@ include file="../head.jsp" %>

<script src="/js/deploy.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<br />


    	<select id="host" name="host">
			<option value="">-- Host --</option>
		</select> 

        <label for="command">Command</label>
        <input type="text" id="command" list="logs" size="128" class="form-control" placeholder="Your command here...">

    <button id="run" class="btn btn-default" type="button">Run</button>

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
	        stompClient.subscribe('/topic/shell', function (protocol) {
	            $("#output").append("<li>" + JSON.parse(protocol.body).response + "</li>");
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
		           url: "/v1/system/shell/"+host+".json",
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
	<%@ include file="../footer.jsp" %>
</body>
</html>