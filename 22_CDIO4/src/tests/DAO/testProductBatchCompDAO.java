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
	public void startTest(){
		try {
			dao.createProductBatchComp(new ProductBatchCompDTO(9, 9, 1.1, 1.1, 11));
		} catch (DALException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	//Positive Test
	@Test
	public void testCreateAndGetProductBatchComp() {
		try {
			ProductBatchCompDTO expected = new ProductBatchCompDTO(1, 1, 1.1, 1.1, 11);

			dao.createProductBatchComp(new ProductBatchCompDTO(1, 1, 1.1, 1.1, 11));

			ProductBatchCompDTO actual = dao.getProductBatchComp(1, 1);

			assertTrue(expected.equals(actual));

		} catch (DALException e) {
			e.printStackTrace();
			fail("Error" + e.getMessage());
		}
	}

	//Positive test
	@Test
	public void testUpdateProductBatchComp(){
		try {
			ProductBatchCompDTO expected = new ProductBatchCompDTO(1, 2, 1.2222, 8.1, 19);

			dao.createProductBatchComp(new ProductBatchCompDTO(1, 2, 1.1, 1.1, 11));
			dao.updateProductBatchComp(new ProductBatchCompDTO(1, 2, 1.2222, 8.1, 19));

			ProductBatchCompDTO actual = dao.getProductBatchComp(1, 2);

			assertTrue(expected.equals(actual));

		} catch (DALException e) {
			e.printStackTrace();
			fail("Error" + e.getMessage());
		}

	}
	
	//Negative Test
	@Test
	public void testCreateProductBatchCompFail() {
		
		boolean expected = true;
		boolean actual = false;
		
		try {
			
			dao.createProductBatchComp(new ProductBatchCompDTO(5, 5, 1.1, 1.1, 11));
			dao.createProductBatchComp(new ProductBatchCompDTO(5, 5, 2.1, 3.1, 44));

		} catch (DALException e) {
			System.out.println(e);
			actual = true;
		}
		
		assertEquals(expected, actual);
	}


	//Abstract test
	@Test
	public void testGetProductBatchCompList(){
		try {
			System.out.println(dao.getProductBatchCompList());
		} catch (DALException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

	}

	//Abstract test
	@Test
	public void testGetProductBatchCompListByID(){
		try {
			System.out.println(dao.getProductBatchCompList(1));
		} catch (DALException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

	}

	
	
}
//
