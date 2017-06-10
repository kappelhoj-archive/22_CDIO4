$(document).ready(function() {
	$('.user_dropdown').dropdown();
	$("#alert_container").alert();
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
		$("#master_container").append(Mustache.render($(template).html(), data));
		$("#alert_container").fadeIn("slow", function () {
		    $(this).delay(5000).fadeOut("slow", function() {
		    	$(this).remove();
		    });
		});
	});
}