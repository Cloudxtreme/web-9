<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Mail</title>
	<%@ include file="../head.jsp" %>
</head>
<body>
	<%@ include file="../header.jsp" %>
	<br />
		<label for="host">SMTP</label>
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
		<textarea id="text" name="text" rows="10" cols="50">Helloworld!!!</textarea>
		<br />
    	<button id="send" class="btn btn-default" type="button">Send</button>
	<fieldset>
		<legend>Tools</legend>
		<button id="conf" class="btn btn-default" type="button">Config</button>
    	<button id="report" class="btn btn-default" type="button">Report</button>
    	<button id="queue" class="btn btn-default" type="button">Queue</button>
    	<button id="timeout" class="btn btn-default" type="button">Timeout</button>
    	<button id="noroute" class="btn btn-default" type="button">No route host</button>
    	<button id="denied" class="btn btn-default" type="button">Denied</button>
    	<button id="all" class="btn btn-default" type="button">Queue Clean All</button>
    	<button id="deferred" class="btn btn-default" type="button">Queue Clean Deferred</button>
    	<button id="exception" class="btn btn-default" type="button">Queue Clean Exception</button>
	</fieldset>	
	<fieldset>
		<legend>Screen output</legend>
		<div id="status">
	
		</div>
		<div id="output">
	
		</div>
	</fieldset>

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
		
		$.getJSON('/v1/config/mail.json',function(data) {
			$.each(data,function(key, val) {
				$("#hostList").append('<option value="' + val + '">' + val	+ "</option>");
			});
		});
		
		jQuery("#send").click(function() {
			
			var host = $("#host").val();
			var from = $("#from").val();
			var to = $("#to").val();
			var subject = $("#subject").val();
			var text = $("#text").val();
			var protocol = {
					'host': host,
					'from': from,
					'to': to,
					'subject': subject,
					'text': text
			};

			console.log('json: ' + JSON.stringify(protocol));
			$("#status").html("");

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
		
		jQuery("#conf").click(function() {
			shell("postconf -n");
		});
		jQuery("#report").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/log/mail/report.sh | bash");
		});
		jQuery("#queue").click(function() {
			shell("mailq");
		});
		jQuery("#timeout").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/log/mail/timeout.sh | bash");
		});
		jQuery("#noroute").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/log/mail/noroute.sh | bash");
		});
		jQuery("#denied").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/log/mail/denied.sh | bash");
		});
		jQuery("#all").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/mail/postfix/queue.all.sh | bash");
		});
		jQuery("#deferred").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/mail/postfix/queue.deferred.sh | bash");
		});
		jQuery("#exception").click(function() {
			shell("curl -s https://raw.githubusercontent.com/oscm/shell/master/mail/postfix/queue.exception.sh | bash");
		});
		
		function shell(command){
			var host = $("#host").val();
			if(host == ""){
				$('#status').html( "请选择 SMTP 主机！" );
			}
			var protocol = {
					'request': command
			};
			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");
			$.ajax({
	           type: "POST",
	           url: "/v1/system/shell/root/"+host+".json",
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