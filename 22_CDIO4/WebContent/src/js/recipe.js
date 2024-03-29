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
}