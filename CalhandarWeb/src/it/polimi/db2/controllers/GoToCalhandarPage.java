package it.polimi.db2.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.calhandar.services.*;
import it.polimi.db2.calhandar.entities.*;

@WebServlet("/Calhandar")
public class GoToCalhandarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.calhandar.services/ProfessorService")
	private ProfessorService professorService;
	@EJB(name = "it.polimi.db2.calhandar.services/StudentService")
	private StudentService studentService;


	public GoToCalhandarPage() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		String loginpath = getServletContext().getContextPath() + "/index.html";
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			response.sendRedirect(loginpath);
			return;
		}
		Professor professor = null;
		Student student = null;
		if(session.getAttribute("user") instanceof Professor){
			 professor = (Professor) session.getAttribute("user");
		}
		else
			student = (Student) session.getAttribute("user");

		List<Commitment> commitments = null;

		try {
			if(professor != null){
				commitments = professorService.findCommitmentsOfTheDayByProfessor(professor.getEmail());
			}
			else if (student != null)
				commitments = studentService.findCommitmentsOfTheDayByStudent(student.getEmail());
			commitments.sort(Comparator.comparing(Commitment::getStartTime));

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to get data");
			return;
		}

		// Redirect to the Home page and add missions to the parameters
		String path = "/WEB-INF/Calhandar.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("commitments", commitments);
		ctx.setVariable("date", LocalDate.now());

		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
