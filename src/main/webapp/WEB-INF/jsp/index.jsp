<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	<form action="action_page.php">

		<select id="envionment">
			<option value="">Envionment</option>
			<option value="development">development</option>
			<option value="testing">testing</option>
			<option value="production">production</option>
			<option value="stable">stable</option>
			<option value="unstable">unstable</option>
			<option value="alpha">alpha</option>
			<option value="beta">beta</option>
			<option value="release">release</option>
		</select> 
		<input id="project" list="projectList" name="project" value="${project}">
		<datalist id="projectList">
			<!-- <option value="www.example.com"> -->
		</datalist>
		<input type="button" id="submit" value="Deploy" />
		<fieldset>
			<legend>Command</legend>
			<input type="checkbox" name="animal" value="restart" />Restart <input type="checkbox" name="animal" value="merge" />Merge <input type="checkbox" name="animal" value="Bird" />Birds

		</fieldset>

		<fieldset>
			<legend>Logging</legend>

			<iframe id="log" src="" width="100%">
		</iframe>
	</form>

	<script>
	
	
	jQuery(document).ready(function() {
		
		$( "#envionment" ).change(function() {
			  
			
			    var str = $( "#envionment option:selected" ).text();
			  
			$.getJSON('/v1/config/envionment/'+str+'.json', 
							
			function(data) {
				
			  $.each(data, function(key,val) {
			    	$("#projectList").append('<option value="' + val + '"/>');
			  });
			 
			});
		
		});
		
		jQuery( "#submit" ).click(function() {
			var env = $("#envionment").val();
			var prj = $("#project").val();
			  $("#log").attr("src","/deploy/cf88.com/"+env+"/"+prj+"/");
		});
		
		
	})
	
	jQuery.readyException = function(error) {
  		// "error" is thrown from any ready handler
  		
	};
	
	</script>

</body>
</html>