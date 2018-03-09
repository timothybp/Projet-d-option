package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Student;
import services.AdministratorService;
import services.CourseService;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//if the username and password are not empty
		if(!username.equals("") && !password.equals("")) {
			String role = request.getParameter("role");
			CourseService courseService = new CourseService();
			
			//if the radio button is "Etudiant.e"
			if(role.equals("student")){
				StudentService studentService = new StudentService();
				
				//connect database
				EntityManager em = studentService.connectDatabase();
				
				//verify the authentification of login
				int resultVerification = studentService.verifyLogin(username, password, em);
				if(resultVerification == 1) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Etudiant.e]!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else if(resultVerification == 2) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le mot de passe n'est pas correct!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else {
					Student student = new Student();
					student = studentService.getStudentInfo(username, em);
					request.setAttribute("name", student.getName());
					request.setAttribute("surname", student.getSurname());
					request.setAttribute("department", student.getDepartment());
					request.setAttribute("grade", String.valueOf(student.getGrade()));
					request.setAttribute("photoPath", student.getPhotoPath());
					
					int firstSemester = student.getGrade() * 2 - 1;
					request.setAttribute("firstSemester", String.valueOf(firstSemester));
					request.setAttribute("courseListForFirstSemester", courseService.searchCourseForOneSemester(firstSemester, em));
					int secondSemester = student.getGrade() * 2;
					request.setAttribute("secondSemester", String.valueOf(secondSemester));
					request.setAttribute("courseListForSecondSemester", courseService.searchCourseForOneSemester(secondSemester, em));
					RequestDispatcher rd = request.getRequestDispatcher("/student_choose_project.jsp");
			        rd.forward(request, response);
				}
			}
			//if the radio button is "Professeur"
			else if(role.equals("teacher")) {
				TeacherService teacherService = new TeacherService();
				
				//connect database
				EntityManager em = teacherService.connectDatabase();
				
				//verify the authentification of login
				int resultVerification = teacherService.verifyLogin(username, password, em);
				if(resultVerification == 1) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Professeur]!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else if(resultVerification == 2) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le mot de passe n'est pas correct!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else {
					response.sendRedirect("/DistributionDeProjets/teacher_home.jsp");
				}
			}
			
			else {
				AdministratorService administratorService = new AdministratorService();
				
				//connect database
				EntityManager em = administratorService.connectDatabase();
				
				//verify the authentification of login
				int resultVerification = administratorService.verifyLogin(username, password, em);
				if(resultVerification == 1) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Administrateur]!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else if(resultVerification == 2) {
					out.print("<script>");
					out.print("alert(\"Erreur: Le mot de passe n'est pas correct!\");");
					out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
					out.print("</script>");
				}
				else {
					response.sendRedirect("/DistributionDeProjets/admin_home.jsp");
				}
			}
		}
		else {
			out.print("<script>");
			out.print("alert(\"Erreur: Le nom d'utilisateur et le mot de passe ne peuvent pas être vides!\");");
			out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
			out.print("</script>");
		}
	}

}
