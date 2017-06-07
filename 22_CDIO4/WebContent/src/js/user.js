$(document).ready(function() {
	
	/** 
	 * Links
	 * **/
	
	// Vis min bruger link
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = "1";
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
	});
	
	// Vis alle brugere link
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		showUserListPage();
	});
	
	// Vis alle brugere link
	$(document).on("click", ".user_create_link", function(event) {
		event.preventDefault();
		
		$.get("src/html/user/user_create.html", function(template) {
            $("#content").html(template);		            
        });
	});
	
	/** 
	 * Form submits
	 * **/
	$(document).on("submit", "#user_create_form", function(event) {
		event.preventDefault();
		var userId = $("#user_id").val();
		getUser(userId).done(function(r) {
			$.ajax({
				url : 'rest/user/login-user',
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
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
		
		
	});
	
});

function showUserListPage() {
	getUsers().done(function(data) {
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
		console.log("Fejl!");
	});
}

function getUser(userId) {
	return $.ajax({
		url : "rest/user/get-user",
		type : "POST",
		data: userId,
		contentType: "application/json"
	});
	
}

function getUsers() {
	return $.ajax({
		url : "rest/user/get-users",
		type : "GET",
		contentType: "application/json"
	});
}