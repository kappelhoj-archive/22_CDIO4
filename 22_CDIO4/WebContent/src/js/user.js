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
	
	// Rediger bruger link
	$(document).on("click", ".user_edit_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
	});
	
	/** 
	 * Form submits
	 * **/
	$(document).on("submit", "#user_create_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/user/create-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				console.log(data);
				switch(data) {
			    case "success":
			        alert("Brugeren blev oprettet");
			        showUserListPage();
			        break;
			    case "input-error":
			        alert("Input fejl");
			        break;
			    case "id-error":
			        alert("ID fejl");
			    default:
			    	alert("System fejl");
				}
			},
			error: function(data){
				console.log("Fejl!")
				console.log(data);
			}
		});
		
	});
	
	$(document).on("submit", "#user_edit_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/user/update-user',
			type : 'PUT',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				console.log(data);
				switch(data) {
			    case "success":
			        alert("Brugeren blev redigeret");
			        showUserListPage();
			        break;
			    case "input-error":
			        alert("Input fejl");
			        break;
			    default:
			    	alert("System fejl");
				}
			},
			error: function(data){
				console.log("Fejl!")
				console.log(data);
			}
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
		url : "rest/user/read-user",
		type : "POST",
		data: userId,
		contentType: "application/json"
	});
	
}

function getUsers() {
	return $.ajax({
		url : "rest/user/read-users",
		type : "GET",
		contentType: "application/json"
	});
}