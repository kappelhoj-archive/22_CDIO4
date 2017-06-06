$(document).ready(function() {

	$("body").load("src/login.html");

	/*
	 * On sumbit login post request
	 * */
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		var userId = $("#user_id").val();
		var userData = getUser(userId);
		var template;
		
		$.ajax({
			url : 'rest/login/login-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				if(data == "super_admin" || data == "logged_in") {
					console.log(userData.getJson());
					
					//template = Mustache.render("<h1>{{rolle}}</h1>", userData.getJson());
					
					$.get("src/master.html", function(template, textStatus, jqXhr) {
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
});