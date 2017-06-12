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
}$(document).ready(function() {
	
	/** 
	 * Links
	 * **/
	
	// Vis alle produktbatches link
	$(document).on("click", ".product_batch_list_link", function(event) {
		event.preventDefault();
		showProductBatchListPage();
	});
	
	// Vis alle råvarebatches link
	$(document).on("click", ".product_batch_create_link", function(event) {
		event.preventDefault();
		$.get("src/html/product_batch/product_batch_create.html", function(template) {
            $("#content").html(template)
        });
	});
	
	// Vis alle råvarebatches link
	$(document).on("click", ".product_batch_edit_table_link", function(event) {
		event.preventDefault();
		var productBatchId = $(this).parents("tr").children("td:first").text();
		getProductBatch(productBatchId).done(function(data) {
			var statusCode = data.status;
			$.get("src/html/product_batch/product_batch_edit.html", function(template) {
				showProductBatchStatus(data);
				$("#content").html(Mustache.render($(template).html(), data));
				$(".pb_status").addClass("status_"+statusCode);
				showProductBatchCompsPage(productBatchId);
			});
		})
		.fail(function(data){
			console.log(data);
		});
	});
	
	
});

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
	})
	.fail(function(x) {
		console.log("Fejl!");
	});
}

function showProductBatchCompsPage(pbId) {
	getProductBatchCompListSpecific(pbId).done(function(data) {
		$.get("src/html/product_batch/product_batch_comp_list.html", function(template) {
			$("#product_batch_edit_form").append(template);
			$.each(data, function(i, data) {
				$.get("src/html/product_batch/product_batch_comp_list_row.html", function(template) {
					$("#product_batch_comp_list .table tbody").append(Mustache.render($(template).html(), data));
				});
			});
		});
	});
}

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

function getProductBatch(pbId) {
	return $.ajax({
		url : "rest/product_batch/read",
		type : "POST",
		data : pbId,
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
}

function getProductBatchList() {
	return $.ajax({
		url : "rest/product_batch/read_list",
		type : "GET",
		contentType: "application/json"
	});
}$(document).ready(function() {
	
	/** 
	 * Links
	 * **/
	
	// Vis alle råvarebatches link
	$(document).on("click", ".raw_material_batch_list_link", function(event) {
		event.preventDefault();
		showRawMaterialBatchListPage();
	});
	
	$(document).on("click", ".raw_material_batch_create_link", function(event) {
		event.preventDefault();
		$.get("src/html/raw_material_batch/raw_material_batch_create.html", function(template) {
            $("#content").html(template)
        });
	});
	
	/* Edit raw material*/
	$(document).on("click", ".raw_material_batch_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialBatchId = $(this).parents("tr").children("td:first").text();
		getRawMaterialBatch(rawMaterialBatchId).done(function (data) {
			$.get("src/html/raw_material_batch/raw_material_batch_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
			});
		})
		.fail(function(data) {
			console.log(data);
		});
	});
	
	// venter med den her
	$(document).on("submit", "#raw_material_batch_create_form", function(event) {
		event.preventDefault();
		createRawMaterialBatch($(this).serializeJSON()).done(function(data) {
			saveRecord(data, showRawMaterialBatchListPage)
		}).fail(function(data) {
			console.log("Fejl i REST");
		});
	});
	
	$(document).on("submit", "#raw_material_batch_edit_form", function(event) {
		event.preventDefault();
		updateRawMaterialBatch($(this).serializeJSON()).done(function(data) {
			var splitData = data.split(": ");
			switch(splitData[0]) {
		    case "success":
		    	showRawMaterialBatchListPage();
		        alert(splitData[1]);
		        break;
		    case "input-error":
		        alert(splitData[1]);
		        break;
		    case "collision-error":
		    	alert(splitData[1]);
		    	break;
		    default: // System error
		    	alert(splitData[1]);
			}
		}).fail(function(data) {
			console.log("Fejl i REST");
		});
	});
});

function updateRawMaterialBatch(form) {
	return $.ajax({
		url : "rest/raw_material_batch/update",
		type : "PUT",
		contentType : "application/json",
		data : form
	});
}

function createRawMaterialBatch(form) {
	return $.ajax({
		url : "rest/raw_material_batch/create",
		type : "POST",
		contentType : "application/json",
		data : form
	});
}

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

function getRawMaterialBatchListSpecific(rawMaterialId) {
	return $.ajax({
		url : "rest/raw_material_batch/read_list_specific",
		type : "POST",
		data: rawMaterialId,
		contentType: "application/json"
	});
}/**
 * 
 */

