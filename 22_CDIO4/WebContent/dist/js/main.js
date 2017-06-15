$(document).ready(function() {
	
	showLoginPage();

	// Logout link
	$(document).on("click", ".logout_link", function(event) {
		event.preventDefault();
		showLoginPage();
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit login form
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		
		userLogin($(this).serializeJSON()).done(function(data) {
			onLoginShowPage(data);
		}).fail(function(data) {
			console.log("Fejl i Login REST");
		});
	});
	
	// Submit new password on login form
	$(document).on("submit", "#login_new_pass_form", function(event) {
		event.preventDefault();
		$("input[name=\"repeat_password\"]").prop('disabled', true);
		
		var userId = $("input[name=\"id\"]").val();
		userLoginNewPass($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showFullPage(userId) });
		}).fail(function(data) {
			console.log("Fejl i Login REST");
		});
	});
});

/*
 * Functions
 * */

function showFullPage(userId) {
	getUser(userId).done(function(data) {
		$.get("src/html/master.html", function(template) {
			$("#login").remove();
            $("body").append(Mustache.render(template, data));
            showStartPage();
            $("#user_dropdown_menu .user_edit_link").show();
            switch(data.role) {
            case "Admin":
            	$(".top_nav_role").addClass("role_admin");
            	getRoleTemplate("src/html/role_privilege/admin_privilege.html");
            	break;
            case "Farmaceut":
            	$(".top_nav_role").addClass("role_pharmacist");
            	getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html").done(function() {
            		getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
            			getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
            		});
            	});
            	break;
            case "Værkfører":
            	$(".top_nav_role").addClass("role_foreman");
            	getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
            		getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
            	});
            	break;
            case "Laborant":
            	$(".top_nav_role").addClass("role_labtech");
            	getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
            default: 
            	break;
            }
        });
	})
	.fail(function(x) {
		console.log("Fejl i User REST");
	});
}

function onLoginShowPage(data) {
	var splitData = data.split(": ");
	var userId = $("#login_form input[name=\"id\"]").val();
	$("#login .alert").remove();
	switch(splitData[0]) {
	case "super_login":
		$.get("src/html/master.html", function(template) {
			$("body").html(template);
			$("#user_dropdown_menu .user_edit_link").hide();
			$(".top_nav_role").addClass("role_super");
			$(".role_super").text("Super");
			$(".top_nav_name").text("admin");
			showStartPage();
			
			getRoleTemplate("src/html/role_privilege/admin_privilege.html").done(function() {
				getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html").done(function() {
					getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
						getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
					});
				});
			  });
		});
		break;
	case "new_login":		
		showNewLoginPage(userId);
		break;
	case "true_login":
		showFullPage();
		break;
	default: // not logged in
		$("#login").find(".form-group:last").prepend("<div class=\"alert alert-danger\" role=\"alert\">" + splitData[1] + "</div>");
	}
}

// Show the login page and focus the id input field.
function showLoginPage() {
	$.get("src/html/login.html", function(template) {
        $("body").html(template);
        $("#login_form input[name=\"id\"]").focus();
    });
}

function showNewLoginPage(userId) {
	$.get("src/html/login_new_pass.html", function(template) {
        $("body").html(template);
        $("#login_new_pass_form input[name=\"id\"]").val(userId);
        validateLoginNewPass();
    });
}

// Show side navigation depending on the user's role.
function getRoleTemplate(template) {
	return $.get(template, function(template) {
		$("#side_panel > #side_nav > ul").append(template);
	});
}

/*
 * REST functions
 * 
 */

function userLogin(form) {
	return $.ajax({
		url : 'rest/login/user',
		type : 'POST',
		contentType : "application/json",
		data : form
	});
}

function userLoginNewPass(form) {
	return $.ajax({
		url : 'rest/login/new_password',
		type : 'PUT',
		contentType : "application/json",
		data : form
	});
}

function resetPassword(userId) {
	return $.ajax({
		url : 'rest/login/reset_password',
		type : 'POST',
		contentType : "application/json",
		data : userId
	});
}

