<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table">
                <thead>
                <tr>
                    <th>Screen output</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
    </form>
    <script>
    connect();
    
    $.getJSON('/v1/config/group.json',

			function(data) {

				$.each(data,
						function(key, val) {
							$("#group").append(
									'<option value="' + val + '">' + val
											+ "</option>");
						});

			});

    		$("#group").change(function() {
    			$("#envionment").html("");
				$.getJSON('/v1/config/envionment.json',	function(data) {
					$.each(data, function(key, val) {
						$("#envionment").append('<option value="' + val + '">' + val + "</option>");
					});

				});
			}); 
			$("#envionment").change(
					function() {

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
    
    </script>
</div>