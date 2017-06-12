/**
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
	showRecipeEditPage(recipeId);
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
$(document).on("submit", "#recipe_comp_edit_form", function(event) {
	event.preventDefault();
	updateRecipeComp($(this).serializeJSON()).done(function(data){
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

/* Go to create recipe component page */
$(document).on("click", ".recipe_comp_create_link", function(event) {
	event.preventDefault();
	var recipeId = $("#recipe_edit_form input[name=\"recipeId\"]").val();
	$.get("src/html/recipe/recipe_component_create.html", function(template) {
		$("#content").html(template);
		$("#recipe_component_create_form input[name=\"recipeId\"]").val(recipeId);
	})
	
})

$(document).on("submit", "#recipe_component_create_form", function(event) {
	event.preventDefault();
	createRecipeComp($(this).serializeJSON()).done(function(data) {
		var splitData = data.split(": ");
		switch(splitData[0]) {
		case "success": 
			var recipeId = $("#recipe_component_create_form input[name=\"recipeId\"]").val();
			showRecipeEditPage(recipeId);
			alert(splitData[1]);
			break;
		default: //system error
			alert(splitData[1]);
	}
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

function showRecipeEditPage(recipeId) {
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

function updateRecipeComp(form) {
	return $.ajax({
		url: "rest/recipe_component/update",
		type: "PUT",
		contentType: "application/json",
		data: form
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

function createRecipeComp(form) {
	return $.ajax({
		url: "rest/recipe_component/create",
		type: "POST",
		contentType: "application/json",
		data: form
	})
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
}