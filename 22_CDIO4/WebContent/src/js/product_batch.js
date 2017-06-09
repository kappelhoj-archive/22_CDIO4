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
		showProductBatchListPage();
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

function getProductBatchList() {
	return $.ajax({
		url : "rest/product_batch/read_list",
		type : "GET",
		contentType: "application/json"
	});
}