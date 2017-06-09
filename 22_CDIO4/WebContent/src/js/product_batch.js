$(document).ready(function() {
	
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
			$.get("src/html/product_batch/product_batch_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data));
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
				$.get("src/html/product_batch/product_batch_list_row.html", function(template) {
		            $("#product_batch_list .table tbody").append(Mustache.render($(template).html(),data))
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
				console.log(data);
				$.get("src/html/product_batch/product_batch_comp_list_row.html", function(template) {
					$("#product_batch_comp_list .table tbody").append(Mustache.render($(template).html(), data))
				});
			});
		});
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
}