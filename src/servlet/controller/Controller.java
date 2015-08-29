package servlet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller {
	public abstract void action(HttpServletRequest req, HttpServletResponse resp);
}