function changePassword(form) {
	return $.ajax({
		url : 'rest/login/change_password',
		type : 'POST',
		contentType : "application/json",
		data : form
	});
}$(document).ready(function() {
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
		event.prevenDefault();
		showStartPage();
	});
});

function showStartPage() {
	// userId must be parsed as string
	var data = {
		id: $("top_nav_userid").text(),
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
}/*
 * jQuery Validation methods
 * */
$.validator.addMethod("validCPR", function checkCPR(cpr){
	//Check if the cpr contains 10 numbers.
	if(cpr.search(/\d\d\d\d\d\d\d\d\d\d/)<-1){
		return false;
	}
	
	//Split into characther pairs.
	splitCPR=cpr.split("",2);
	
	//Valid date?
	if(parseInt(splitCPR[0])>31){
		return false;
	}
	//Valid Month?
	if(parseInt(splitCPR[1])>12){
		return false;
	}
	
	//Split into single characthers.
	splitCPR=cpr.split("");
	var cprsum=0;
	var controlNumber= [4,3,2,7,6,5,4,3,2];
	var controlCifre=parseInt(splitCPR[9])
	for(var i=0;i<9;i++){
		cprsum+=parseInt(splitCPR[i])*controlNumber[i];
	}
	if(11-(cprsum%11)==controlCifre){
		return true;
	}
	else{
		return false;
	}
}, "Indtast et gyldigt CPR-nummer.");

$.validator.addMethod("validID", function validID(id){
	if(id >= 1 && id <= 99999999 && id % 1 == 0) {
		return true;
	}
	else {
		return false;
	}
}, "Indtast et id mellem 1 og 99999999.");

$.validator.addMethod("validPassword", function checkPassword(password)
{
	//Counter for the different groups.
	var counter = 0;
	//Check passsword length
	if(password.length<6){
		return false;
	}
	//Check for capital letters.
	if(password.search(/[A-Z]/)>-1){
		counter++;
	}
	//Check for lowercase letters
	if(password.search(/[a-z]/)>-1){

		counter++;
	}
	//Check for numbers 
	if(password.search(/[0-9]/)>-1){
		counter++;
	}
	//Check for any of the given symbols.
	if(password.search(/[-._+!?=]/)>-1){
		counter++;
	}
	
	//Are 3 or more groups included.
	if(counter>=3){
		return true;
	}
	else{
		return false
	}
}, "Passwordet skal indeholde små og store bogstaver samt tal.");


$.validator.addMethod("validNetto", function checkNetto(netto)
{
	if(netto >= 0.05 && netto <= 20) {
		return true
	}
	else {
		return false;
	}

}, "Nominel netto skal være mellem 0.05 kg og 20 kg.");

$.validator.addMethod("validTolerance", function checkTolerance(tolerance)
{
	if(tolerance >= 0.1 && tolerance <= 10) {
		return true
	}
	else {
		return false;
	}

}, "Tolerance skal være mellem 0.1 % og 10 %.");

/*
 * jQuery Validation set defaults
 * */

$.validator.setDefaults({
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

/*
 * Functions
 * */

function validateLoginNewPass() {
	$("#login_new_pass_form").validate({
		rules : {
			password: {
				required: true,
				validPassword: true
			},
			repeat_password: {
				required: true,
				equalTo: function(element) {
					if($("#password").val() != "") {
						return "#password";
					}
					else {
						return false;
					}
				}
			}
		}
	});
}

function validateUser(form) {
	$(form).validate({
		rules : {
			id: {
				required: true,
				validID: true
			},
			name: {
				required: true,
				minlength: 2,
				maxlength: 20
			},
			ini: {
				required: true,
				minlength: 2,
				maxlength: 4
			},
			cpr: {
				required: true,
				validCPR: true
			},
			role: {
				required: true
			}
		}
	});
}

function validateRawMaterial(form) {
	$(form).validate({
		rules : {
			id: {
				required: true,
				validID: true
			},
			name: {
				required: true,
				minlength: 2,
				maxlength: 20
				
			},
			supplier: {
				required: true,
				minlength: 2,
				maxlength: 20
			}
		}
	});
}

function validateRecipe(form) {
	$(form).validate({
		rules : {
			recipeId: {
				required: true,
				validID: true
			},
			recipeName: {
				required: true,
				minlength: 2,
				maxlength: 20
			}
		}
	});
}

function validateRecipeComp(form) {
	$(form).validate({
		rules : {
			recipeId: {
				required: true,
				validID: true
			},
			rawMaterialId: {
				required: true,
				validID: true
			},
			nomNetto: {
				required: true,
				validNetto: true
			},
			tolerance: {
				required: true,
				validTolerance: true
			}
		}
	});
}

function validateRawMaterialBatch(form) {
	$(form).validate({
		rules : {
			rbId: {
				required: true,
				validID: true
			},
			rawMaterialId: {
				required: true,
				validID: true
			},
			amount: {
				required: true,
				number: true,
				min: 0
			}
		}
	});
}

function validateProductBatch(form) {
	$(form).validate({
		rules : {
			pbId: {
				required: true,
				validID: true
			},
			recipeId: {
				required: true,
				validID: true
			}
		}
	});
}$(document).ready(function() {
	
	/* 
	 * Links
	 * */
	
	// Link to list of product batches page
	$(document).on("click", ".product_batch_list_link", function(event) {
		event.preventDefault();
		showProductBatchListPage();
	});
	
	// Link to create product batch page
	$(document).on("click", ".product_batch_create_link", function(event) {
		event.preventDefault();
		getRecipeList().done(function(data) {
			$.get("src/html/product_batch/product_batch_create.html", function(template) {
	            $("#content").html(template);
				$.each(data, function(i, data) {
					$(".custom-select").append(Mustache.render("<option value=\""+ data.recipeId + "\">" + data.recipeId + " (" + data.recipeName + ")</option>", data));
				});
				validateProductBatch("#product_batch_create_form");
	        });
		}).fail(function(data){
			console.log("Fejl i Recipe REST")
		});
	});
	
	// Link to edit product batch page
	$(document).on("click", ".product_batch_edit_table_link", function(event) {
		event.preventDefault();
		var productBatchId = $(this).parents("tr").children("td:first").text();
		getProductBatch(productBatchId).done(function(data) {
			var statusCode = data.status;
			$.get("src/html/product_batch/product_batch_edit.html", function(template) {
				showProductBatchStatus(data);
				$("#content").html(Mustache.render($(template).html(), data));
				// raw material id must be parsed as a string ("")
				getRecipe("" + data.recipeId).done(function(data) {
					$("input[name=\"recipeName\"]").val(data.recipeName);
				}).fail(function(data){
					console.log("Fejl i Recipe REST")
				});
				$(".pb_status").removeClass("status_0").removeClass("status_1").removeClass("status_2").addClass("status_"+statusCode);
				showProductBatchCompsPage(productBatchId);
			});
		}).fail(function(data){
			console.log("Fejl i ProductBatch REST");
		});
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create product batch form
	$(document).on("submit", "#product_batch_create_form", function(event) {
		event.preventDefault();
		createProductBatch($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showProductBatchListPage() });
		}).fail(function(data) {
			console.log("Fejl i ProductBatch REST");
		});
	});	
	
});

/*
 * Functions
 * */

function showProductBatchListPage() {
	getProductBatchList().done(function(data) {
		$.get("src/html/product_batch/product_batch_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data){
				var statusCode = data.status;
				showProductBatchStatus(data);
				$.get("src/html/product_batch/product_batch_list_row.html", function(template) {
		            $("#product_batch_list .table tbody").append(Mustache.render($(template).html(),data))
		            $(".pb_status").addClass("status_"+statusCode);
		        });
			});
        });
	}).fail(function(x) {
		console.log("Fejl i ProductBatch REST");
	});
}

