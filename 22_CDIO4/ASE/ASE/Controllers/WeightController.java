package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class WeightController implements Runnable {
	MeasurementController measurementAdder;
	Socket weightConnection;

	WeightController(MeasurementController measurementAdder, String ip, int port)
			throws UnknownHostException, IOException {
		this.measurementAdder = measurementAdder;
		weightConnection = new Socket(ip, port);
	}

	@Override
	public void run() {
		while(true){
			 startMeasureMent();
		}
	}
	
	public void startMeasureMent(){
		
	}
	
	public void login(){
		
	}
	
	
	
	public void handleReturnMessage(String messageSent, String answerReceived){
		switch(answerReceived){
			default:
				break;
		}
	}
	
}
