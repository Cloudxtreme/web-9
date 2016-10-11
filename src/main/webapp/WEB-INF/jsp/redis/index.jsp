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

    <button id="run" class="btn btn-default" type="button">Run</button>

	<fieldset>
		<legend>Screen output</legend>
		<ol id="output">
	
		</ol>
	</fieldset>
	
<pre id="error">

</pre>

	<script>
	jQuery(document).ready(function() {
		
	  	    
		$.getJSON('/v1/config/host.json',function(data) {
			$.each(data,function(key, val) {
				$("#hostList").append('<option value="' + val + '">' + val	+ "</option>");
			});
		});
		
		jQuery("#run").click(function() {
			
			var host = $("#host").val();
			var db = $("#db").val();
			var keys = $("#keys").val();
			var protocol = {
					'host': host,
					'db': db,
					'keys': keys
			};

			console.log('json: ' + JSON.stringify(protocol));
			$("#output").html("");

			$.ajax({
		           type: "POST",
		           url: "/v1/redis/keys.json",
		           dataType: "json",
		           contentType: 'application/json',
		           data: JSON.stringify(protocol),
		           success: function (data) {
		               if (data) {
		            	   	$.each(data,function(key, val) {
		       					$("#output").append("<li><a href=\"javascript:del('"+ val +"\')\">" + val + '</a></li>');
		       				});
		               } else {
		            	   $('#error').html( "没有数据" );
		               }
		           }
		       });
		});
				
	});

	function del(key) {
		
		var result = confirm('是否删除 Key:'+key);  
		if(!result){
			return;
		}  
		
		var host = $("#host").val();
		var db = $("#db").val();
		var protocol = {
				'host': host,
				'db': db,
				'keys': key
		};

		console.log('json: ' + JSON.stringify(protocol));
		$("#output").html("");

		$.ajax({
	           type: "POST",
	           url: "/v1/redis/del.json",
	           dataType: "json",
	           contentType: 'application/json',
	           data: JSON.stringify(protocol),
	           success: function (data) {
	               if (data) {
	            	   $('#error').html( data );
	               }
	           }
	       });
	}	
	</script>
	<%@ include file="../footer.jsp" %>
</body>
</html>