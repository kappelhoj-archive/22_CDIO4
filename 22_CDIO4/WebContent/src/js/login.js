$(document).ready(function() {
	
	showLoginPage();

	// Logout link
	$(document).on("click", ".logout_link", function(event) {
		event.preventDefault();
		showLoginPage();
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit login form
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		
		userLogin($(this).serializeJSON()).done(function(data) {
			showFrontPage(data);
		}).fail(function(data) {
			console.log("Fejl i Login REST");
		});
	});
	
	// Submit new password on login form
	$(document).on("submit", "#login_new_pass_form", function(event) {
		event.preventDefault();
		$("input[name=\"repeat_password\"]").prop('disabled', true);
		
		var userId = $("input[name=\"id\"]").val();
		userLoginNewPass($(this).serializeJSON()).done(function(data) {
			$("input[name=\"repeat_password\"]").prop('disabled', false);
			$("#login .alert").remove();
			$(".has-success").removeClass("has-success");
			var splitData = data.split(": ");
			$("#login_new_pass_form").find(".form-group:last").prepend("<div class=\"alert alert-danger\" role=\"alert\">" + splitData[1] + "</div>");
			showRestMessage(data, function() { return redirectToFrontPage(userId) });
		}).fail(function(data) {
			console.log("Fejl i Login REST");
		});
	});
});

/*
 * Functions
 * */

function redirectToFrontPage(userId) {
	getUser(userId).done(function(data) {
		$.get("src/html/master.html", function(template) {
			$("#login").remove();
            $("body").append(Mustache.render(template, data));
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
}

function showFrontPage(data) {
	var splitData = data.split(": ");
	var userId = $("#login_form input[name=\"id\"]").val();
	$("#login .alert").remove();
	switch(splitData[0]) {
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
		break;
	case "new_login":		
		showNewLoginPage(userId);
		break;
	case "true_login":
		redirectToFrontPage(userId);
		break;
	default: // not logged in
		$("#login").find(".form-group:last").prepend("<div class=\"alert alert-danger\" role=\"alert\">" + splitData[1] + "</div>");
	}
}

// Show the login page and focus the id input field.
function showLoginPage() {
	$.get("src/html/login.html", function(template) {
        $("body").html(template);
        $("#login_form input[name=\"id\"]").focus();
    });
}

function showNewLoginPage(userId) {
	$.get("src/html/login_new_pass.html", function(template) {
        $("body").html(template);
        $("#login_new_pass_form input[name=\"id\"]").val(userId);
        validateLoginNewPass();
    });
}

// Show side navigation depending on the user's role.
function getRoleTemplate(template) {
	return $.get(template, function(template) {
		$("#side_panel > #side_nav > ul").append(template);
	});
}

/*
 * REST functions
 * 
 */

function userLogin(form) {
	return $.ajax({
		url : 'rest/login/user',
		type : 'POST',
		contentType : "application/json",
		data : form
	});
}

function userLoginNewPass(form) {
	return $.ajax({
		url : 'rest/login/new_password',
		type : 'PUT',
		contentType : "application/json",
		data : form
	});
}