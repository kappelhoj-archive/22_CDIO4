package dataTransferObjects;

import java.io.Serializable;

public abstract class DTO implements Comparable<DTO>, Serializable  {
	
	private static final long serialVersionUID = 7145864589999999260L;
	
	public boolean equals(DTO dto){
		if(this.toString().equals(dto.toString()))
			return true;
		else
			return false;
	}
}
