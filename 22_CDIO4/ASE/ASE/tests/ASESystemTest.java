package ASE.tests;

import ASE.Controllers.MeasurementController;
import ASE.Views.ConnectionManager;
import controller.Initializer;

public class ASESystemTest {
	public static void main(String[] args){
		
		Initializer ini=new Initializer();
		MeasurementController measureCon =new MeasurementController(Initializer.getProductBatchCompDAO(), Initializer.getProductBatchDAO());
		ConnectionManager conMan=new ConnectionManager(null,measureCon);
		conMan.threadStarter();
		
	}
}
