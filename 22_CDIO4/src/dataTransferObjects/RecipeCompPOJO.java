package dataTransferObjects;

public class RecipeCompPOJO {
	
	private String recipeId;
	private String rawMaterialId;
	
	public RecipeCompPOJO()
	{
		
	}
	
	public RecipeCompPOJO(String recipeId, String rawMaterialId)
	{
		this.recipeId = recipeId;
		this.rawMaterialId = rawMaterialId;
		
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getRawMaterialId() {
		return rawMaterialId;
	}

	public void setRawMaterialId(String rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}





}
