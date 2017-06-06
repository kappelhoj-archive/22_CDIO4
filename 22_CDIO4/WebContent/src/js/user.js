$(document).ready(function() {
	
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
		return json;
	}};
}

function getUsers() {
	$.ajax({
		url : "rest/user/get-users",
		type : "GET",
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
		return json;
	}};
}