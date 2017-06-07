$(document).ready(function() {
	
});

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