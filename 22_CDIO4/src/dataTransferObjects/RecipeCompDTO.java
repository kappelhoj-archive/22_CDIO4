package dataTransferObjects;

public class RecipeCompDTO
{
	int recipeId;                  // auto genereres fra 1..n   
	int rawMaterialId;             // i omraadet 1-99999999
	double nomNetto;            // skal vaere positiv og passende stor
	double tolerance;           // skal vaere positiv og passende stor

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
}
