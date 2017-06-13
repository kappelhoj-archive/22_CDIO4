$(document).ready(function()
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
		});
	});
	
	// Link to edit raw material page
	$(document).on("click", ".raw_material_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialId = $(this).parents("tr").children("td:first").text();
		getRawMaterial(rawMaterialId).done(function (data) {
			$.get("src/html/raw_material/raw_material_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
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
}