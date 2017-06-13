package dataTransferObjects;

public class RawMaterialBatchDTO extends DTO implements IWeightControlDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8926764359117199554L;
	int rbId;                     // i omraadet 1-99999999
	int rawMaterialId;             // i omraadet 1-99999999
	double amount;             // kan vaere negativ 

	public RawMaterialBatchDTO() {
		
	}
	
	public RawMaterialBatchDTO(int rbId, int rawMaterialId, double amount)
	{
		this.rbId = rbId;
		this.rawMaterialId = rawMaterialId;
		this.amount = amount;
	}
	

	public int getRbId() {
		return rbId;
	}


	public void setRbId(int rbId) {
		this.rbId = rbId;
	}


	public int getRawMaterialId() {
		return rawMaterialId;
	}


	public void setRawMaterialId(int rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String toString() { 
		return rbId + "\t" + rawMaterialId +"\t" + amount; 
	}
	
	public RawMaterialBatchDTO copy(){
		return new RawMaterialBatchDTO(rbId, rawMaterialId, amount);
	}

	@Override
	public String getIdentity() {
		return rawMaterialId+"";
	}


	@Override
	public void copy(IWeightControlDTO dto) throws RuntimeException {
		if(dto instanceof RawMaterialBatchDTO){
			RawMaterialBatchDTO castDTO=(RawMaterialBatchDTO) dto;
			this.amount=castDTO.getAmount();
			this.rawMaterialId=castDTO.getRawMaterialId();
			this.rbId=castDTO.getRbId();
		}
		else{
			throw new RuntimeException("Invalid DTO");
		}
		
	}

	@Override
	public int compareTo(DTO o) {
		return this.rbId - ((RawMaterialBatchDTO) o).getRbId();
	}
}
