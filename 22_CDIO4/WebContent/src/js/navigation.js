$(document).ready(function() {
	// Vis min bruger
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userid = "1";
		var userData = getUser(userid);
		$.get("src/userAdministration/user_edit.html", function(template) {
            $("#content").html(Mustache.render($(template).html(), userData.getJson()))		            
        });
	});
});