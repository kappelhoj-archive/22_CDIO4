package dataTransferObjects;

public class RecipeCompPOJO {
	
	private String id;
	private String raw_material_id;
	
	public RecipeCompPOJO()
	{
		
	}
	
	public RecipeCompPOJO(String id, String raw_material_id)
	{
		this.id = id;
		this.raw_material_id = raw_material_id;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRaw_material_id() {
		return raw_material_id;
	}

	public void setRaw_material_id(String raw_material_id) {
		this.raw_material_id = raw_material_id;
	}

}
