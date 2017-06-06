package dataTransferObjects;

public class ProductBatchDTO implements IWeightControlDTO
{
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int recipeId;
	
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
	public int getReceptId() { return recipeId; }
	public void setReceptId(int receptId) { this.recipeId = receptId; }
	public String toString() { return pbId + "\t" + status + "\t" + recipeId; }

	public ProductBatchDTO copy() {
		return new ProductBatchDTO(pbId, status, recipeId);
	}

	@Override
	public String getIdentity() {
		return recipeId+"";
	}
}

