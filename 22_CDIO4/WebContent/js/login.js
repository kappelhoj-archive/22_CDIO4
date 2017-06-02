$(document).ready(function() {

	$("body").load("login.html");

	/*
	 * On sumbit login post request
	 * */
	$(document).on("submit", "#login_form", function(event) {
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

});