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
	
	// Vis alle brugere
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		var userListData = getUsers();
		$.get("src/userAdministration/user_list.html", function(template) {
			$("#content").html(template);
            $.get("src/userAdministration/user_list_row.html", function(template) {
            	$(".table tbody").html(Mustache.render($(template).html(), userListData.getJson()))
            });
        });
	});
	
});