package controller.interfaces;

public interface ILoginController {
	boolean checkLogin(int id, String password);
	int generateAdminKey(int id);
}
