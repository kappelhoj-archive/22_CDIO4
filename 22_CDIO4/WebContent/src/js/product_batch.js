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
		getRecipeList().done(function(data) {
			$.get("src/html/product_batch/product_batch_create.html", function(template) {
	            $("#content").html(template);
				$.each(data, function(i, data) {
					$(".custom-select").append(Mustache.render("<option value=\""+ data.recipeId + "\">" + data.recipeId + " (" + data.recipeName + ")</option>", data));
				});
				validateProductBatch("#product_batch_create_form");
	        });
		}).fail(function(data){
			console.log("Fejl i Recipe REST")
		});
	});
	
	// Link to edit product batch page
	$(document).on("click", ".product_batch_edit_table_link", function(event) {
		event.preventDefault();
		var productBatchId = $(this).parents("tr").children("td:first").text();
		getProductBatch(productBatchId).done(function(data) {
			var statusCode = data.status;
			$.get("src/html/product_batch/product_batch_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data));
				$(".pb_status").text(pbStatusCodeToText(data.status));
				// raw material id must be parsed as a string ("")
				getRecipe("" + data.recipeId).done(function(data) {
					$("input[name=\"recipeName\"]").val(data.recipeName);
				}).fail(function(data){
					console.log("Fejl i Recipe REST")
				});
				showProductBatchCompsPage(productBatchId);
				$(".pb_status").addClass("status_"+statusCode)
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
				data["statusText"] = pbStatusCodeToText(data.status);
				$.get("src/html/product_batch/product_batch_list_row.html", function(template) {
		            $("#product_batch_list .table tbody").append(Mustache.render($(template).html(),data));	            
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
			
			if(data.length > 0) {
				$("#product_batch_comp_list .product_batch_comp_list_table").html(
						"<table class=\"table table-hover\">"
							+"<thead>"
								+"<tr>"
									+"<th>Produkt batch id</th>"
									+"<th>Råvare batch id</th>"
									+"<th>Tara</th>"
									+"<th>Netto</th>"
									+"<th>Bruger id</th>"
								+"</tr>"
							+"</thead>"
							+"<tbody></tbody>"
						+"</table>");
				$.each(data, function(i, data) {
					$.get("src/html/product_batch/product_batch_comp_list_row.html", function(template) {
						$("#product_batch_comp_list .table tbody").append(Mustache.render($(template).html(), data));
					});
				});
			}
		});
	});
}

// Change status code of a product batch to a string
function pbStatusCodeToText(statusCode) {
	var statusText;
	switch(statusCode) {
	case 0:
		statusText = "Ikke påbegyndt";
		break;
	case 1:
		statusText = "Under produktion";
		break;
	case 2:
		statusText = "Afsluttet";
		break;
	default:
		console.log("Fejl i status kode");
		break;
	}
	return statusText;
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