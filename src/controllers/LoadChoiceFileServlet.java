package controllers;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Teacher;
import services.TeacherService;

/**
 * Servlet implementation class LoadChoiceFileServlet
 */
public class LoadChoiceFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadChoiceFileServlet() {
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
			
			
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/choice/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + courseName + "[" + courseSchoolYear + "]_etu.txt";
			attachment = "etu#" + filename;
		}
		else{
			message = "Error: Il faut choisi un cours!";
		}
		
		TeacherService teacherService = new TeacherService();
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacherHost = new Teacher();
		teacherHost = teacherService.getTeacherInfo("wholeName", host).get(0);
		
		redirectCtrl.redirectToAdministratorPage(teacherHost, message, "allocate_project", attachment,request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
