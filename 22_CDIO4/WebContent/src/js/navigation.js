$(document).ready(function() {
	// Vis min bruger
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
	
	// Vis alle brugere
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		
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
	});
});