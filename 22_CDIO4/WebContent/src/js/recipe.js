$(document).ready(function(){
	
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
				$("#content").html(Mustache.render($(template).html(), data))
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
		$.get("src/html/recipe/recipe_component_create.html", function(template) {
			$("#content").html(template);
			$("#recipe_component_create_form input[name=\"recipeId\"]").val(recipeId);
		})
		
	});
	
	/*
	 * Submit forms
	 * */
	
	// Submit create recipe form
	$(document).on("submit", "#recipe_create_form", function(event){
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		createRecipe($(this).serializeJSON()).done(function(data) {
			getResponseMessageAndRedirect(data, function() { return showRecipeEditPage(recipeId) });
		}).fail(function(data) {
			console.log("Fejl i Recipe REST");
		});
	})
	
	// Submit edit recipe component form
	$(document).on("submit", "#recipe_comp_edit_form", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		updateRecipeComp($(this).serializeJSON()).done(function(data) {
			getResponseMessageAndRedirect(data, function() { return showRecipeEditPage(recipeId) });
		}).fail(function(data) {
			console.log("Fejl i RecipeComp REST");
		});
	});
	
	// Submit create recipe component form
	$(document).on("submit", "#recipe_component_create_form", function(event) {
		event.preventDefault();
		var recipeId = $("input[name=\"recipeId\"]").val();
		createRecipeComp($(this).serializeJSON()).done(function(data) {
			getResponseMessageAndRedirect(data, function() { return showRecipeEditPage(recipeId) });
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
	getRecipeContent(recipeId).done(function(data) {
		$.get("src/html/recipe/recipe_comp_list.html", function(template) {
			$("#recipe_edit_form").append(template);
			$.each(data, function(i, data) {
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
		console.log("Fejl i Recipe Comp REST");
	});
	
	
}

/*
 * REST functions
 * */

function updateRecipeComp(form) {
	return $.ajax({
		url: "rest/recipe_component/update",
		type: "PUT",
		contentType: "application/json",
		data: form
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
}

function getRecipeContent(recipeId){
	return $.ajax({
		url: "rest/recipe_component/read_list_specific",
		type: "POST",
		data: recipeId,
		contentType: "application/json"
	});
}