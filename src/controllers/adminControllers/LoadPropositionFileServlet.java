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
 * Servlet implementation class LoadPropositionFileServlet
 * Cette classe est pour faire l'action de bouton "Rechercher" dans la page "distribute_project.jsp"
 */

public class LoadPropositionFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadPropositionFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseOptionStr = request.getParameter("course");
		String host = request.getParameter("host");
		
		String message = "";
		String attachment = "";
		if(!courseOptionStr.equals("default")){
			String courseName = courseOptionStr.split("#")[0].trim();
			String courseSchoolYear = courseOptionStr.split("#")[1];
			
			//chercher le fichier de proposition temporaire pour ce cours
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/proposition/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + courseName + "[" + courseSchoolYear + "]_pro.txt";
			attachment = "pro#" + filename;
		}
		else{
			message = "Error: Il faut choisi un cours!";
		}
		
		TeacherService teacherService = new TeacherService();
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "distribute_project", attachment,request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
