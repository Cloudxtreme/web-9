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
		<label for="host">Name server</label>
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

        
 -->
 	<label for="domain">Domain</label>
    <input type="text" id="domain" size="16" class="form-control" value="*" placeholder="Your domain here...">
    
	<fieldset>
		<legend>System setup</legend>
		<button id="any" class="btn btn-default" type="button">ANY</button>
	    <button id="a" class="btn btn-default" type="button">A</button>
	    <button id="cname" class="btn btn-default" type="button">CNAME</button>
	    <button id="MX" class="btn btn-default" type="button">MX</button>
	    <button id="NS" class="btn btn-default" type="button">NS</button>
	    <button id="TXT" class="btn btn-default" type="button">TXT</button>
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
		
		jQuery("#any").click(function() {
			shell("any");
		});
		jQuery("#a").click(function() {
			shell("a");
		});
		jQuery("#mx").click(function() {
			shell("mx");
		});
		jQuery("#cname").click(function() {
			shell("cname");
		});
		jQuery("#ns").click(function() {
			shell("ns");
		});
		jQuery("#txt").click(function() {
			shell("txt");
		});
		jQuery("#free").click(function() {
			shell("free -m");
		});
		
		jQuery("#ipaddr").click(function() {
			shell("/usr/sbin/ip addr");
		});
		
		jQuery("#route").click(function() {
			shell("/usr/sbin/ip route");
		});
		jQuery("#ss").click(function() {
			shell("/usr/sbin/ss -ntlp");
		});
		jQuery("#iptables").click(function() {
			shell("/usr/sbin/iptables-save");
		});
		jQuery("#history").click(function() {
			shell("history");
		});
		
		function shell(type){
			var host = $("#host").val();
			var domain = $("#domain").val();
			var command = "dig "+ domain + " " + type
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