/**
 * 
 */


$(document).ready(function() {
	/*
	 * Navigation links
	 * */

	//Logo
	$(document).on("click", ".logo", function(event) {
		event.preventDefault();
		$(".users-page").load("user-list.html", function() {
			showUserListPage();
		});
	});

	// Create New User
	$(document).on("click", ".user-create", function(event) {
		event.preventDefault();
		$(".users-page").load("user-create.html");
	});

	// All Users
	$(document).on("click", ".user-list", function(event) {
		event.preventDefault();
		$(".users-page").load("user-list.html", function() {
			showUserListPage();
		});
	});

	// Logout
	$(document).on("click", ".logout", function(event) {
		event.preventDefault();
		$("body").load("login.html");
	});

});

function showUserListPage() {
	$.ajax({
		url : "rest/CRUD/get-users",
		type : "GET",
		contentType: "application/json",
		success : function(data){
			$(".users-page").load("user-list.html", function(){
				$.each(data, function(i, data){
					$(".table tbody").append(
							"<tr><td>" + data.userId + "</td>" +
							"<td>" + data.userName + "</td>" +
							"<td>" + data.ini + "</td>" +
							"<td>" + data.cpr + "</td>" +
							"<td>" + data.roles + "</td>" +
							"<td><a href=\"#\" class=\"user-edit\">Edit</a></td>" +
							"<td><a href=\"#\" class=\"user-delete\">Delete</a></td></tr>"
					);
				});    
			});
		},
		error: function(data){
			alert("fail data: " + data.toString());
		}
	});
	
	
}
