/**
 * 
 */

$(document).ready(function(){
	
	/* Create raw material javascript */
	
	$(document).on("submit", "#raw_material_create_form", function(event){
		event.preventDefault();
		$.ajax({
			url: "rest/raw_material/create",
			type: "POST",
			contentType: "application/json",
			data: $("#raw_material_create_form").serializeJSON(),
			success: function(data) {
				if(data == "success")
				{
					$.get("src/rawMaterialAdministration/raw_material_list.html", function(template) {
			            $("#content").html(Mustache.render($(template).html(), getRawMaterialList())		            
			        });
				}
				else
				{
					alert("data");
				}
			},
			error: function(data){
				console.log(data);
			}
		});
	});
});
	

function getRawMaterialList(){
	var json;
	$.ajax({
		url: "rest/raw_material/read_list",
		type: "GET",
		contentType: "application/json",
		success: function(data){
			json = data;
		}
	});
	return { getJson : function () {
		if(json) return json;
}
