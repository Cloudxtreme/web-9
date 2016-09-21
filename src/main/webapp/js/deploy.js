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

						var str = $("#envionment option:selected").text();

						$.getJSON('/v1/config/project/' + str + '.json',

						function(data) {

							$.each(data, function(key, val) {
								$("#projectList").append(
										'<option value="' + val + '"/>');
							});

						});

					});

			
			$("form").submit(function(event) {
				var group = $("#group").val();
				var env = $("#envionment").val();
				var prj = $("#project").val();
				// $("#deploy").attr("action","/deploy/"+group+"/"+env+"/"+prj+"/");
				// alert('the action is: ' + $("#deploy").attr('action'));
				$("#deploy").submit();
				event.preventDefault();
			});

		})

jQuery.readyException = function(error) {
	// "error" is thrown from any ready handler

};