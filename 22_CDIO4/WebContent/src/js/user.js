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
	            $("#content").html(Mustache.render($(template).html(),data))		            
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
        });
	});
	
	// Link to edit user page
	$(document).on("click", ".user_edit_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl i User REST");
		});
	});
	
	$(document).on("click", ".user_edit_admin_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
//		showUserEditAdminPage(userId);
		showUserListPage();
	});
	
	$(document).on("click", ".user_edit_admin_reset_pw_link", function(event) {
		event.preventDefault();
		var userId = $("input[name=\"id\"]").val();
		resetPassword(userId).done(function(data) {
			console.log(data);
			showRestMessage(data, function() { return showUserEditAdminPage(userId) })
			
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
	
});

/*
 * Functions
 * */

function showUserEditAdminPage(userId) {
	getUser(userId).done(function(data) {
		$.get("src/html/user/user_edit_admin.html", function(template) {
            $("#content").html(Mustache.render($(template).html(),data))		            
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

/*
 * REST functions
 * */

function resetPassword(userId) {
	return $.ajax({
		url : 'rest/login/reset_password',
		type : 'POST',
		contentType : "application/json",
		data : userId
	});
}

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