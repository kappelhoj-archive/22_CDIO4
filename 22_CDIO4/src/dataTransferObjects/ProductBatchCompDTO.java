package dataTransferObjects;

public class ProductBatchCompDTO extends DTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1192760162109693059L;
	int pbId; 	  // produktbatchets id
	int rbId;        // i omraadet 1-99999999
	double tara;
	double netto;
	int userId;					// operatoer-nummer


	public ProductBatchCompDTO(int pbId, int rbId, double tara, double netto, int oprId)
	{
		this.pbId = pbId;
		this.rbId = rbId;
		this.tara = tara;
		this.netto = netto;
		this.userId = oprId;
	}

	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public double getTara() { return tara; }
	public void setTara(double tara) { this.tara = tara; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }
	public int getUserId() { return userId; }
	public void setUserId(int oprId) { this.userId = oprId; }
	public String toString() { 
		return pbId + "\t" + rbId +"\t" + tara +"\t" + netto + "\t" + userId ; 
	}

	public ProductBatchCompDTO copy(){
		return new ProductBatchCompDTO(pbId, rbId, tara, netto, userId);
	}

	public boolean equals(ProductBatchCompDTO dto){
		if(this.toString().equals(dto.toString()))
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(DTO o) {
		int a = this.pbId - ((ProductBatchCompDTO) o).getPbId();
		
		if(a ==0)
			a = this.rbId - ((ProductBatchCompDTO) o).getRbId();
		
		return a;
	}
}
