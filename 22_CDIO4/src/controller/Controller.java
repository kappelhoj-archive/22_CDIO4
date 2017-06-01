package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dataAccessObjects.IUserDAO;
import dataAccessObjects.UserDAO;
import dataTransferObjects.UserDTO;
import functionLayer.DataVerifier;
import functionLayer.IDataVerifier;
import functionLayer.IDataVerifier.WrongDataException;
import staticClasses.Validator;

@WebListener
public class Controller implements ServletContextListener {

	public static IUserDAO data;
	public static IDataVerifier func;
	private static boolean initialized = false;

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Listener initialized");

		if (!initialized) {
			initialized=true;
			data = new UserDAO(null);
			func = new DataVerifier(data);
			
			UserDTO[] users=new UserDTO[2];
			for(int i=0;i<users.length;i++){
				users[i]=new UserDTO();
			}
			users[0].setUserName("Bob Jensen");
			users[0].setCpr("1111111118");
			users[0].setIni("BJ");
			users[0].setUserId(11);
			users[0].setPassword("Gryrh4heyefe");
			users[0].addRole(Validator.validRoles[0]);
			
			users[1].setUserName("Bo Hansen");
			users[1].setCpr("1111111118");
			users[1].setIni("BH");
			users[1].setUserId(15);
			users[1].setPassword("Gryrh4thteefe");
			users[1].addRole(Validator.validRoles[3]);
			users[1].addRole(Validator.validRoles[2]);
			try {
				func.createUser(users[0]);
				func.createUser(users[1]);
			} catch (WrongDataException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Listener destroyed.\n");
	}

}
