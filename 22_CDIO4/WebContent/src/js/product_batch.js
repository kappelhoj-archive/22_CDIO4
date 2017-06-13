$(document).ready(function() {
	
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
		$.get("src/html/product_batch/product_batch_create.html", function(template) {
            $("#content").html(template)
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
				$(".pb_status").addClass("status_"+statusCode);
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
			$.each(data, function(i, data) {
				$.get("src/html/product_batch/product_batch_comp_list_row.html", function(template) {
					$("#product_batch_comp_list .table tbody").append(Mustache.render($(template).html(), data));
				});
			});
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
}