package dataTransferObjects;

public class RawMaterialBatchDTO implements IWeightControlDTO
{
	int rbId;                     // i omraadet 1-99999999
	int raavareId;             // i omraadet 1-99999999
	double maengde;             // kan vaere negativ 

	public RawMaterialBatchDTO(int rbId, int raavareId, double maengde)
	{
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
	}
	
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public int getRaavareId() { return raavareId; }
	public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public double getMaengde() { return maengde; }
	public void setMaengde(double maengde) { this.maengde = maengde; }
	public String toString() { 
		return rbId + "\t" + raavareId +"\t" + maengde; 
	}
	
	public RawMaterialBatchDTO copy(){
		return new RawMaterialBatchDTO(rbId, raavareId, maengde);
	}

	@Override
	public String getIdentity() {
		return raavareId+"";
	}
}
