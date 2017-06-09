$(document).ready(function() {

	$("body").load("src/html/login.html", function() {
		$("#login_form input[name=\"id\"]").focus();
	});

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
				switch(data) {
				case "super_login":
					$.get("src/html/master.html", function(template) {
						$("body").html(template);
						$(".top_nav_role").addClass("role_super");
						$(".role_super").text("Super");
						$(".top_nav_name").text("admin");
						
						getRoleTemplate("src/html/role_privilege/admin_privilege.html").done(function() {
							getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html").done(function() {
								getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
									getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
								});
							});
						  });
					});
					console.log(data);
					break;
				case "new_login":
					$.get("src/html/login_new_pass.html", function(template) {
			            $("body").html(template)		            
			        });
					console.log(data);
					break;
				case "true_login":
					var userId = $("#login_form input[name=\"id\"]").val();
					getUser(userId).done(function(data) {
						$.get("src/html/master.html", function(template) {
				            $("body").html(Mustache.render($(template).html(), data))
				            switch(data.role) {
				            case "Admin":
				            	$(".top_nav_role").addClass("role_admin");
				            	getRoleTemplate("src/html/role_privilege/admin_privilege.html");
				            	break;
				            case "Farmaceut":
				            	$(".top_nav_role").addClass("role_pharmacist");
				            	getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html");
				            	getRoleTemplate("src/html/role_privilege/foreman_privilege.html");
				            	getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
				            	break;
				            case "Værkfører":
				            	$(".top_nav_role").addClass("role_foreman");
				            	getRoleTemplate("src/html/role_privilege/foreman_privilege.html");
				            	getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
				            	break;
				            case "Laborant":
				            	$(".top_nav_role").addClass("role_labtech");
				            	$.get("src/html/role_privilege/labtech_privilege.html", function(template) {
				            		$("#side_panel").html(template);
				            	});
				            default: 
				            	break;
				            }
				        });
					})
					.fail(function(x) {
						console.log("Fejl!");
					});
					console.log(data);
					break;
				default:
					console.log(data);
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
});

function getRoleTemplate(template) {
	return $.get(template, function(template) {
		$("#side_panel > #side_nav > ul").append(template);
	});
}