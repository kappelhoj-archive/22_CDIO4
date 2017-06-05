package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.ProduktBatchKompDAO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.DALException;

public class MyProduktBatchKompDAO implements ProduktBatchKompDAO {

	class DoubleInteger {
		int x;
		int y;

		int getX(){return x;}
		int getY(){return y;}

		public DoubleInteger(int x, int y){this.x = x; this.y = y;}
	}

	static Hashtable<DoubleInteger, ProduktBatchKompDTO> productBatchCompList = new Hashtable<DoubleInteger, ProduktBatchKompDTO>();

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {

		if(productBatchCompList.get(new DoubleInteger(pbId, rbId)) != null)
			return productBatchCompList.get(new DoubleInteger(pbId, rbId)).copy();

		else
			throw new DALException("Unknown Product Batch Comp ID: " + rbId);
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		List<ProduktBatchKompDTO> productbsc = new ArrayList<ProduktBatchKompDTO>();

		Set<DoubleInteger> keys = productBatchCompList.keySet();

		for(DoubleInteger key : keys){
			if(productBatchCompList.get(key).getPbId() == pbId)
				productbsc.add(productBatchCompList.get(key).copy());
		}

		return productbsc;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		List<ProduktBatchKompDTO> productbsc = new ArrayList<ProduktBatchKompDTO>();

		Set<DoubleInteger> keys = productBatchCompList.keySet();

		for(DoubleInteger key : keys){
			productbsc.add(productBatchCompList.get(key).copy());
		}

		return productbsc;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {

		if (productBatchCompList.putIfAbsent(new DoubleInteger(produktbatchkomponent.getPbId(), produktbatchkomponent.getRbId()), produktbatchkomponent.copy()) == null)
			return;

		else
			throw new DALException("Product Batch ID:"+produktbatchkomponent.getPbId()+" already exists !");


	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		productBatchCompList.replace(new DoubleInteger(produktbatchkomponent.getPbId(), produktbatchkomponent.getRbId()), produktbatchkomponent.copy());

	}

}
