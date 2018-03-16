package controllers.adminControllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.FileController;
import controllers.globalControllers.RedirectController;
import models.Project;
import models.Teacher;
import services.ProjectService;
import services.TeacherService;

/**
 * Servlet implementation class DistributeProjectServlet
 */
public class DistributeProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DistributeProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedCourse = request.getParameter("selectedCourse");
		String host = request.getParameter("host_1");
		
		String message = "";
		String attachment = "";
		if(!selectedCourse.equals("")){
			String courseName = selectedCourse.split("#")[0].trim();
			String courseSchoolYear = selectedCourse.split("#")[1];
			
			
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/proposition/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + courseName + "[" + courseSchoolYear + "]_pro.txt";
			
			FileController fileCtrl = new FileController();
			List<Project> listProject = fileCtrl.readPropositionFile(filename);
			ProjectService projectService = new ProjectService();
			for(Project project: listProject){
				projectService.save(project);
			}
			message = "Succ¨¨s: Ces projets est bien distribu¨¦! Les ¨¦tudiants peuvent les regarder!";
			fileCtrl.deleteFile(filename);
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
