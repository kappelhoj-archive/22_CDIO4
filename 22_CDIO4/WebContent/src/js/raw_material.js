/**
 * 
 */

$(document).ready(function()
{
	
	/* Create raw material javascript */
	
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
					getRawMaterialList().done(function(data){
						$.get("src/raw_material/raw_material_list.html", function(template) {
							$("#content").html(Mustache.render($(template).html(),data))
						});
					})
					.fail(function(data){
						console.log(data);
					});
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
	
});
	

function getRawMaterialList(){
	return $.ajax({
		url: "rest/raw_material/read_list",
		type: "GET",
		contentType: "application/json"
	});
}
