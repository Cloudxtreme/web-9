<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Deploy </title>
	<%@ include file="../head.jsp" %>
	<script src="/js/logging.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>

<noscript>
<h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2>
    </noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">Console:</label>
                    <button id="connect" class="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
        <!-- 
            <form class="form-inline">
                <div class="form-group">
                    <label for="name">What is your name?</label>
                    <input type="text" id="name" list="logs" class="form-control" placeholder="Your name here...">
                </div>
                <button id="send" class="btn btn-default" type="submit">Send</button>
            </form>
          -->
        </div>
    </div>
    <div class="row">
    	<select id="group" name="group">
			<option value="">-- Group --</option>
		</select> 
		<select id="envionment" name="envionment">
			<option value="">-- Envionment --</option>
		</select> 
		<select id="project" name="project">
			<option value="">-- Project --</option>
		</select> 
		<button id="deploy" class="" type="button">Deploy</button>
		<button id="backup" class="" type="button">Backup</button>
		<button id="restart" class="" type="button">Restart</button>
    </div>
    <div class="row">
        <div class="col-md-12">
        <dl id="output">
		   <dt>Screen output</dt>
		</dl>
        </div>
    </div>
    </form>
    <script>

    jQuery(document).ready(function() {

	    connect();
	    
	 	$.getJSON('/v1/config/group.json',function(data) {
	
			$.each(data,function(key, val) {
				$("#group").append('<option value="' + val + '">' + val	+ "</option>");
			});
	
		});

   		$("#group").change(function() {
   			$("#envionment").html('<option value="">-- Envionment --</option>');
			$.getJSON('/v1/config/envionment.json',	function(data) {
				$.each(data, function(key, val) {
					$("#envionment").append('<option value="' + val + '">' + val + "</option>");
				});

			});
		}); 
		
   		$("#envionment").change(function() {

			var envionment = $("#envionment option:selected")
					.text();

			var group = $("#group").val();
			var env = $("#envionment").val();
			var prj = $("#project").val();
			var url = "/deploy/" + group + "/" + env;

			$("#project").html("");

			$.getJSON('/v1/config/project/' + group + '/'
					+ envionment + '.json',

			function(data) {

				$.each(data, function(key, val) {
					$("#project").append('<option value="' + val + '">' + val
							+ "</option>");
					

				});

			});

		});    
		
		jQuery("#deploy").click(function() {
			jQuery("#greetings").html("");
			
			var group = $("#group").val();
			var envionment = $("#envionment").val();
			var project = $("#project").val();
			var url = "/v1/deploy/config/" + group + "/" + envionment+"/"+project+"/";
			
			var data = $.getJSON(url,
				function(data) {
					if(data.status){
						alert("Success")
					}
				});

		});
		
		jQuery("#backup").click(function() {
			manual(["ant","tgz"]);
		});
		jQuery("#restart").click(function() {
			manual(["ant","restart"]);
		});
			
		function manual(argv){
			
			var group = $("#group").val();
			var envionment = $("#envionment").val();
			var project = $("#project").val();
			
			var protocol = {
					'group': group,
					'envionment': envionment,
					'project': project,
					'arguments': argv
					
			};
			
			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");

			$.ajax({
	           type: "POST",
	           url: "/v1/deploy/manual.json",
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
</div>


	<%@ include file="../footer.jsp" %>
</body>
</html>