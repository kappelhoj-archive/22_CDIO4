package ASE.tests;

import ASE.Controllers.MeasurementController;
import ASE.Views.ConnectionManager;
import controller.Initializer;
import exceptions.DALException;

public class ASESystemTest {
	public static void main(String[] args){
		
		Initializer ini=new Initializer();
		ini.contextInitialized(null);
		try {
			System.out.println(Initializer.getProductBatchCompDAO().getProductBatchCompList());
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MeasurementController measureCon =new MeasurementController(Initializer.getProductBatchCompDAO(), Initializer.getProductBatchDAO());
		ConnectionManager conMan=new ConnectionManager(null,measureCon);
		conMan.threadStarter();
		new Thread(measureCon).start();
		
	}
}
