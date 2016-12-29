<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Redis</title>
<script src="/webjars/jquery/jquery.min.js"></script>
<%-- <%@ include file="head.jsp" %> --%>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<br />
	<fieldset>
		<legend>SSL Check</legend>
		<input id="host" name="host" size="16" placeholder="Your host here..."/>
		<input id="port" name="port" size="2" value="443" />
    	<button id="check" name="check" type="button">Check</button>
    
    </fieldset>
	<fieldset>
		<legend>Screen output</legend>
		<ol id="output">
	
		</ol>
	</fieldset>
	
<pre id="error">

</pre>

	<script>
	jQuery(document).ready(function() {
		
		jQuery("#check").click(function() {
			
			var host = $("#host").val();
			var port = $("#port").val();
			var command = "openssl s_client -servername "+ host +" -connect " +host+ ":"+ port;
			
			var protocol = {
					'request': command
			};

			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");
			$.ajax({
		           type: "POST",
		           url: "/v1/system/shell/localhost.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (data) {
		               if (data) {
		            	   	$.each(data,function(key, val) {
		       					$("#output").append("<li>"+ val +"</li>");
		       				});
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