$(document).ready(function() {
	
	/*
	 * User Edit link and page
	 * */
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userid = $(this).parents("tr").children("td:first").text();
		$.ajax({
			url : "rest/CRUD/get-user",
			type : "POST",
			data : userid,
			contentType: "application/json",
			success : function(data){
				console.log(data);
				$(".users-page").load("user-edit.html", function(){
//					formmodified=0;
//					$('form *').change(function(){
//					formmodified=1;
//					});
//					window.onbeforeunload = confirmExit;
//					function confirmExit() {
//					if (formmodified == 1) {
//					return "New information not saved. Do you wish to leave the page?";
//					}
//					}
					$("#user_id").val(data.userId);
					$("#name").val(data.userName);
					$("#initials").val(data.ini);
					$("#cpr").val(data.cpr);
					$("#rolle").val(data.roles);
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Delete User
	 * */
	$(document).on("click", ".user-delete", function(event) {
		event.preventDefault();
		var userid = $(this).parents("tr").children("td:first").text();
		$.ajax({
			url : "rest/CRUD/delete-user",
			type : "DELETE",
			data : userid,
			contentType: "application/json",
			success : function(data){
				showUserListPage();
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Edit User PUT
	 * */
	$(document).on("submit", ".user-edit-page form", function(event) {
		event.preventDefault();
		$.ajax({
			url : "rest/CRUD/edit-user",
			type : "PUT",
			contentType: "application/json",
			data : $(this).serializeJSON(),
			success : function(data){
				$(".users-page").load("user-list.html", function() {
					showUserListPage();
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});

	/*
	 * Create user page
	 * */
	$(document).on("submit", ".user-create-page form", function(event) {
		event.preventDefault();
		$.ajax({
			url : "rest/CRUD/create-user",
			type : "POST",
			contentType: "application/json",
			data : $(this).serializeJSON(),
			success : function(data){
				$(".users-page").load("user-list.html", function() {
					showUserListPage();
				});
			},
			error: function(data){
				console.log(data);
				alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});	
});

function getUser(userId) {
	var json;
	$.ajax({
		url : "rest/user/get-user",
		type : "POST",
		data: userId,
		contentType: "application/json",
		success : function(data){
			json = data;
		},
		error: function(data){
			console.log("Fejl! " + data);
		}
	});
	return { getJson : function () {
		if(json) return json;
	}};
}