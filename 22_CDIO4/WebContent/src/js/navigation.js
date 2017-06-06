$(document).ready(function() {
	// Vis min bruger
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = "1";
		getUser(userId).done(function(data) {
			$.get("src/userAdministration/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
		
//		$.ajax({
//		url : "rest/user/get-user",
//		type : "POST",
//		data: userId,
//		contentType: "application/json",
//		success : function(data){
//			$.get("src/userAdministration/user_edit.html", function(template) {
//	            $("#content").html(Mustache.render($(template).html(),data))		            
//	        });
//		},
//		error: function(data){
//			console.log("Fejl! " + data);
//		}
//	});
	});
	
	// Vis alle brugere
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		
		getUsers().done(function(data) {
			$.get("src/userAdministration/user_list.html", function(template) {
				$("#content").html(template);
				$.each(data, function(i, data){
					$.get("src/userAdministration/user_list_row.html", function(template) {
			            $("#user_list .table tbody").append("<tr>" + Mustache.render($(template).html() + "</tr>",data))
			        });
				});
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
		
//		$.ajax({
//		url : "rest/user/get-users",
//		type : "GET",
//		contentType: "application/json",
//		success : function(data){
//			$.get("src/userAdministration/user_list.html", function(template) {
//				$.each(data, function(i, data){
//					$("#user_list .table tbody").append(
//							"<tr><td>" + data.oprId + "</td>" +
//							"<td>" + data.oprNavn + "</td>" +
//							"<td>" + data.ini + "</td>" +
//							"<td>" + data.cpr + "</td>" +
//							"<td>" + data.rolle + "</td>" +
//							"<td><a href=\"#\" class=\"user-edit\">Edit</a></td>" +
//							"<td><a href=\"#\" class=\"user-delete\">Delete</a></td></tr>"
//					);
//				});
//				
//				
//				$("#content").html(template);
//	        });
//			
////			$.get("src/userAdministration/user_list_row.html", function(template) {
////            	$(".table tbody").html(Mustache.render($(template).html(), userListData.getJson()))
////            });
//		},
//		error: function(data){
//			console.log("Fejl! " + data);
//		}
//	});
	});
});