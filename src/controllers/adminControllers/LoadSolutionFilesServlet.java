package controllers.adminControllers;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.RedirectController;
import models.Teacher;
import services.TeacherService;

/**
 * Servlet implementation class VisualizeSolutionServlet
 * Cette classe est pour faire l'action de bouton "Rechercher" dans la page "solutions.jsp"
 */
public class LoadSolutionFilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadSolutionFilesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedSolutionPath = request.getParameter("filepath");
		String selectedSolution = request.getParameter("solution");
		String selectedCourseName = request.getParameter("selectedCourseName");
		String selectedCourseSchoolYear = request.getParameter("selectedCourseSchoolYear");
		String host = request.getParameter("host");
		
		String message = "";
		String attachment = "";
		File filepath = new File(selectedSolutionPath);
		if(!selectedSolution.equals("default")){
			
			//chercher les fichiers de solutions temporaires pour ce cours
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + selectedSolution + ".txt";
			attachment = "rec#" + filepath + "@" + selectedCourseName + "@" + selectedCourseSchoolYear + "@" + filename + "@" + selectedSolution;
		}
		else{
			message = "Error: Il faut choisi une solution!";
			attachment = "sol#" + filepath + "@" + selectedCourseName + "@" + selectedCourseSchoolYear;
		}
		
		TeacherService teacherService = new TeacherService();
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "solutions", attachment,request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
