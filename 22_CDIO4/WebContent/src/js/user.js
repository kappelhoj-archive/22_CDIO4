$(document).ready(function() {
	
	/* 
	 * Links
	 * */
	
	// Link to edit user (logged in) page
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = $(".top_nav_userid").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data));
	            showInitials();
	            validateUser("#user_edit_form");
	        });
		})
		.fail(function(x) {
			console.log("Fejl i User REST");
		});
	});
	
	// Link to list of users page
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		showUserListPage();
	});
	
	// Link to create user page
	$(document).on("click", ".user_create_link", function(event) {
		event.preventDefault();
		$.get("src/html/user/user_create.html", function(template) {
            $("#content").html(template);
            showInitials();
            validateUser("#user_create_form");
        });
	});
	
	// Link to edit user page
	$(document).on("click", ".user_edit_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
		showUserEditAdminPage(userId);
		
	});
	
	$(document).on("click", ".user_edit_admin_reset_pw_link", function(event) {
		event.preventDefault();
		var userId = $("input[name=\"id\"]").val();
		resetPassword(userId).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() })
			
		})
		.fail(function(x) {
			console.log("Fejl i User REST");
		});
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create user form
	$(document).on("submit", "#user_create_form", function(event) {
		event.preventDefault();
		createUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});		
	});
	
	// Submit edit user form
	$(document).on("submit", "#user_edit_form", function(event) {
		event.preventDefault();
		updateUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	// Submit edit user (admin) form
	$(document).on("submit", "#user_edit_admin_form", function(event) {
		event.preventDefault();
		updateUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	// Submit edit user (admin) form
	$(document).on("submit", "#user_edit_password_form", function(event) {
		event.preventDefault();
		var userId = $("#user_edit_password_form input[name=\"id\"]").val();
		var $password = $("#user_edit_password_form input[name=\"password\"]");
		
		// Remove error message
		$("#password-error").remove();
		
		changePassword($(this).serializeJSON()).done(function(data) {
			var splitData = data.split(": ");
			switch(splitData[0]) {
			case "success":
				showNewLoginPage(userId);
			default:
				$password.parent().append("<div id=\"password-error\" class=\"error form-control-feedback\">Forkert password.</div>");
				$password.addClass("form-control-danger");
				$password.parent().addClass("has-danger");
				console.log(splitData[1]);
			}
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	
});

/*
 * Functions
 * */

function showInitials() {
	$("input[name=\"ini\"]").focus(function() {
    	var iniValue = $(this).val();
    	var iniAuto = generateInitials($("input[name=\"name\"]").val());
    	if (iniValue ==  "") {
    		$(this).val(iniAuto);
    	}
    });
}

function showUserEditAdminPage(userId) {
	getUser(userId).done(function(data) {
		$.get("src/html/user/user_edit_admin.html", function(template) {
            $("#content").html(Mustache.render($(template).html(),data));
            $(".custom-select").find("option[value=\"" + data.role + "\"]").attr("selected", true);
            showInitials();
            validateUser("#user_edit_admin_form");
        });
	})
	.fail(function(x) {
		console.log("Fejl i User REST");
	});
}

function showUserListPage() {
	getUserList().done(function(data) {
		$.get("src/html/user/user_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data){
				$.get("src/html/user/user_list_row.html", function(template) {
		            $("#user_list .table tbody").append(Mustache.render($(template).html(),data))
		        });
			});
        });
	})
	.fail(function(x) {
		console.log("Fejl i User REST");
	});
}

//generate initials from a name.
function generateInitials(name){
	var initials="";
	splitName=name.split(" ");
	if(splitName.length<3){
		//Add the two first letters of every name.
		for(var i=0;i<splitName.length;i++){
			
			initials+=splitName[i].substring(0,2);
		}
	}
	else{
		//Add first letter of the first name. 
		initials+=splitName[0].substring(0,1);
		
		//Make sure the initials length match:
		var modifier;
		if(splitName.length==3){
			modifier=2;
		}
		else{
			modifier=3;
		}
		
		//Add the first letter of the last three names, or two if there are 3 names.
		for(var i=splitName.length-modifier;i<splitName.length;i++){
			initials+=splitName[i].substring(0,1);
		}
	}
	return initials
}

/*
 * REST functions
 * */

function createUser(form) {
	return $.ajax({
		url : 'rest/user/create',
		type : 'POST',
		contentType : "application/json",
		data : form
	});
}

function updateUser(form) {
	return $.ajax({
		url : 'rest/user/update',
		type : 'PUT',
		contentType : "application/json",
		data : form
	});
}

function getUser(userId) {
	return $.ajax({
		url : "rest/user/read",
		type : "POST",
		data: userId,
		contentType: "application/json"
	});
	
}

function getUserList() {
	return $.ajax({
		url : "rest/user/read_list",
		type : "GET",
		contentType: "application/json"
	});
}