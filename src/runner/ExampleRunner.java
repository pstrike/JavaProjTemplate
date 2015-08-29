package runner;

import controller.Controller;
import controller.ExampleController;
import dao.utility.DBConnectionManager;

public class ExampleRunner {

	public static void main(String[] args) {
		Controller ctrl = new ExampleController();
		ctrl.action();
		
		// close DB connection if open before
		DBConnectionManager.getInstance().closeConnection();
	}
	
}
