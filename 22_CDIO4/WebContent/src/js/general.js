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
    case "collision-error": {
    	
    	$("input[name=\"rawMaterialId\"]").parent().removeClass("has-success").addClass("has-danger");
    	$("input[name=\"rawMaterialId\"]").removeClass("form-control-success").addClass("form-control-danger");
    	
    	$("input[name=\"rbId\"]").parent().removeClass("has-success").addClass("has-danger");
    	$("input[name=\"rbId\"]").removeClass("form-control-success").addClass("form-control-danger");
    	
    	$("input[name=\"pbId\"]").parent().removeClass("has-success").addClass("has-danger");
    	$("input[name=\"pbId\"]").removeClass("form-control-success").addClass("form-control-danger");
    	
    	$("input[name=\"recipeId\"]").parent().removeClass("has-success").addClass("has-danger");
    	$("input[name=\"recipeId\"]").removeClass("form-control-success").addClass("form-control-danger");
    	
    	$("input[name=\"id\"]").parent().removeClass("has-success").addClass("has-danger");
    	$("input[name=\"id\"]").removeClass("form-control-success").addClass("form-control-danger");
    	showAlertMessage(alertData);
    	break;
    }
    default: // errors
    	// If the error alert message is on the new-login-password page then don't show the pop up
    	if($("#login_new_pass_form").length < 1) {
    		showAlertMessage(alertData);
    	}
	}
}

// Show the REST response in an alert pop up
function showAlertMessage(alertData) {
	$.get("src/html/alert.html", function(template) {
		$("#alert_container").remove();
		$("body").append(Mustache.render(template, alertData));
		
		if($("#alert_container").width() > 500)
		{
			$("#alert_container").css("margin-left", "-" + $("#alert_container").width() / 2);
		}
		
		$("#alert_container").fadeIn("slow", function () {
		    $(this).delay(10000).fadeOut("slow", function() {
		    	$(this).remove();
		    });
		});
	});
}