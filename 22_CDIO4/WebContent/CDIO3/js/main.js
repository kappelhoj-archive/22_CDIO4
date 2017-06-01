$(document).ajaxSend(function(event, xhr, settings) {
	xhr.setRequestHeader('Authorization', 'Bearer ' +
			localStorage.getItem('jwttoken'), settings.async = true);
});

$(document).ready(function() {

	$("body").load("login.html");

	/*
	 * Login form POST
	 * */
	$(document).on("submit", ".login-page form", function(event) {
		event.preventDefault();
		$.ajax({
			url : 'rest/CRUD/login-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				if(data == "true") {
					$("body").load("master.html", function() {
						showUserListPage();
					});
				}
				else {
					console.log("forkert login");
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});

	/*
	 * User Edit link and page
	 * */
	$(document).on("click", ".user-edit", function(event) {
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
					$("input[name=\"userId\"]").val(data.userId);
					$("input[name=\"userName\"]").val(data.userName);
					$("input[name=\"ini\"]").val(data.ini);
					$("input[name=\"cpr\"]").val(data.cpr);
					$("input[name=\"roles[]\"]").val(data.roles);
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
function cprChecker(cpr)
{
	var reg = new RegExp("^(?:(?:31(?:0[13578]|1[02])|(?:30|29)(?:0[13-9]|1[0-2])|(?:0[1-9]|1[0-9]|2[0-8])(?:0[1-9]|1[0-2]))[0-9]{2}-?[0-9]|290200-?[4-9]|2902(?:(?!00)[02468][048]|[13579][26])-?[0-3])[0-9]{3}|000000-?0000$");	

	if (reg.test(cpr))
		{return true;}
	else return false;
}
function usernameChecker(username)
{
	var reg = new RegExp("^(.){2,20}$");
	if(reg.test(username))
	{
		return true;
	}
	else return false;
}
function passwordChecker(password)
{
	var reg = new RegExp("^(((?=.*[a-z])(?=.*[A-Z])(?=.*\d).*$)|((?=.*[a-z])(?=.*[A-Z])(?=.*[\-._+!?=]).*$)|((?=.*[A-Z])(?=.*\d)(?=.*[\-._+!?=]))|((?=.*[a-z])(?=.*\d)(?=.*[\-._+!?=])))$");
	var reg2 = new RegExp("^(.){6,99999}$");
	if(password=="")
	{
		return false;
	}
	if(reg2.test(password)){
		if(reg.test(password))
		{
			return true;
		}
		else return false;
	}
	else return false;
}

