<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>DNS lookup</title>
	<%@ include file="../head.jsp" %>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<fieldset>
		<legend>DNS lookup</legend>
		
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
    	<input type="text" id="domain" size="16" class="form-control" value="" placeholder="Your domain here...">
    		
		<br />
		<button id="any" class="btn btn-default" type="button">ANY</button>
	    <button id="a" class="btn btn-default" type="button">A</button>
	    <button id="cname" class="btn btn-default" type="button">CNAME</button>
	    <button id="mx" class="btn btn-default" type="button">MX</button>
	    <button id="ns" class="btn btn-default" type="button">NS</button>
	    <button id="txt" class="btn btn-default" type="button">TXT</button>
	    <button id="ptr" class="btn btn-default" type="button">PTR</button>
	    <button id="soa" class="btn btn-default" type="button">SOA</button>
	    <button id="srv" class="btn btn-default" type="button">SRV</button>
	    <button id="aaaa" class="btn btn-default" type="button">AAAA</button>
	    <button id="spf" class="btn btn-default" type="button">SPF</button>
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
		    
		$.getJSON('/v1/config/dns.json',function(data) {
			$.each(data,function(key, val) {
				$("#hostList").append('<option value="' + val + '">' + key	+ "</option>");
			});
		});
		
		jQuery("#any").click(function() {
			shell("any");
		});
		jQuery("#a").click(function() {
			shell("a");
		});
		jQuery("#cname").click(function() {
			shell("cname");
		});
		jQuery("#mx").click(function() {
			shell("mx");
		});		
		jQuery("#ns").click(function() {
			shell("ns");
		});
		jQuery("#txt").click(function() {
			shell("txt");
		});
		jQuery("#ptr").click(function() {
			shell("ptr");
		});
		
		jQuery("#soa").click(function() {
			shell("soa");
		});
		
		jQuery("#srv").click(function() {
			shell("srv");
		});
		jQuery("#aaaa").click(function() {
			shell("aaaa");
		});
		jQuery("#spf").click(function() {
			var domain = $("#domain").val();
			shell("domain="+domain+"; cmd=spf.sh; if [ -f $cmd ]; then bash $cmd $domain; else curl -s https://raw.githubusercontent.com/oscm/shell/master/mail/spf.sh > $cmd; fi");
		});
		
		function shell(type){
			var host = $("#host").val();
			if(host != ""){
				host = "@"+host
								
			}
			var domain = $("#domain").val();
			var command = "dig "+ host +" "+ domain + " " + type
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
	           success: function (msg) {
	               if (msg.status) {
	            	   $('#error').html( type );
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