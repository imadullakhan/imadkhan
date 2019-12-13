package com.techouts.aemindustry.core.Registration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/Logout", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})
public class Logout extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter pw = resp.getWriter();

		resp.setContentType("text/html");

		HttpSession session = req.getSession(false);
		
				

		try {

			if (session != null) {
				// pw.println("SessionId" + session.getId());
				session.removeAttribute("user");
				session.invalidate();				
				pw.println("Your SuccessFully Logout ...!");
				pw.println(
						"<a href = '/content/aemindustry/en/home.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Home Page</a>");

			} else {
				pw.println("Still Your In Login Mode");
			}

		} catch (Exception e) {
			pw.println("Exception" + e);
			e.printStackTrace();
		}

	}

}
