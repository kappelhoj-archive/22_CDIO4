package dataTransferObjects;

public class ProductBatchCompPOJO {

	private String pbId;
	private String rbId;
	
	public ProductBatchCompPOJO(){
		
	}
	
	public ProductBatchCompPOJO(String pbId, String rbId)
	{
		this.pbId = pbId;
		this.rbId = rbId;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public String getRbId() {
		return rbId;
	}

	public void setRbId(String rbId) {
		this.rbId = rbId;
	}
}
