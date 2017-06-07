$(document).ready(function() {

	$("body").load("src/html/login.html");

	/*
	 * On sumbit login post request
	 * */
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/login/login-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				if(data == "new_log_in") {
					$.get("src/html/login_new_pass.html", function(template) {
			            $("body").html(template)		            
			        });
				}
				else if(data == "super_admin" || data == "logged_in") {		
					var userId = $("#user_id").val();
					getUser(userId).done(function(data) {
						$.get("src/html/master.html", function(template) {
				            $("body").html(Mustache.render($(template).html(), data))
				            switch(data.role) {
				            case "Admin":
				            	$(".top_nav_role").addClass("role_admin");
				            	break;
				            case "Farmaceut":
				            	$(".top_nav_role").addClass("role_pharmacist");
				            	break;
				            case "Værkfører":
				            	$(".top_nav_role").addClass("role_foreman");
				            	break;
				            case "Laborant":
				            	$(".top_nav_role").addClass("role_labtech");
				            	break;
				            }
				        });
					})
					.fail(function(x) {
						console.log("Fejl!");
					});
				}
				else {
					console.log(data);
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
});