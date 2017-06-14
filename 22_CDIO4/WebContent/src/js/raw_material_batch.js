$(document).ready(function() {
	
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
	            console.log(data);
				$.each(data, function(i, data) {
					$(".custom-select").append(Mustache.render("<option value=\""+ data.id + "\">(" + data.id + ") " + data.name + "</option>", data));
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
}