package dataTransferObjects;

public class RecipeDTO extends DTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5049520480592199167L;
	int recipeId;
	String recipeName;
	
	
	public RecipeDTO(){
		
	}
    
	public RecipeDTO(int recipeId, String recipeName)
	{
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }


	public int getRecipeId() {
		return recipeId;
	}


	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}


	public String getRecipeName() {
		return recipeName;
	}


	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}


	public String toString() { 
		return recipeId + "\t" + recipeName; 
	}
	
	public RecipeDTO copy(){
		return new RecipeDTO(recipeId, recipeName);
	}

	@Override
	public int compareTo(DTO o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
