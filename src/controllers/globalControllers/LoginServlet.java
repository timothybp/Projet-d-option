package controllers.globalControllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Student;
import models.Teacher;
import services.StudentService;
import services.TeacherService;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RedirectController redirectCtrl = new RedirectController();
		
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		String errorMessage = "";
		
		//if the username and password are not empty
		if(!username.equals("") && !password.equals("")) {
			String role = request.getParameter("role");
			
			//if the radio button is "Etudiant.e"
			if(role.equals("student")){
				StudentService studentService = new StudentService();
				
				//verify the authentification of login
				int resultVerification = studentService.verifyLogin(username, password);
				if(resultVerification == 1) {
					errorMessage = "Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Etudiant.e]!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Student student = new Student();
					student = studentService.getStudentInfo("idStudent",username.substring(0, username.length()-1)).get(0);
					redirectCtrl.redirectToStudentPage(student, "", "student_home", "", request, response);
				}
			}
			//if the radio button is "Professeur"
			else if(role.equals("teacher")) {
				TeacherService teacherService = new TeacherService();
				
				//verify the authentification of login
				int resultVerification = teacherService.verifyLogin(username, password);
				if(resultVerification == 1) {
					errorMessage = "Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Professeur]!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Teacher teacher = new Teacher();
					teacher = teacherService.getTeacherInfo("idTeacher", username.substring(0, username.length()-1)).get(0);
					redirectCtrl.redirectToTeacherPage(teacher, "", "teacher_home", "all_null", request, response);
				}
			}
			
			//if the radio button is "Administrateur"
			else {
				TeacherService teacherService = new TeacherService();
				
				//verify the authentification of login
				int resultVerification = teacherService.verifyLogin(username, password);
				if(resultVerification == 1) {
					errorMessage = "Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Administrateur]!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					redirectCtrl.stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Teacher teacher = new Teacher();
					teacher = teacherService.getTeacherInfo("idTeacher", username.substring(0, username.length()-1)).get(0);
					redirectCtrl.redirectToAdministratorPage(teacher, "", "admin_home", "", request, response);
				}
			}
		}
		else {
			errorMessage = "Erreur: Le nom d'utilisateur et le mot de passe ne peuvent pas être vides!";
			redirectCtrl.stayOnLoginPage(errorMessage,request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
