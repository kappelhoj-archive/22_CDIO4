$(document).ready(function() {
	// Vis min bruger
	
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		console.log("trkkede på knappen")
		var userid = "1";
		var userData = getUser(userid);
		$.get("src/userAdministration/user_edit.html", function(template) {
            $("#content").html(Mustache.render($(template).html(), userData.getJson()))		            
        });
//		$.ajax({
//			url : "rest/user/get-user",
//			type : "POST",
//			data : userid,
//			contentType: "application/json",
//			success : function(data){
//				console.log(data);
//				$.get("src/userAdministration/user_edit.html", function(template) {
//		            $("#content").html(Mustache.render($(template).html(), data))		            
//		        });
//
//				
//			},
//			error: function(data){
//				console.log(data);
//				alert("fail data: " + data);
//			}
//		});
	});
});$(document).ready(function() {

	$("body").load("src/login.html");

	/*
	 * On sumbit login post request
	 * */
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		var userId = $("#user_id").val();
		var userData = getUser(userId);
		
		$.ajax({
			url : 'rest/login/login-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				if(data == "super_admin" || data == "logged_in") {
					
					//template = Mustache.render("<h1>{{rolle}}</h1>", userData.getJson());
					
					$.get("src/master.html", function(template) {
			            $("body").html(Mustache.render($(template).html(), userData.getJson()))		            
			        });
//					$("body").load("src/master.html", function() {
//						console.log(userId);
//						getUser(userId);
//					});
				}
				else {
					console.log(data);
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
});$(document).ready(function() {
	
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
			console.log(data);
		},
		error: function(data){
			console.log("Fejl! " + data);
		}
	});
	return { getJson : function () {
		return json;
	}};

}