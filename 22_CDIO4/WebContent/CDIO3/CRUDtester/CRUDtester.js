$(document).ready(function() { // Sikrer sig at dokumentet er indlæst, 
							   //før click handlers håndteres
	$('#button').click(function() {
		$.ajax({				//Indleder et asynkront ajax kald
			url : '../rest/CRUD/get-users',	//specificerer endpointet
			type : 'GET',			//Typen af HTTP requestet (GET er default)
			success : function(data){//Funktion der skal udføres når data er hentet
				console.log(data); //Manipulerer #mydiv.
			}
		});
		return false; //for at undgå at knappen poster data (default behavior)
	});
	/*
	 * Edit User PUT
	 * */
	$('#submit-button').click(function() {
		console.log($('#my-form').serializeJSON());
		$.ajax({
			url : "../rest/CRUD/edit-user",
			type : "PUT",
			contentType: "application/json",
			data : $('#my-form').serializeJSON(),
			success : function(data){
				console.log(data);
			},
			error: function(data){
				console.log(data);
			    alert("fail data: " + data);
			}
		});
		return false; //for at undgå at knappen poster data (default behavior).
	});
	
});