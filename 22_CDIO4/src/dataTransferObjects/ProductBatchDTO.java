package dataTransferObjects;

public class ProductBatchDTO extends DTO implements IWeightControlDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5415559033194916090L;
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int recipeId;
	
	public ProductBatchDTO() {
		
	}
	
	public ProductBatchDTO(int pbId, int status, int recipeId)
	{
		this.pbId = pbId;
		this.status = status;
		this.recipeId = recipeId;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
	public String toString() { return pbId + "\t" + status + "\t" + recipeId; }

	public ProductBatchDTO copy() {
		return new ProductBatchDTO(pbId, status, recipeId);
	}

	@Override
	public String getIdentity() {
		return recipeId+"";
	}

	@Override
	public void copy(IWeightControlDTO dto) throws RuntimeException {
		if(dto instanceof ProductBatchDTO){
			ProductBatchDTO castDTO=(ProductBatchDTO) dto;
			this.pbId=castDTO.getPbId();
			this.recipeId=castDTO.getRecipeId();
			this.status=castDTO.getStatus();
		}
		else{
			throw new RuntimeException("Invalid DTO");
		}
	}

	@Override
	public int compareTo(DTO o) {
		return this.pbId - ((ProductBatchDTO) o).getPbId();
	}
	
}

