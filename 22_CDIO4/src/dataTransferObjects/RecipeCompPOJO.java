package dataTransferObjects;

public class RecipeCompPOJO {
	
	private String nomNetto;
	private String rawMaterialId;
	private String tolerance;
	
	public RecipeCompPOJO()
	{
		
	}
	
	public RecipeCompPOJO(String raw_material_id, String nomNetto, String tolerance)
	{
		
		this.rawMaterialId = raw_material_id;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
		
	}


	public String getRawMaterialId() {
		return rawMaterialId;
	}

	public void setRawMaterialId(String rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}

	public String getNomNetto() {
		return nomNetto;
	}

	public void setNomNetto(String nomNetto) {
		this.nomNetto = nomNetto;
	}

	
	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}


}
