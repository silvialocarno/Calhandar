package it.polimi.db2.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.calhandar.entities.Professor;
import it.polimi.db2.calhandar.services.StudentService;
import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.calhandar.services.ProfessorService;
import it.polimi.db2.calhandar.entities.Student;
import it.polimi.db2.calhandar.exceptions.CredentialsException;
import javax.persistence.NonUniqueResultException;
import org.apache.commons.validator.routines.EmailValidator;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.calhandar.services/ProfessorService")
	private ProfessorService professorService;
	@EJB(name = "it.polimi.db2.calhandar.services/StudentService")
	private StudentService studentService;

	public CheckLogin() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		// obtain and escape params
		String email = null;
		String pwd = null;
		try {
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			if (email == null || pwd == null || email.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}

		EmailValidator validator = EmailValidator.getInstance();
		if(!validator.isValid(email)) {
			ctx.setVariable("errorMsg", "Invalid email");
			String path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
			return;
		}

		String domain = email.split("@")[1];

		if(domain.equals("mail.polimi.it")) {
			Student student;
			try {
				// query db to authenticate for user
				student = studentService.checkCredentials(email, pwd);
			} catch (CredentialsException | NonUniqueResultException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
				return;
			}

			// If the user exists, add info to the session and go to home page, otherwise
			// show login page with error message

			String path;
			if (student == null) {
				ctx.setVariable("errorMsg", "Incorrect username or password");
				path = "/index.html";
				templateEngine.process(path, ctx, response.getWriter());
			} else {
				request.getSession().setAttribute("user", student);
				path = getServletContext().getContextPath() + "/Calhandar";
				response.sendRedirect(path);
			}
		}
		else {
			Professor professor;
			try {
				// query db to authenticate for user
				professor = professorService.checkCredentials(email, pwd);
			} catch (CredentialsException | NonUniqueResultException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
				return;
			}

			// If the user exists, add info to the session and go to home page, otherwise
			// show login page with error message

			String path;
			if (professor == null) {
				ctx.setVariable("errorMsg", "Incorrect username or password");
				path = "/index.html";
				templateEngine.process(path, ctx, response.getWriter());
			} else {
				request.getSession().setAttribute("user", professor);
				path = getServletContext().getContextPath() + "/Calhandar";
				response.sendRedirect(path);
			}
		}


	}

	public void destroy() {
	}
}