$(document).ready(function(){
	
/* Goes to the list of all recipes. */
$(document).on("click", ".recipe_list_link", function(event) {
	event.preventDefault();
	showRecipeListPage();
});

/* Goes to create recipe. */
$(document).on("click", ".recipe_create_link", function(event){
	event.preventDefault();
	$.get("src/html/recipe/recipe_create.html", function(template) {
		$("#content").html(template);
	});
})

/* Shows the recipe id and the recipe name and all of its components */
$(document).on("click", ".recipe_edit_table_link", function(event) {
	event.preventDefault();
	var recipeId = $(this).parents("tr").children("td:first").text();
	getRecipe(recipeId).done(function(data) {
		$.get("src/html/recipe/recipe_edit.html", function(template) {
			$("#content").html(Mustache.render($(template).html(), data))
			showRecipeCompsPage(recipeId);
		});
	})
	.fail(function(data){
		console.log(data);
	});
})

/* Adds recipe to the data*/
$(document).on("submit", "#recipe_create_form", function(event){
	event.preventDefault();
	createRecipe($(this).serializeJSON()).done(function(data) {
		var splitData = data.split(": ");
		switch(splitData[0]) {
		case "success": 
			var recipeId = $("input[name=\"recipeId\"]").val();
			getRecipe(recipeId).done(function(data) {
				$.get("src/html/recipe/recipe_edit.html", function(template) {
					$("#content").html(Mustache.render($(template).html(), data))
				});
			})
			.fail(function(data){
				console.log(data);
			});
			showRecipeCompsPage(recipeId)
			alert(splitData[1]);
			break;
		case "collision-error":
			alert(splitData[1]);
			break;
		default: //system error
			alert(splitData[1]);
		}
	})
	.fail(function(data) {
		console.log(data);
	});
})

/* Goes to edit component */
$(document).on("click", ".recipe_comp_edit_table_link", function(event) {
	event.preventDefault();
	var recipeId = $("input[name=\"recipeId\"]").val();
	var rawMaterialId = $(this).parents("tr").children("td:first").text();
	getRecipeComp(recipeId, rawMaterialId).done(function(data) {
		$.get("src/html/recipe/recipe_comp_edit.html", function(template) {
			$("#content").html(Mustache.render($(template).html(), data))
		});
	})
	.fail(function(data) {
		console.log(data);
	});
})

/* Updates the recipe component data in the "data file" and goes back to the page before. */
$(document).on("click", ".recipe_comp_edit_link", function(event) {
	event.preventDefault();
	updateRecipeComp().done(function(data){
		if(data == "success")
		{
		var recipeId = $("input[name=\"recipeId\"]").val();
		getRecipe(recipeId).done(function(data) {
			$.get("src/html/recipe/recipe_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
				showRecipeCompsPage(recipeId);
			});
		})
		.fail(function(data){
			console.log(data);
		});
	}
		else{
			alert("Ikke opdateret.")
		}
	})
	.fail(function(data) {
		console.log(data);
	})
	
	
})

})

/* Functions */
/* Creates a table of all the recipes in the system. And shows it in the section with id="content" */
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
	})
	.fail(function(data){
		console.log("System fejl")
		alert("System fejl")
	})
}

/* Creates a table of all the recipe components in a specific recipe. */
function showRecipeCompsPage(recipeId) {
	getRecipeContent(recipeId).done(function(data) {
		$.get("src/html/recipe/recipe_comp_list.html", function(template) {
			$("#recipe_edit_form").append(template);
			$.each(data, function(i, data) {
				console.log(data);
				$.get("src/html/recipe/recipe_comp_list_row.html", function(template) {
					$("#recipe_comp_list .table tbody").append(Mustache.render($(template).html(), data))
				});
			});
		});
	});
}

function updateRecipeComp() {
	return $.ajax({
		url: "rest/recipe_component/update",
		type: "PUT",
		contentType: "application/json",
		data: $("#recipe_comp_edit_form").serializeJSON()
	})
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
	})
}

function getRecipeContent(recipeId){
	return $.ajax({
		url: "rest/recipe_component/read_list_specific",
		type: "POST",
		data: recipeId,
		contentType: "application/json"
	})
}/**
 * 
 */

$(document).ready(function()
{	
	/* ################################# Main page button functions #################################### */
	/*  Calls the showRawMaterialListPage() function when button with class="raw_material_list_link is clicked"*/
	$(document).on("click", ".raw_material_list_link", function(event) {
		event.preventDefault();
		showRawMaterialListPage();
	});
	
	
	/* ################################# List page button functions #####################################*/
	/* Goes to raw_material_create.html when button with id="create_raw_material" is clicked. */
	$(document).on("click", ".raw_material_create_link", function(event){
		event.preventDefault();
		$.get("src/html/raw_material/raw_material_create.html", function(template) {
			$("#content").html(template);
		});
	});
	
	/* Edit raw material*/
	$(document).on("click", ".raw_material_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialId = $(this).parents("tr").children("td:first").text();
		getRawMaterial(rawMaterialId).done(function (data) {
			$.get("src/html/raw_material/raw_material_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
			});
		})
		.fail(function(data) {
			console.log(data);
		});
	});
	
	/* ######################### Create page button functions ########################################## */
	
	/* Sumbitting the form with id="raw_material_create_form" and go back to the raw material list page */
	$(document).on("submit", "#raw_material_create_form", function(event) {
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/create",
			type: "POST",
			contentType: "application/json",
			data: $("#raw_material_create_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
					{
					 showRawMaterialListPage();
					 alert("Råvaren blev tilføjet")
					}
				else
					{
						alert(data);
					}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
	
	
	/* ############################# Edit page button functions ####################################### */
	$(document).on("submit", "#raw_material_edit_form", function(event){
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/update",
			type: "PUT",
			contentType: "application/json",
			data: $("#raw_material_edit_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
					{
					showRawMaterialListPage();
					alert("Råvaren blev redigeret")
					}
				else
					{
					alert(data);
					}
			},
			error: function(data) {
				console.log(data);
			}
		})
	})
	
});

