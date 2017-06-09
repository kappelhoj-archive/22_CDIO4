/**
 * 
 */

$(document).ready(function(){
	
/* ################################ Main page button functions ######################################### */

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
		});
	})
	.fail(function(data){
		console.log(data);
	});
	showRecipeCompsPage(recipeId);
})

$(document).on("sumbit", "#recipe_create_form", function(event){
	event.preventDefault();
	createRecipe((this).serializeJSON()).done(function(data) {
		var splitData = data.split(": ");
		switch(splitdata[0]) {
		case "success": 
			showRecipeListPage();
			alert(splitData[1]);
			break;
		case "collision-error":
			alert(splitData[1]);
			break;
		case "system-error":
			alert(splitData[1]);
			break;
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

function createRecipe(form) {
	return $.ajax({
		url: "rest/recipe/create",
		type: "POST",
		contentType: "application/json",
		data: form
		})
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