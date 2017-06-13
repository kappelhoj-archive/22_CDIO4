package dataTransferObjects;

public class RecipeCompDTO extends DTO
{

	private static final long serialVersionUID = -6282952596437001174L;
	int recipeId;                    
	int rawMaterialId;            
	double nomNetto;            
	double tolerance;           
	
	public RecipeCompDTO() {
		
	}
	
	public RecipeCompDTO(int recipeId, int rawMaterialId, double nomNetto, double tolerance)
	{
		this.recipeId = recipeId;
		this.rawMaterialId = rawMaterialId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}


	public int getRecipeId() {
		return recipeId;
	}


	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}


	public int getRawMaterialId() {
		return rawMaterialId;
	}


	public void setRawMaterialId(int rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}


	public double getNomNetto() {
		return nomNetto;
	}


	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}


	public double getTolerance() {
		return tolerance;
	}


	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}


	public String toString() { 
		return recipeId + "\t" + rawMaterialId + "\t" + nomNetto + "\t" + tolerance; 
	}

	public RecipeCompDTO copy(){
		return new RecipeCompDTO(recipeId, rawMaterialId, nomNetto, tolerance);
	}

	@Override
	public int compareTo(DTO o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