// Show product batch components for a specific product batch
function showProductBatchCompsPage(pbId) {
	getProductBatchCompListSpecific(pbId).done(function(data) {
		$.get("src/html/product_batch/product_batch_comp_list.html", function(template) {
			$("#product_batch_edit_form").append(template);
			
			if(data.length > 0) {
				$("#product_batch_comp_list .product_batch_comp_list_table").html(
						"<table class=\"table table-hover\">"
							+"<thead>"
								+"<tr>"
									+"<th>Produkt batch id</th>"
									+"<th>Råvare batch id</th>"
									+"<th>Tara</th>"
									+"<th>Netto</th>"
									+"<th>Bruger id</th>"
								+"</tr>"
							+"</thead>"
							+"<tbody></tbody>"
						+"</table>");
				$.each(data, function(i, data) {
					$.get("src/html/product_batch/product_batch_comp_list_row.html", function(template) {
						$("#product_batch_comp_list .table tbody").append(Mustache.render($(template).html(), data));
					});
				});
			}
		});
	});
}

// Change status code of a product batch to a string
function showProductBatchStatus(data) {
	switch(data.status) {
	case 0:
		data.status = "Ikke påbegyndt";
		break;
	case 1:
		data.status = "Under produktion";
		break;
	case 2:
		data.status = "Afsluttet";
		break;
	default:
		console.log("Fejl i status kode");
		break;
	}
}

