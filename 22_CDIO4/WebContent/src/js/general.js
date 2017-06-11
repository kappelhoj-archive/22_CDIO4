$(document).ready(function() {
	$('.user_dropdown').dropdown();
	$("#alert_container").alert();
	
	// Click on logo
	$(document).on("click", "#logo a", function(event) {
		event.preventDefault();
		// show start page
	});
	
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
	    	console.log(element);
	    	console.log($(element));
	    },
	    unhighlight: function(element) {
	    	$(element).parent().removeClass("has-danger").addClass("has-success");
	    	$(element).parent().find("input").removeClass("form-control-danger").addClass("form-control-success");        
	    }
	});
});

function saveRecord(data, showPage) {
	var splitData = data.split(": ");
	var splitDataToJson = { 
			notification : splitData[0],
	        message : splitData[1],
	        cssClass : "danger"
	};
	switch(splitDataToJson.notification) {
    case "success":
    	splitDataToJson.cssClass = "success";
    	showPage();
    	showAlertMessage(splitDataToJson);
        break;
    case "input-error":
    	showAlertMessage(splitDataToJson);
        break;
    case "collision-error":
    	showAlertMessage(splitDataToJson);
    	break;
    default: // System error
    	showAlertMessage(splitDataToJson);
	}
}

function showAlertMessage(data) {
	$.get("src/html/alert.html", function(template) {
		$("#alert_container").remove();
		$("#master_container").append(Mustache.render($(template).html(), data));
		$("#alert_container").fadeIn("slow", function () {
		    $(this).delay(5000).fadeOut("slow", function() {
		    	$(this).remove();
		    });
		});
	});
}