package com.techouts.aemindustry.core.extra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;



@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Query Builder servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/demo/querybuilder" })

public class QueryBuilderServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(QueryBuilderServlet.class);

	@Reference
	private QueryBuilder builder;

	private Session session;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		try {

			log.info("<------(Executing Query Builder Servlet)------------>");

			String param = request.getParameter("param");

			log.info("Search item is: {}", param);

			ResourceResolver resourceResolver = request.getResourceResolver();

			session = resourceResolver.adaptTo(Session.class);

			Map<String, String> predicts = new HashMap<>();

			predicts.put("path", "/content/dam");
			predicts.put("type", "dam:Asset");
			predicts.put("group.p.or", "true");
			predicts.put("group.1_fulltext", param);
			predicts.put("group.1_fulltext.relPath", "jcr:content");

			Query query = builder.createQuery(PredicateGroup.create(predicts), session);

			query.setStart(0);
			query.setHitsPerPage(20);

			SearchResult searchResult = query.getResult();

			for (Hit hit : searchResult.getHits()) {

				String path = hit.getPath();

				response.getWriter().println(path);

			}
		} catch (Exception e) {
			e.printStackTrace();

			log.error(e.getMessage(), e);

		}

		finally {
			if (session != null) {
				session.logout();
			}
		}

	}

}