/*
 * REST functions
 * */

function createProductBatch(form) {
	return $.ajax({
		url: "rest/product_batch/create",
		type: "POST",
		contentType: "application/json",
		data: form
		});
}

function getProductBatch(pbId) {
	return $.ajax({
		url : "rest/product_batch/read",
		type : "POST",
		data : pbId,
		contentType: "application/json"
	});
}

function getProductBatchList() {
	return $.ajax({
		url : "rest/product_batch/read_list",
		type : "GET",
		contentType: "application/json"
	});
}

function getProductBatchCompListSpecific(pbId) {
	return $.ajax({
		url : "rest/product_batch_comp/read_list_specific",
		type : "POST",
		data : pbId,
		contentType: "application/json"
	});
}$(document).ready(function() {
	
	/* 
	 * Links
	 * */
	
	// Link to list raw material batches page
	$(document).on("click", ".raw_material_batch_list_link", function(event) {
		event.preventDefault();
		showRawMaterialBatchListPage();
	});
	
	// Link to create raw material batch page
	$(document).on("click", ".raw_material_batch_create_link", function(event) {
		event.preventDefault();
		
		getRawMaterialList().done(function(data) {
			$.get("src/html/raw_material_batch/raw_material_batch_create.html", function(template) {
	            $("#content").html(template);
				$.each(data, function(i, data) {
					$(".custom-select").append(Mustache.render("<option value=\""+ data.id + "\">" + data.id + " (" + data.name + ")</option>", data));
				});
				validateRawMaterialBatch("#raw_material_batch_create_form");
	        });
		}).fail(function(data){
			console.log("Fejl i Recipe REST")
		});
	});
	
	// Link to edit raw material batch page
	$(document).on("click", ".raw_material_batch_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialBatchId = $(this).parents("tr").children("td:first").text();
		getRawMaterialBatch(rawMaterialBatchId).done(function (data) {
			$.get("src/html/raw_material_batch/raw_material_batch_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data));
				// raw material id must be parsed as a string ("")
				getRawMaterial("" + data.rawMaterialId).done(function(data) {
					$("input[name=\"rawMaterialName\"]").val(data.name);
				}).fail(function(data){
					console.log("Fejl i RawMaterial REST")
				});
				validateRawMaterialBatch("#raw_material_batch_edit_form");
			});
		})
		.fail(function(data) {
			console.log("Fejl i REST");
		});
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create raw material batch form
	$(document).on("submit", "#raw_material_batch_create_form", function(event) {
		event.preventDefault();
		createRawMaterialBatch($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRawMaterialBatchListPage() });
		}).fail(function(data) {
			console.log("Fejl i REST");
		});
	});
	
	// Submit edit raw material batch form
	$(document).on("submit", "#raw_material_batch_edit_form", function(event) {
		event.preventDefault();
		$("input[name=\"rawMaterialName\"]").prop('disabled', true);
		updateRawMaterialBatch($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { showRawMaterialBatchListPage() });
		}).fail(function(data) {
			console.log("Fejl i REST");
		});
	});
});

