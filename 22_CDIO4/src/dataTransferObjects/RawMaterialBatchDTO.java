package dataTransferObjects;

public class RawMaterialBatchDTO implements IWeightControlDTO
{
	int rbId;                     // i omraadet 1-99999999
	int rawMaterialId;             // i omraadet 1-99999999
	double amount;             // kan vaere negativ 

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
}
