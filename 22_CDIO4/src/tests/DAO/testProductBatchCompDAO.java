package tests.DAO;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccessObjects.ProductBatchCompDAO;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;


public class testProductBatchCompDAO {

	IProductBatchCompDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = new ProductBatchCompDAO();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}
	
	@Test
	public void testCreateAndGetProductBatchComp() {
		try {
			dao.createProductBatchComp(new ProductBatchCompDTO(1, 1, 1.1, 1.1, 11));
			
			
		} catch (DALException e) {
			e.printStackTrace();
			fail("Error" + e.getMessage());
		}
	}

}
