$(document).ready(function() {
	// Add drop down functionality to the user drop down
	$('.user_dropdown').dropdown();
	
	// Add alert functionality to the alert container
	$("#alert_container").alert();
	
	// Click on logo
	$(document).on("click", "#logo a", function(event) {
		event.preventDefault();
		showStartPage();
	});
	
	$(document).on("click", ".startpage_link", function(event) {
		event.preventDefault();
		var userId = $("input[name=\"id\"]").text();
		showStartPage(userId);
	});
});

function showStartPage() {
	// userId must be parsed as string
	var data = {
		id: $(".top_nav_userid").text(),
		name: $(".top_nav_name").text(),
		role: $(".top_nav_role").text()
		
	}
	$.get("src/html/start.html", function(template) {
		$("#content").html(Mustache.render(template, data));
	});
}

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
    	var $rawMaterialId = $("[name=\"rawMaterialId\"]");
    	var $rbId = $("[name=\"rbId\"]");
    	var $pbId = $("[name=\"pbId\"]");
    	var $recipeId = $("[name=\"recipeId\"]");
    	var $id = $("[name=\"id\"]");
    	
    	
    	$rawMaterialId.parent().removeClass("has-success").addClass("has-danger");
    	$rawMaterialId.removeClass("form-control-success").addClass("form-control-danger").attr("aria-invalid", "true");
    	
    	$rbId.parent().removeClass("has-success").addClass("has-danger");
    	$rbId.removeClass("form-control-success").addClass("form-control-danger").attr("aria-invalid", "true");
    	
    	$pbId.parent().removeClass("has-success").addClass("has-danger");
    	$pbId.removeClass("form-control-success").addClass("form-control-danger").attr("aria-invalid", "true");
    	
    	if($("#recipe_comp_edit_form").length < 1 && $("#recipe_component_create_form").length < 1) {
    		console.log("edit: " + $("#recipe_comp_edit_form").length)
    		console.log("create: " + $("#recipe_component_create_form").length)
    		console.log("hey");
    		$recipeId.parent().removeClass("has-success").addClass("has-danger");
        	$recipeId.removeClass("form-control-success").addClass("form-control-danger").attr("aria-invalid", "true");
    	}
    	
    	$id.parent().removeClass("has-success").addClass("has-danger");
    	$id.removeClass("form-control-success").addClass("form-control-danger").attr("aria-invalid", "true");
    	
    	showAlertMessage(alertData);
    	break;
    }
    default: // errors
    	// If the error alert message is on the new-login-password page then don't show the pop up
    	if($("#login_new_pass_form").length > 0) {
    		$("input[name=\"repeat_password\"]").prop('disabled', false);
			$("#login .alert").remove();
			$("#login_new_pass_form").find(".form-group:last").prepend("<div class=\"alert alert-danger\" role=\"alert\">" + alertData.message + "</div>");
    	}
    	else {
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