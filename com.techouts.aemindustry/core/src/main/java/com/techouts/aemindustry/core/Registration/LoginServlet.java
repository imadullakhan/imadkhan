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
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=/bin/path/registerlogin", "sling.servlet.selectors=" + "sample",
		"sling.core.servletName=" + "service.pid"

})
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	private LoginDao login;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter pw = resp.getWriter();

		resp.setContentType("text/html");

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);

		

		try {
			if (login.validate(user)) {

				HttpSession session = req.getSession(true);
				//pw.println("SessionId" + session.getId());
				session.setAttribute("username", username);
				
				/*Cookie cookie = new Cookie(username, username);
				  resp.addCookie(cookie);*/

				pw.println("<h2>" + "You Have logged in SuccessFully...." + username + "</h2>");
				pw.println("<a href = '/content/aemindustry/en/logout.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#fff;text-decoration:none;border-radius:5px;'>Press Here For Logout</a>");

				// resp.sendRedirect("/content/aemindustry/en/aboutus.html");
			} else {

				HttpSession session = req.getSession();
				session.setAttribute("user", username);
				pw.println("<h2>" + "Password Incorrect Please Try Again..!" + username + "</h2>");
				
				pw.println("<a href = '/content/aemindustry/en/Reset.html' style='width:80px;height:80px;padding:12px;background-color:teal;color:#33ccff;text-decoration:none;border-radius:5px;'>Forget Password</a>");

				// resp.sendRedirect("login.jsp");
			}

		} catch (Exception e) {
			pw.println("Exception" + e);
			pw.println(e.getMessage());
			e.printStackTrace();

		}
		// close Stream
		pw.close();

	}

}
