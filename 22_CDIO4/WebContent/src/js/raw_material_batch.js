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
	
	// venter med den her
	$(document).on("submit", "#raw_material_batch_create_form", function(event) {
		event.preventDefault();
		
		$.ajax({
			url : 'rest/raw_material_batch/create',
			type : 'POST',
			contentType : "application/json",
			data : $(this).serializeJSON(),
			success : function(data) {
				var splitData = data.split(": ");
				switch(data[0]) {
			    case "success":
			        alert(data[1]);
			        showUserListPage();
			        break;
			    case "input-error":
			        alert(data[1]);
			        break;
			    default: // System error
			    	alert(data[1]);
				}
			},
			error: function(data){
				console.log("Fejl i REST")
				console.log(data);
			}
		});
	});
});

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
		url : "rest/raw_material_batch/read_all",
		type : "GET",
		contentType: "application/json"
	});
}

function getRawMaterialBatchListSpecific(rawMaterialId) {
	return $.ajax({
		url : "rest/raw_material_batch/read_all_specific",
		type : "POST",
		data: rawMaterialId,
		contentType: "application/json"
	});
}