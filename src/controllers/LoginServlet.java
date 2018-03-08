package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import daos.StudentDao;
import models.Project;
import models.Student;
import services.AdministratorService;
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
		String role = request.getParameter("role");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//if the username and password are not empty
		if(!username.equals("") && !password.equals("")) {
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
					response.sendRedirect("/DistributionDeProjets/student_choose_project.jsp");
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
