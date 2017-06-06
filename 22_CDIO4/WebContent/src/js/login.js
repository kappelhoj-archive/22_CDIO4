$(document).ready(function() {

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
				if(data == "new_log_in") {
					$.get("src/login_new_pass.html", function(template) {
			            $("body").html(template)		            
			        });
				}
				else if(data == "super_admin" || data == "logged_in") {		
					$.get("src/master.html", function(template) {
			            $("body").html(Mustache.render($(template).html(), userData.getJson()))		            
			        });
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