// Show list of raw material batches page
function showRawMaterialBatchListPage() {
	getRawMaterialBatchList().done(function(data) {
		$.get("src/html/raw_material_batch/raw_material_batch_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data){
				$.get("src/html/raw_material_batch/raw_material_batch_list_row.html", function(template) {
		            $("#raw_material_batch_list .table tbody").append(Mustache.render($(template).html(),data))
		        });
			});
        });
	})
	.fail(function(x) {
		console.log("Fejl!");
	});
}

/*
 * REST functions
 * */

function createRawMaterialBatch(form) {
	return $.ajax({
		url : "rest/raw_material_batch/create",
		type : "POST",
		contentType : "application/json",
		data : form
	});
}

function updateRawMaterialBatch(form) {
	return $.ajax({
		url : "rest/raw_material_batch/update",
		type : "PUT",
		contentType : "application/json",
		data : form
	});
}

function getRawMaterialBatch(rbId) {
	return $.ajax({
		url : "rest/raw_material_batch/read",
		type : "POST",
		data: rbId,
		contentType: "application/json"
	});
}

function getRawMaterialBatchList() {
	return $.ajax({
		url : "rest/raw_material_batch/read_list",
		type : "GET",
		contentType: "application/json"
	});
}

// Get list of raw material batches for a specific raw material
// Not in use.
function getRawMaterialBatchListSpecific(rawMaterialId) {
	return $.ajax({
		url : "rest/raw_material_batch/read_list_specific",
		type : "POST",
		data: rawMaterialId,
		contentType: "application/json"
	});
}$(document).ready(function(){
	
	/* 
	 * Links
	 * */
	
	// Link to list recipes page
	$(document).on("click", ".recipe_list_link", function(event) {
		event.preventDefault();
		showRecipeListPage();
	});
	
	// Link to create recipe page
	$(document).on("click", ".recipe_create_link", function(event){
		event.preventDefault();
		$.get("src/html/recipe/recipe_create.html", function(template) {
			$("#content").html(template);
			validateRecipe("#recipe_create_form");
		});
	})
	
	// Link to edit recipe page
	$(document).on("click", ".recipe_edit_table_link", function(event) {
		event.preventDefault();
		var recipeId = $(this).parents("tr").children("td:first").text();
		showRecipeEditPage(recipeId);
	});
	
	// Link to edit recipe page (back button)
	$(document).on("click", ".recipe_edit_back_link", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		showRecipeEditPage(recipeId);
	});
	
	// Link to edit recipe component page
	$(document).on("click", ".recipe_comp_edit_table_link", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		var rawMaterialId = $(this).parents("tr").children("td:first").text();
		getRecipeComp(recipeId, rawMaterialId).done(function(data) {
			$.get("src/html/recipe/recipe_comp_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data));
				getRawMaterial(rawMaterialId).done(function(data) {
					$("input[name=\"rawMaterialName\"]").val(data.name);
				}).fail(function(data){
					console.log("Fejl i RawMaterial REST")
				});
				
				validateRecipeComp("#recipe_comp_edit_form");
			});
		})
		.fail(function(data) {
			console.log(data);
		});
	});
	
	// Link to create recipe component page
	$(document).on("click", ".recipe_comp_create_link", function(event) {
		event.preventDefault();
		var recipeId = $("#recipe_edit_form input[name=\"recipeId\"]").val();
		getRawMaterialList().done(function(data) {
			$.get("src/html/recipe/recipe_component_create.html", function(template) {
	            $("#content").html(template);
	            $("#recipe_component_create_form input[name=\"recipeId\"]").val(recipeId);
				$.each(data, function(i, data) {
					$(".custom-select").append(Mustache.render("<option value=\""+ data.id + "\">" + data.id + " (" + data.name + ")</option>", data));
				});
				validateRecipeComp("#recipe_component_create_form");
	        });
		}).fail(function(data){
			console.log("Fejl i Recipe REST")
		});
		
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create recipe form
	$(document).on("submit", "#recipe_create_form", function(event){
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		createRecipe($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRecipeEditPage(recipeId) });
		}).fail(function(data) {
			console.log("Fejl i Recipe REST");
		});
	})
	
	// Submit edit recipe component form
	$(document).on("submit", "#recipe_comp_edit_form", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		$("input[name=\"rawMaterialName\"]").prop('disabled', true);
		updateRecipeComp($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRecipeEditPage(recipeId) });
		}).fail(function(data) {
			console.log("Fejl i RecipeComp REST");
		});
	});
	
	// Submit create recipe component form
	$(document).on("submit", "#recipe_component_create_form", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		createRecipeComp($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRecipeEditPage(recipeId) });
		});
	});
});

