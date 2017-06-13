$(document).ready(function() {
	// Add drop down functionality to the user drop down
	$('.user_dropdown').dropdown();
	
	// Add alert functionality to the alert container
	$("#alert_container").alert();
	
	// Click on logo
	$(document).on("click", "#logo a", function(event) {
		event.preventDefault();
		// TODO: vis startside
	});
});

/*
 * Functions
 * */

// Split the REST response message and redirect to a page
function showRestMessage(data, showPage) {
	var splitData = data.split(": ");
	var alertData = { 
			notification : splitData[0],
	        message : splitData[1],
	        cssClass : "danger"
	};
	switch(alertData.notification) {
    case "success":
    	alertData.cssClass = "success";
    	showPage();
    	showAlertMessage(alertData);
        break;
    default: // Input, Collision & System error
    	showAlertMessage(alertData);
	}
}

// Show the REST response in an alert pop up
function showAlertMessage(alertData) {
	$.get("src/html/alert.html", function(template) {
		$("#alert_container").remove();
		$("body").append(Mustache.render(template, alertData));
		$("#alert_container").fadeIn("slow", function () {
		    $(this).delay(5000).fadeOut("slow", function() {
		    	$(this).remove();
		    });
		});
	});
}