/* Functions */
/* Creates a table of all the raw materials in the system. And shows it in the section with id="content" */
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
	})
	.fail(function(data){
		console.log("System fejl")
		alert("System fejl")
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

	$("body").load("src/html/login.html", function() {
		$("#login_form input[name=\"id\"]").focus();
	});

	/*
	 * On sumbit login post request
	 * */
	$(document).on("submit", "#login_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/login/login-user',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				switch(data) {
				case "super_login":
					$.get("src/html/master.html", function(template) {
						$("body").html(template);
						$("#user_dropdown_menu .user_edit_link").hide();
						$(".top_nav_role").addClass("role_super");
						$(".role_super").text("Super");
						$(".top_nav_name").text("admin");
						
						getRoleTemplate("src/html/role_privilege/admin_privilege.html").done(function() {
							getRoleTemplate("src/html/role_privilege/pharmacist_privilege.html").done(function() {
								getRoleTemplate("src/html/role_privilege/foreman_privilege.html").done(function() {
									getRoleTemplate("src/html/role_privilege/labtech_privilege.html");
								});
							});
						  });
					});
					console.log(data);
					break;
				case "new_login":
					$.get("src/html/login_new_pass.html", function(template) {
			            $("body").html(template)		            
			        });
					console.log(data);
					break;
				case "true_login":
					var userId = $("#login_form input[name=\"id\"]").val();
					getUser(userId).done(function(data) {
						$.get("src/html/master.html", function(template) {
				            $("body").html(Mustache.render($(template).html(), data));
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
					break;
				default:
					console.log(data);
				}
			},
			error: function(data){
				console.log("Fejl i Login REST");
			}
		});
	});
});

function getRoleTemplate(template) {
	return $.get(template, function(template) {
		$("#side_panel > #side_nav > ul").append(template);
	});
}$(document).ready(function() {
	
	/** 
	 * Links
	 * **/
	
	// Vis min bruger link
	$(document).on("click", ".user_edit_link", function(event) {
		event.preventDefault();
		var userId = $(".top_nav_userid").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
	});
	
	// Vis alle brugere link + gå tilbage
	$(document).on("click", ".user_list_link", function(event) {
		event.preventDefault();
		showUserListPage();
	});
	
	// Opret bruger link
	$(document).on("click", ".user_create_link", function(event) {
		event.preventDefault();
		
		$.get("src/html/user/user_create.html", function(template) {
            $("#content").html(template);		            
        });
	});
	
	// Rediger bruger link
	$(document).on("click", ".user_edit_table_link", function(event) {
		event.preventDefault();
		var userId = $(this).parents("tr").children("td:first").text();
		getUser(userId).done(function(data) {
			$.get("src/html/user/user_edit.html", function(template) {
	            $("#content").html(Mustache.render($(template).html(),data))		            
	        });
		})
		.fail(function(x) {
			console.log("Fejl!");
		});
	});
	
	/** 
	 * Form submits
	 * **/
	$(document).on("submit", "#user_create_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/user/create',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				var splitData = data.split(": ");
				switch(splitData[0]) {
			    case "success":
			        alert(splitData[1]);
			        showUserListPage();
			        break;
			    case "input-error":
			    	alert(splitData[1]);
			        break;
			    case "id-error":
			    	alert(splitData[1]);
			    default:
			    	alert(splitData[1]);
				}
			},
			error: function(data){
				console.log("Fejl!")
				console.log(data);
			}
		});
		
	});
	
	$(document).on("submit", "#user_edit_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/user/update',
			type : 'PUT',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				console.log(data);
				switch(data) {
			    case "success":
			        alert("Brugeren blev redigeret");
			        showUserListPage();
			        break;
			    case "input-error":
			        alert("Input fejl");
			        break;
			    default:
			    	alert("System fejl");
				}
			},
			error: function(data){
				console.log("Fejl!")
				console.log(data);
			}
		});
		
	});
	
});

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
		console.log("Fejl!");
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