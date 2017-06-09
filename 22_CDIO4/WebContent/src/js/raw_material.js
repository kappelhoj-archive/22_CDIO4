/**
 * 
 */

$(document).ready(function()
{	
	/* ################################# Main page button functions #################################### */
	/*  Calls the showRawMaterialListPage() function when button with class="raw_material_list_link is clicked"*/
	$(document).on("click", ".raw_material_list_link", function(event) {
		event.preventDefault();
		showRawMaterialListPage();
	});
	
	
	/* ################################# List page button functions #####################################*/
	/* Goes to raw_material_create.html when button with id="create_raw_material" is clicked. */
	$(document).on("click", ".raw_material_create_link", function(event){
		event.preventDefault();
		$.get("src/html/raw_material/raw_material_create.html", function(template) {
			$("#content").html(template);
		});
	});
	
	/* Edit raw material*/
	$(document).on("click", ".raw_material_edit_table_link", function(event) {
		event.preventDefault();
		var rawMaterialId = $(this).parents("tr").children("td:first").text();
		getRawMaterial(rawMaterialId).done(function (data) {
			$.get("src/html/raw_material/raw_material_edit.html", function(template) {
				$("#content").html(Mustache.render($(template).html(), data))
			});
		})
		.fail(function(data) {
			console.log(data);
		});
	});
	
	/* ######################### Create page button functions ########################################## */
	
	/* Sumbitting the form with id="raw_material_create_form" and go back to the raw material list page */
	$(document).on("submit", "#raw_material_create_form", function(event) {
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/create",
			type: "POST",
			contentType: "application/json",
			data: $("#raw_material_create_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
					{
					 showRawMaterialListPage();
					 alert("Råvaren blev tilføjet")
					}
				else
					{
						alert(data);
					}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
	
	
	/* ############################# Edit page button functions ####################################### */
	$(document).on("submit", "#raw_material_edit_form", function(event){
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/update",
			type: "PUT",
			contentType: "application/json",
			data: $("#raw_material_edit_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
					{
					showRawMaterialListPage();
					alert("Råvaren blev redigeret")
					}
				else
					{
					alert(data);
					}
			},
			error: function(data) {
				console.log(data);
			}
		})
	})
	
});

/* Functions */
/* Creates a table of all the raw materials in the system. And shows it in the section with id="content" */
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
	})
	.fail(function(data){
		console.log("System fejl")
		alert("System fejl")
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