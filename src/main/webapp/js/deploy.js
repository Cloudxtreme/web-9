/**
 * 
 */

jQuery(document).ready(
		function() {

			$.getJSON('/v1/config/group.json',

			function(data) {

				$.each(data,
						function(key, val) {
							$("#group").append(
									'<option value="' + val + '">' + val
											+ "</option>");
						});

			});

			$.getJSON('/v1/config/envionment.json',

			function(data) {

				$.each(data,
						function(key, val) {
							$("#envionment").append(
									'<option value="' + val + '">' + val
											+ "</option>");
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
						var color = "";
						if (envionment == "testing") {
							color = "blue";
						}
						if (envionment == "production") {
							color = "red";
						}
						$("#projectItem").html("");

						$.getJSON('/v1/config/project/' + group + '/'
								+ envionment + '.json',

						function(data) {

							$.each(data, function(key, val) {
								$("#projectList").append(
										'<option value="' + val + '"/>');
								$("#projectItem").append(
										'<li><span style="color:' + color
												+ '">' + val
												+ '<span>&nbsp;<a href="' + url
												+ '/' + val
												+ '/">deploy</a></li>');

							});

						});

					});

			$("form").submit(function(event) {

				$("#deploy").submit();
				event.preventDefault();
			});

			jQuery("#project").click(function() {
				jQuery("#project").val("");
			});
			jQuery("#ant").click(function() {
				$("input[value=mvn]").prop("checked", false);
				$("input[value=deployment]").prop("checked", false);

				$("input[value=tgz]").prop("checked", true);
				$("input[value=deploy]").attr("checked", "checked");
				$("input[value=deployment]").prop("checked", false);
			});
			jQuery("#mvn").click(function() {
				$("input[value=ant]").prop("checked", false);
				$("input[value=deployment]").prop("checked", false);

				$("input[value=tgz]").prop("checked", true);
				$("input[value=pull]").prop("checked", true);
				$("input[value=install]").prop("checked", true);
				$("input[value=deploy]").prop("checked", true);
				$("input[value=deployment]").prop("checked", false);

			});
			jQuery("#deployment").click(function() {
				$("input[value=ant]").prop("checked", false);
				$("input[value=mvn]").prop("checked", false);
			});
			jQuery("#logging").click(function() {
				var group = $("#group").val();
				var envionment = $("#envionment").val();
				var project = $("#project").val();
				var url = "/logging/watch/" + group + "/" +envionment+"/" + project+"/";
				
				var win = window.open(url, 'logging');
				  win.focus();
			});
			
			
		    
			
		});
jQuery.readyException = function(error) {
	// "error" is thrown from any ready handler

};