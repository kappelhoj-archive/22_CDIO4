package ASE.DTOs;

import java.util.List;

import dataTransferObjects.ProductBatchCompDTO;

public class MeasurementDTO {
	private int newStatus;
	private int pbId;
	private List<ProductBatchCompDTO> measurements;

	public MeasurementDTO(int newStatus, int pbId, List<ProductBatchCompDTO> measurements) {
		super();
		this.newStatus = newStatus;
		this.pbId = pbId;
		this.measurements = measurements;
	}

	public int getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(int newStatus) {
		this.newStatus = newStatus;
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	public List<ProductBatchCompDTO> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<ProductBatchCompDTO> measurements) {
		this.measurements = measurements;
	}

}
