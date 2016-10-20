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

		<input id="host" list="hostList" name="host" size="16" />
    	<datalist id="hostList">
			<option value="127.0.0.1">localhost</option>
		</datalist> 
		<br />
		<label for="from">From</label>
		<input id="from" list="dbList" name="from" size="32" type="email" required placeholder="Enter a valid email address" /> <br />
		<label for="to">To</label>
		<input id="to" list="dbList" name="to" size="32" type="email" required placeholder="Enter a valid email address"/> <br />
		<label for="subject">Subject</label> 
        <input type="text" id="subject" list="keyList" size="60" class="form-control" value="Hello" placeholder="Your subject here..." required/> <br />
		<textarea id="text" name="text" rows="10" cols="50">
		这里是文本域中的文本 ... ... ... ...
		</textarea>
		<br />
    <button id="send" class="btn btn-default" type="button">Send</button>

	<fieldset>
		<legend>Screen output</legend>
		<div id="status">
	
		</div>
	</fieldset>

	<script>
	jQuery(document).ready(function() {
		
		jQuery("#send").click(function() {
			
			var host = $("#host").val();
			var from = $("#from").val();
			var to = $("#to").val();
			var subject = $("#subject").val();
			var text = $("#text").val();
			var protocol = {
					'from': from,
					'to': to,
					'subject': subject,
					'text': text
			};

			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");

			$.ajax({
		           type: "POST",
		           url: "/v1/email/sendmail.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (json) {
		               if (json.status) {
		            	   $('#status').html( "发送成功" );
		               } else {
		            	   $('#status').html( json.text );
		               }
		           }
		       });
		});
				
	});
	</script>
	<%@ include file="../footer.jsp" %>
</body>
</html>