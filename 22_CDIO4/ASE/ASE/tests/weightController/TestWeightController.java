package ASE.tests.weightController;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Controllers.WeightController;

public class TestWeightController {
	WeightController wCon;

	@Before
	public void setUp() throws Exception {
		wCon=new WeightController(null, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