/*
 * Functions
 * */

function showRecipeListPage() {
	getRecipeList().done(function(data) {
		$.get("src/html/recipe/recipe_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data) {
				$.get("src/html/recipe/recipe_list_row.html", function(template) {
					$("#recipe_list .table tbody").append(Mustache.render($(template).html(), data))
				});
			});
		});
	}).fail(function(data){
		console.log("Fejl i Recipe REST")
	});
}

function showRecipeCompsPage(recipeId) {
	getRecipeCompList(recipeId).done(function(data) {
		$.get("src/html/recipe/recipe_comp_list.html", function(template) {
			$("#recipe_edit_form").append(template);
			if(data.length > 0)
			{
				$("#recipe_comp_list .recipe_comp_list_table").html(
						"<table class=\"table table-hover\">"
							+"<thead>"
								+"<tr>"
									+"<th>Råvare id</th>"
									+"<th>Nominel netto (kg)</th>"
									+"<th>Tolerance (%)</th>"
									+"<th></th>"
								+"</tr>"
							+"</thead>"
							+"<tbody></tbody>"
						+"</table>");
				$.each(data, function(i, data) {
					$.get("src/html/recipe/recipe_comp_list_row.html", function(template) {
						$("#recipe_comp_list .table tbody").append(Mustache.render($(template).html(), data))
					});
				});
			}
		});
	});
}

function showRecipeEditPage(recipeId) {
	getRecipe(recipeId).done(function(data) {
		$.get("src/html/recipe/recipe_edit.html", function(template) {
			$("#content").html(Mustache.render($(template).html(), data))
			showRecipeCompsPage(recipeId);
		});
	})
	.fail(function(data){
		console.log("Fejl i Recipe Comp REST");
	});
	
	
}

/*
 * REST functions
 * */

function getRecipeCompList(recipeId){
	return $.ajax({
		url: "rest/recipe_component/read_list_specific",
		type: "POST",
		data: recipeId,
		contentType: "application/json"
	});
}

function getRecipeComp(recipeId, rawMaterialId) {
	var json = JSON.stringify({recipeId: recipeId, rawMaterialId: rawMaterialId});
	return $.ajax({
		url: "rest/recipe_component/read",
		type: "POST",
		contentType: "application/json",
		data: json
	});
}

function createRecipeComp(form) {
	return $.ajax({
		url: "rest/recipe_component/create",
		type: "POST",
		contentType: "application/json",
		data: form
	});
}

function updateRecipeComp(form) {
	return $.ajax({
		url: "rest/recipe_component/update",
		type: "PUT",
		contentType: "application/json",
		data: form
	});
}

function createRecipe(form) {
	return $.ajax({
		url: "rest/recipe/create",
		type: "POST",
		contentType: "application/json",
		data: form
	});
}

