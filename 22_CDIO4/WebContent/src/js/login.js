$(document).ready(function() {
	
	showLoginPage();

	// Logout link
	$(document).on("click", ".logout_link", function(event) {
		event.preventDefault();
		showLoginPage();
	});
	
	/*
	 * Submit login form
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
						$("#user_dropdown_menu .user_edit_link").hide();
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
				            $("body").html(Mustache.render($(template).html(), data));
				            $("#user_dropdown_menu .user_edit_link").show();
				            switch(data.role) {
				            case "Admin":
				            	$(".top_nav_role").addClass("role_admin");
				            	getRoleTemplate("src/html/role_privilege/admin_privilege.html");
				            	break;
				            case "Farmaceut":
				            	$(".top_nav_role").addClass("role_pharmacist");
				            	getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html").done(function() {
				            		getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
				            			getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
				            		});
				            	});
				            	break;
				            case "Værkfører":
				            	$(".top_nav_role").addClass("role_foreman");
				            	getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
				            		getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
				            	});
				            	break;
				            case "Laborant":
				            	$(".top_nav_role").addClass("role_labtech");
				            	getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
				            default: 
				            	break;
				            }
				        });
					})
					.fail(function(x) {
						console.log("Fejl i User REST");
					});
					break;
				default: // not logged in
					console.log(data);
				}
			},
			error: function(data){
				console.log("Fejl i Login REST");
			}
		});
	});
});

// Show the login page and focus the id input field.
function showLoginPage() {
	$.get("src/html/login.html", function(template) {
        $("body").html(template);
        $("#login_form input[name=\"id\"]").focus();
    });
}

// Show side navigation depending on the user's role.
function getRoleTemplate(template) {
	return $.get(template, function(template) {
		$("#side_panel > #side_nav > ul").append(template);
	});
}