<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>

<script src="/jquery-3.1.0.min.js"></script>
<!-- <script src="/jquery-3.1.0.min.map"></script> -->

<!-- <script src="https://ajax.aspnetcdn.com/ajax/jquery.ui/1.11.4/jquery-ui.min.js"></script>
<link rel="Stylesheet" href="https://ajax.aspnetcdn.com/ajax/jquery.ui/1.11.4/themes/redmond/jquery-ui.css" /> -->
</head>
<body>

	<form id="deploy" action="">

	<select id="group">
		<option value="">Group</option>
	</select> 
		<select id="envionment">
			<option value="">Envionment</option>
		</select> 
		<input id="project" list="projectList" name="project" value="${project}">
		<datalist id="projectList">
			<!-- <option value="www.example.com"> -->
		</datalist>
		<input type="submit" id="submit" value="Deploy" />
		<fieldset>
			<legend>Command</legend>
			<input type="checkbox" name="animal" value="restart" />Restart <input type="checkbox" name="animal" value="merge" />Merge <input type="checkbox" name="animal" value="Bird" />Birds

		</fieldset>
<!-- 
		<fieldset>
			<legend>Logging</legend>
			<iframe id="log" src="" width="100%" height="100%">
		</iframe>
 -->
	</form>

	<script>
	
	
	jQuery(document).ready(function() {
		
		$.getJSON('/v1/config/group.json', 

			function(data) {
					
				$.each(data, function(key,val) {
			   	$("#group").append('<option value="' + val + '">'+val+"</option>");
			});
				 
		});

		$.getJSON('/v1/config/envionment.json', 

				function(data) {
						
					$.each(data, function(key,val) {
				   	$("#envionment").append('<option value="' + val + '">'+val+"</option>");
				});
					 
			});
		
		$( "#envionment" ).change(function() {
			  
			
			    var str = $( "#envionment option:selected" ).text();
			  
			$.getJSON('/v1/config/project/'+str+'.json', 
							
			function(data) {
				
			  $.each(data, function(key,val) {
			    	$("#projectList").append('<option value="' + val + '"/>');
			  });
			 
			});
		
		});
		
		//jQuery( "#submit" ).click(function() {
		$( "form" ).submit(function( event ) {
			var group = $("#group").val();
			var env = $("#envionment").val();
			var prj = $("#project").val();
			$("#deploy").attr("action","/deploy/"+group+"/"+env+"/"+prj+"/");
			//alert('the action is: ' + $("#deploy").attr('action'));
			$("#deploy").submit();
			event.preventDefault();
		});
		
		
	})
	
	jQuery.readyException = function(error) {
  		// "error" is thrown from any ready handler
  		
	};
	
	</script>

</body>
</html>