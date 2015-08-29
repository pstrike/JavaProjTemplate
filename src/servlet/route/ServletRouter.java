package servlet.route;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.utility.DBConnectionManager;
import servlet.controller.Controller;
import servlet.controller.ControllerFactory;

public class ServletRouter extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String doType = req.getParameter("do");
		
		ControllerFactory factory = new ControllerFactory();
		Controller ctrl = factory.createController(doType);
		
		ctrl.action(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doGet(req, resp);
	}

	@Override
	public void destroy() {
		DBConnectionManager.getInstance().closeConnection();
		
		super.destroy();
	}

}
