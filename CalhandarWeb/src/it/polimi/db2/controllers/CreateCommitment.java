package it.polimi.db2.controllers;

import com.google.gson.GsonBuilder;
import it.polimi.db2.calhandar.entities.Commitment;
import it.polimi.db2.calhandar.entities.Professor;
import it.polimi.db2.calhandar.entities.Room;
import it.polimi.db2.calhandar.services.CommitmentService;
import it.polimi.db2.calhandar.services.RoomService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.thymeleaf.TemplateEngine;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import org.thymeleaf.context.WebContext;

@WebServlet("/CreateCommitment")
@MultipartConfig
public class CreateCommitment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.calhandar.services/CommitmentService")
	private CommitmentService cService;
	@EJB(name = "it.polimi.db2.calhandar.services/RoomService")
	private RoomService roomService;

	public CreateCommitment() {
		super();
	}

	public void init() throws ServletException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}

		Professor professor = (Professor) session.getAttribute("user");


		// Get and parse all parameters from request
		String name = request.getParameter("name");
		Date startTime = null;
		Date endTime = null;
		String material = null;
		String topic = null;
		String recording = null;
		String subject = null;
		boolean replica = false;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			startTime = (Date) sdf.parse(request.getParameter("startTime"));
			endTime = (Date) sdf.parse(request.getParameter("endTime"));
		if (!StringUtils.isBlank(request.getParameter("material"))){
			material = request.getParameter("material");
		}
		if (!StringUtils.isBlank(request.getParameter("recording"))){
			recording = request.getParameter("recording");
		}
		if (!StringUtils.isBlank(request.getParameter("topic"))){
			topic = request.getParameter("topic");
		}
		if (!StringUtils.isBlank(request.getParameter("subjectId"))){
			subject = request.getParameter("subjectId");
		}
		String isReplica = request.getParameter("isReplica");
		if(isReplica.equals("Yes"))
			replica=true;
		else if(isReplica.equals("No"))
			replica=false;
		else
			replica=false;
		} catch (NumberFormatException | NullPointerException | ParseException e) {
			e.printStackTrace();
		}

		Commitment commitment = cService.createCommitment(name, professor, startTime, endTime, material, recording, topic, subject, replica);

		// OLD CODE
		/*
		request.getSession().setAttribute("commitment", commitment);
		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/RoomPage";
		response.sendRedirect(path);
		*/

		/* *** NEW CODE FOR POPUP *** */
		Room room = roomService.findFreeRoom(commitment.getStartTime(), commitment.getEndTime());
		cService.setRoom(commitment, room);

		HashMap<String, String> commitmentDetails = new HashMap<>();
		commitmentDetails.put("formattedDate", commitment.getFormattedDate(commitment.getDate()));
		commitmentDetails.put("startTime", commitment.getTime(commitment.getStartTime()));
		commitmentDetails.put("endTime", commitment.getTime(commitment.getEndTime()));
		commitmentDetails.put("room", room.getRoomId());
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(commitmentDetails);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	public void destroy() {
	}

}