function getRecipeList() {
	return $.ajax({
		url: "rest/recipe/read_list",
		type: "GET",
		contentType: "application/json"
	});
}

function getRecipe(id){
	return $.ajax({
		url: "rest/recipe/read",
		type: "POST",
		data: id,
		contentType: "application/json"
	});
}$(document).ready(function()
{	
	/*
	 * Links
	 * */
	
	// Link to list of raw materials page
	$(document).on("click", ".raw_material_list_link", function(event) {
		event.preventDefault();
		showRawMaterialListPage();
	});
	
	
	// Link to create raw material page
	$(document).on("click", ".raw_material_create_link", function(event){
		event.preventDefault();
		$.get("src/html/raw_material/raw_material_create.html", function(template) {
			$("#content").html(template);
			validateRawMaterial("#raw_material_create_form");
		});
	});
	
	// Link to edit raw material page
	$(document).on("click", ".raw_material_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialId = $(this).parents("tr").children("td:first").text();
		getRawMaterial(rawMaterialId).done(function (data) {
			$.get("src/html/raw_material/raw_material_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
				validateRawMaterial("#raw_material_edit_form");
			});
		}).fail(function(data) {
			console.log("Fejl i RawMaterial REST");
		});
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create raw material form
	$(document).on("submit", "#raw_material_create_form", function(event) {
		event.preventDefault();		
		createRawMaterial($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRawMaterialListPage() });
		}).fail(function(data) {
			console.log("Fejl i RawMaterial REST");
		});
	});
	
	
	// Submit edit raw material form
	$(document).on("submit", "#raw_material_edit_form", function(event){
		event.preventDefault();
		updateRawMaterial($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showRawMaterialListPage() });
		}).fail(function(data) {
			console.log("Fejl i RawMaterial REST");
		});
	});
});

/*
 * Functions
 * */

function showRawMaterialListPage() {
	getRawMaterialList().done(function(data) {
		$.get("src/html/raw_material/raw_material_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data) {
				$.get("src/html/raw_material/raw_material_list_row.html", function(template) {
					$("#raw_material_list .table tbody").append(Mustache.render($(template).html(), data))
				});
			});
		});
	}).fail(function(data){
		console.log("Fejl i RawMaterial REST")
	})
}

/*
 * REST functions
 * */

function createRawMaterial(form) {
	return $.ajax({
		url: "rest/raw_material/create",
		type: "POST",
		contentType: "application/json",
		data: form
	});
}

function updateRawMaterial(form) {
	return $.ajax({
		url: "rest/raw_material/update",
		type: "PUT",
		contentType: "application/json",
		data: form
	})
}

function getRawMaterialList(){
	return $.ajax({
		url: "rest/raw_material/read_list",
		type: "GET",
		contentType: "application/json"
	});
}

