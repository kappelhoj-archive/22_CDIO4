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
	
	// TODO: Skal stå et andet sted
	// Add validation functionality to create user form
	$("#user_create_form").validate({
		rules : {
			id: {
				required: true
			},
			name: {
				required: true,
				minlength: 2
			}
		},
		errorElement: "div",
		errorPlacement: function(div, element) {			
	        div.addClass("form-control-feedback");
	        div.insertAfter(element);
	       
	    },
	    highlight: function(element) {
	    	$(element).parent().removeClass("has-success").addClass("has-danger");
	    	$(element).parent().find("input").removeClass("form-control-success").addClass("form-control-danger");
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
});

/*
 * Functions
 * */

// Split the REST response message and redirect to a page
function getResponseMessageAndRedirect(data, showPage) {
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
    case "input-error":
    	showAlertMessage(alertData);
        break;
    case "collision-error":
    	showAlertMessage(alertData);
    	break;
    default: // System error
    	showAlertMessage(alertData);
	}
}

// Show the REST response in an alert pop up
function showAlertMessage(alertData) {
	$.get("src/html/alert.html", function(template) {
		$("#alert_container").remove();
		$("#master_container").append(Mustache.render($(template).html(), alertData));
		$("#alert_container").fadeIn("slow", function () {
		    $(this).delay(5000).fadeOut("slow", function() {
		    	$(this).remove();
		    });
		});
	});
}