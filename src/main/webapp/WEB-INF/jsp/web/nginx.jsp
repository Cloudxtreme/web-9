<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<form method="post" id="deploy" action="/v1/deploy/manual.json" enctype='application/json'>
		<select id="group" name="group">
			<option value="">-- Group --</option>
		</select> <select id="envionment" name="envionment">
			<option value="">-- Envionment --</option>
		</select> <input id="project" list="projectList" name="project" value="${project}" />
		<datalist id="projectList">
			<!-- <option value="www.example.com"> -->
		</datalist>
		<!-- <input type="submit" id="submit" value="Deploy" /> -->
		<input type="button" id="deploy" value="Deploy" />
		<input type="reset" id="reset" value="Cancel" />
		<table width="100%">
			<tr>
				<td>
				<fieldset>
			<legend>Tools</legend>
			<input type="checkbox" name="arguments" value="ant" id="ant" />Ant
			<input type="checkbox" name="arguments" value="mvn" id="mvn" />Maven  
			<input type="checkbox" name="arguments" value="deployment" id="deployment"/>Deployment
		</fieldset>
				</td>
				<td>
				<fieldset>
			<legend>Arguments</legend>
			
			<input type="checkbox" name="arguments" value="tgz" />Backup
			<input type="checkbox" name="arguments" value="clean" />Clean 
			<input type="checkbox" name="arguments" value="pull" />Pull 
			<input type="checkbox" name="arguments" value="push" />Merge 
			<input type="checkbox" name="arguments" value="install" />Install 
			<input type="checkbox" name="arguments" value="package" />Package
			<input type="checkbox" name="arguments" value="trial" />Trial  
			<input type="checkbox" name="arguments" value="deploy" />Deploy 
			<input type="checkbox" name="arguments" value="restart" />Restart
		</fieldset>
				
				</td>
			</tr>
		</table>
		
		
	</form>
	
	<div id="error">
	</div>
	
	<fieldset>
		<legend>Screen output</legend>
		<div class="screen">
		<ol id="output">
	
		</ol>
		</div>
	</fieldset>
	
	<script>

	</script>