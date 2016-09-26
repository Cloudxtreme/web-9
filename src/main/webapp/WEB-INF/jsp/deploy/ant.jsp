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
<title>Ant deployment</title>
<%-- <%@ include file="head.jsp" %> --%>

<script src="/js/deploy.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>

	<fieldset>
		<legend>Tools</legend>
		<select id="group" name="group">
			<option value="">-- Group --</option>
		</select>
		<input id="project" list="projectList" name="project" value="${project}" />
		<datalist id="projectList">
			<!-- <option value="www.example.com"> -->
		</datalist>
		
		<select id="envionment" name="envionment">
			<option value="">-- Envionment --</option>
		</select>
		
		<!-- <input type="submit" id="submit" value="Deploy" /> -->
		<input type="button" id="deploy" value="Deploy" />
		<input type="reset" id="reset" value="Cancel" />			
	</fieldset>

	<fieldset>
		<legend>Arguments</legend>
		<input type="checkbox" name="arguments" value="tgz" />Backup
		<input type="checkbox" name="arguments" value="clean" />Clean
		<input type="checkbox" name="arguments" value="pull" />Pull
		<input type="checkbox" name="arguments" value="push" />Merge
		<input type="checkbox" name="arguments" value="compile" />Compile
		<input type="checkbox" name="arguments" value="install" />Install
		<input type="checkbox" name="arguments" value="package" />Package
		<input type="checkbox" name="arguments" value="trial" />Trial
		<input type="checkbox" name="arguments" value="deploy" />Deploy
		<input type="checkbox" name="arguments" value="restart" />Restart
	</fieldset>

	<div id="error">
	</div>
	
	<fieldset>
		<legend>Screen output</legend>
		<ol id="output">
	
		</ol>
	</fieldset>

<script>
jQuery(document).ready(function() {
	
	$("#group").change(function() {

		var group = $("#group").val();
		var env = $("#envionment").val();
		var prj = $("#project").val();
		var url = "/v1/config/project/" + group + ".json";	
		$.getJSON(url,function(data) {

			$.each(data, function(key, val) {
				$("#projectList").append(
						'<option value="' + val + '"/>');
			});

		});

	});
	
	$("#project").change(function() {

		var group = $("#group").val();
		var envionment = $("#envionment").val();
		var project = $("#project").val();
		var url = "/v1/config/envionment/" + group + "/"+project+".json";
		$.getJSON(url,function(data) {

			$.each(data, function(key, val) {
				$("#envionment").append('<option value="' + val + '"/>');
			});

		});

	});
	
	jQuery("#deploy").click(function() {
		var group = $("#group").val();
		var envionment = $("#envionment").val();
		var project = $("#project").val();
		
		var arguments = (function() {
            var a = [];
            $("input[name='arguments']:checked").each(function() {
                a.push(this.value);
            });
            return a;
        })();
		
		var protocol = {
				'group': group,
				'envionment': envionment,
				'project': project,
				'arguments': arguments
				
		};

		console.log('json: ' + JSON.stringify(protocol));
		$("#output").html("");

		$.ajax({
	           type: "POST",
	           url: "/v1/deploy/ant.json",
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