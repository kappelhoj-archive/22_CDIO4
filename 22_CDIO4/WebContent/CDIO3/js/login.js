$(document).ready(function(){
	$(document).on("submit", ".login-page form", function() { // Sætter en click handler på knappen
		$.ajax({ // Indleder et asynkront ajax kald.
			url : 'rest/CRUD/login-user', // specificerer endpointet
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),// Typen af requestet (GET er default)
			success : function(data) {// Funktion der skal udføres når data er hentet
			console.log("Information sent for verification"); // Consol-log besked for tests.
			console.log(data);
			}
		});
		return false; // for at undgå at knappen poster data (default behavior).
	});
});