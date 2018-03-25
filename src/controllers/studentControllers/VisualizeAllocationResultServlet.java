package controllers.studentControllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.globalControllers.RedirectController;
import models.Student;
import services.StudentService;

/**
 * Servlet implementation class VisualizeAllocationResultServlet
 * Cette classe est pour faire l'action de bouton "Rechercher" dans la page "student_visualize_allocation.jsp"
 */
public class VisualizeAllocationResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisualizeAllocationResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String course = request.getParameter("course");
		String host = request.getParameter("host");
		
		String attachment = "";
		String message = "";
		if(!course.equals("default")){
			String idCourse = course.split("#")[1];
			idCourse = idCourse.substring(1, idCourse.length()-1);
			attachment = "selectedCourseId_" + idCourse;
		}
		else{
			message = "Error: Il faut choisir un cours!";
		}
		RedirectController redirectCtrl = new RedirectController();
		StudentService studentService = new StudentService();
		Student student = new Student();
		student = studentService.getStudentInfo("wholeName", host).get(0);
		redirectCtrl.redirectToStudentPage(student,message, "student_visualize_allocation",attachment,request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
