$(document).ready(function() {
	
	/** 
	 * Links
	 * **/
	
	// Vis min bruger link
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = $(".top_nav_userid").text();
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
			url : 'rest/user/create',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				var splitData = data.split(": ");
				switch(splitData[0]) {
			    case "success":
			        alert(splitData[1]);
			        showUserListPage();
			        break;
			    case "input-error":
			    	alert(splitData[1]);
			        break;
			    case "id-error":
			    	alert(splitData[1]);
			    default:
			    	alert(splitData[1]);
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
			url : 'rest/user/update',
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
		console.log("Fejl!");
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