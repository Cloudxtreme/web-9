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
	<script src="/js/deploy.js"></script>
</head>
<body>
	<%@ include file="../header.jsp" %>

<form class="form-inline">

<fieldset>
	<legend>Automation</legend>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
             <div class="form-group">
                 
             </div>
            
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
		<input id="reset" type="reset" value="Cancel" />
		<button id="backup" class="" type="button">Backup</button>
		<button id="restart" class="" type="button">Restart</button>
		<button id="merge" class="" type="button">Merge</button>
		<button id="start" class="" type="button">Start</button>
		<button id="stop" class="" type="button">Stop</button>
    </div>
</div>
</fieldset>
   	<table width="100%">
		<tr>
			<td>
			<fieldset>
				<legend>Manual</legend>
				<input type="checkbox" name="arguments" value="ant" id="ant" />Ant
				<input type="checkbox" name="arguments" value="mvn" id="mvn" />Maven  
				<input type="checkbox" name="arguments" value="deployment" id="deployment"/>Deployment
				<button id="run" class="" type="button">Run</button>
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
    
    <fieldset>
		<legend>Screen output</legend>
		<label for="connect">Console:</label>
        <button id="connect" class="btn btn-default" type="submit">Connect</button>
        <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect</button>
        <button id="clean">Clean</button>
		<div class="screen">
		<ol id="output">
	
		</ol>
		</div>
	</fieldset>
    
    <script>
    </script>

	<%@ include file="../footer.jsp" %>
</body>
</html>