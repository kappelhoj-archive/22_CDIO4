$(document).ready(function() {
	
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
	
	$(document).on("submit", "#raw_material_batch_edit_form", function(event) {
		event.preventDefault();
		updateRawMaterialBatch($(this).serializeJSON()).done(function(data) {
			var splitData = data.split(": ");
			switch(splitData[0]) {
		    case "success":
		        alert(splitData[1]);
		        showRawMaterialBatchListPage();
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
}