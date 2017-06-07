package dataTransferObjects;

public abstract class DTO {
	public boolean equals(DTO dto){
		if(this.toString().equals(dto.toString()))
			return true;
		else
			return false;
	}
}
