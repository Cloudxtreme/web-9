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
<!-- 
		<input id="db" list="dbList" name="db" size="2" />
		<datalist id="dbList">
			<option value="0"/>
			<option value="1"/>
			<option value="2"/>
			<option value="4"/>
			<option value="5"/>
			<option value="6"/>
			<option value="7"/>
			<option value="8"/>
			<option value="9"/>
			<option value="10"/>
			<option value="11"/>
			<option value="12"/>
			<option value="13"/>
			<option value="14"/>
			<option value="15"/>
			<option value="16"/>
		</datalist>

        <label for="keys">Keys</label>
        <input type="text" id="keys" list="keyList" size="64" class="form-control" value="*" placeholder="Your command here...">
 -->
    <button id="version" class="btn btn-default" type="button">Version</button>
    <button id="date" class="btn btn-default" type="button">Date</button>
    <button id="last" class="btn btn-default" type="button">Last</button>
    <button id="who" class="btn btn-default" type="button">Who</button>
    <button id="df" class="btn btn-default" type="button">df</button>
    <button id="ps" class="btn btn-default" type="button">ps</button>
    <button id="free" class="btn btn-default" type="button">free</button>
    


	<fieldset>
		<legend>Screen output</legend>
    	<button id="init" class="btn btn-default" type="button">Init</button>
    	<button id="ssh" class="btn btn-default" type="button">SSH</button>
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
		
		version
		
		jQuery("#version").click(function() {
			shell("cat /etc/issue /etc/centos-release");
		});
		jQuery("#date").click(function() {
			shell("date");
		});
		jQuery("#last").click(function() {
			shell("last");
		});
		jQuery("#who").click(function() {
			shell("who");
		});
		jQuery("#df").click(function() {
			shell("df -h");
		});
		jQuery("#ps").click(function() {
			shell("ps");
		});
		jQuery("#free").click(function() {
			shell("free -m");
		});
		
		function shell(command){
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
		}
	});
	</script>
	<%@ include file="../footer.jsp" %>
</body>
</html>