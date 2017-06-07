package dataTransferObjects;

public interface IWeightControlDTO {
	public String getIdentity();
	
	public void copy(IWeightControlDTO dto) throws RuntimeException;
}