function getRawMaterial(rawMaterialId){
	return $.ajax({
		url: "rest/raw_material/read",
		type: "POST",
		data: rawMaterialId,
		contentType: "application/json"
	});
}$(document).ready(function() {
	
	/* 
	 * Links
	 * */
	
	// Link to edit user (logged in) page
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = $(".top_nav_userid").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data));
	            showInitials();
	            validateUser("#user_edit_form");
	        });
		})
		.fail(function(x) {
			console.log("Fejl i User REST");
		});
	});
	
	// Link to list of users page
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		showUserListPage();
	});
	
	// Link to create user page
	$(document).on("click", ".user_create_link", function(event) {
		event.preventDefault();
		$.get("src/html/user/user_create.html", function(template) {
            $("#content").html(template);
            showInitials();
            validateUser("#user_create_form");
        });
	});
	
	// Link to edit user page
	$(document).on("click", ".user_edit_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
		showUserEditAdminPage(userId);
		
	});
	
	$(document).on("click", ".user_edit_admin_reset_pw_link", function(event) {
		event.preventDefault();
		var userId = $("input[name=\"id\"]").val();
		resetPassword(userId).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() })
			
		})
		.fail(function(x) {
			console.log("Fejl i User REST");
		});
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create user form
	$(document).on("submit", "#user_create_form", function(event) {
		event.preventDefault();
		createUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});		
	});
	
	// Submit edit user form
	$(document).on("submit", "#user_edit_form", function(event) {
		event.preventDefault();
		updateUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showStartPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	// Submit edit user (admin) form
	$(document).on("submit", "#user_edit_admin_form", function(event) {
		event.preventDefault();
		updateUser($(this).serializeJSON()).done(function(data) {
			showRestMessage(data, function() { return showUserListPage() });
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	// Submit edit user (admin) form
	$(document).on("submit", "#user_edit_password_form", function(event) {
		event.preventDefault();
		var userId = $("#user_edit_password_form input[name=\"id\"]").val();
		var $password = $("#user_edit_password_form input[name=\"password\"]");
		
		// Remove error message
		$("#password-error").remove();
		
		changePassword($(this).serializeJSON()).done(function(data) {
			var splitData = data.split(": ");
			switch(splitData[0]) {
			case "success":
				showNewLoginPage(userId);
			default:
				$password.parent().append("<div id=\"password-error\" class=\"error form-control-feedback\">Forkert password.</div>");
				$password.addClass("form-control-danger");
				$password.parent().addClass("has-danger");
				console.log(splitData[1]);
			}
		}).fail(function(data) {
			console.log("Fejl i User REST");
		});	
	});
	
	
});

/*
 * Functions
 * */

function showInitials() {
	$("input[name=\"ini\"]").focus(function() {
    	var iniValue = $(this).val();
    	var iniAuto = generateInitials($("input[name=\"name\"]").val());
    	if (iniValue ==  "") {
    		$(this).val(iniAuto);
    	}
    });
}

function showUserEditAdminPage(userId) {
	getUser(userId).done(function(data) {
		$.get("src/html/user/user_edit_admin.html", function(template) {
            $("#content").html(Mustache.render($(template).html(),data));
            $(".custom-select").find("option[value=\"" + data.role + "\"]").attr("selected", true);
            showInitials();
            validateUser("#user_edit_admin_form");
        });
	})
	.fail(function(x) {
		console.log("Fejl i User REST");
	});
}

function showUserListPage() {
	getUserList().done(function(data) {
		$.get("src/html/user/user_list.html", function(template) {
			$("#content").html(template);
			$.each(data, function(i, data){
				$.get("src/html/user/user_list_row.html", function(template) {
		            $("#user_list .table tbody").append(Mustache.render($(template).html(),data))
		        });
			});
        });
	})
	.fail(function(x) {
		console.log("Fejl i User REST");
	});
}

//generate initials from a name.
function generateInitials(name){
	var initials="";
	splitName=name.split(" ");
	if(splitName.length<3){
		//Add the two first letters of every name.
		for(var i=0;i<splitName.length;i++){
			
			initials+=splitName[i].substring(0,2);
		}
	}
	else{
		//Add first letter of the first name. 
		initials+=splitName[0].substring(0,1);
		
		//Make sure the initials length match:
		var modifier;
		if(splitName.length==3){
			modifier=2;
		}
		else{
			modifier=3;
		}
		
		//Add the first letter of the last three names, or two if there are 3 names.
		for(var i=splitName.length-modifier;i<splitName.length;i++){
			initials+=splitName[i].substring(0,1);
		}
	}
	return initials
}

/*
 * REST functions
 * */

function createUser(form) {
	return $.ajax({
		url : 'rest/user/create',
		type : 'POST',
		contentType : "application/json",
		data : form
	});
}

function updateUser(form) {
	return $.ajax({
		url : 'rest/user/update',
		type : 'PUT',
		contentType : "application/json",
		data : form
	});
}

function getUser(userId) {
	return $.ajax({
		url : "rest/user/read",
		type : "POST",
		data: userId,
		contentType: "application/json"
	});
	
}

function getUserList() {
	return $.ajax({
		url : "rest/user/read_list",
		type : "GET",
		contentType: "application/json"
	});
}