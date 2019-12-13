package com.techouts.aemindustry.core.extra;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Session;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

@SlingServlet(paths = { "/bin/path/language" }, methods = { "GET", "POST" })
@Properties({ @Property(name = "service.pid", value = "com.techouts.aemindustry.core.extra.PracticeJason"),
		@Property(name = "service.description", value = "Hey Imad Data Stored"), })
public class PracticeJason extends SlingAllMethodsServlet {

	private Session session;

	private List<String> values;

	private Set<Entry<Integer, String>> set;

	public Map<Integer, String> getSet() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		map.put(1, "English");
		map.put(2, "Kannada");
		map.put(3, "Hinde");
		map.put(4, "Tamil");
		map.put(5, "Urdu");
		map.put(6, "Telgu");
		map.put(7, "Bengali");
		return map;
	}

	public List<String> getValues() {
		List<String> books = new ArrayList<>();
		books.add("java");
		books.add("C");
		books.add("C++");
		books.add("C#");
		return books;
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, org.apache.sling.api.SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("Hey Welcome");

		try {

			ResourceResolver resolver = request.getResourceResolver();
			session = resolver.adaptTo(Session.class);

			HashMap<Integer, String> map = new HashMap<Integer, String>();

			map.put(1, "English");
			map.put(2, "Kannada");
			map.put(3, "Hinde");
			map.put(4, "Tamil");
			map.put(5, "Urdu");
			map.put(6, "Telgu");
			map.put(7, "Bengali");

			pw.println("Imad Knows Languages are" + map);

			Set<Entry<Integer, String>> set = map.entrySet();
			Iterator<Integer> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				Integer key = iterator.next();
				String value = map.get(key);
				pw.print("The Key is :" + key + ", And Value is :" + value + "<br>");

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.logout();
			}
		}

	}

}
