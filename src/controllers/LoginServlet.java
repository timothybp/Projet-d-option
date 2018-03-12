package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.URLEncoder;

import daos.StudentDao;
import models.Project;
import models.Student;
import models.Teacher;
import net.sf.json.JSONObject;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
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
					stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Student student = new Student();
					student = studentService.getStudentInfo("idStudent",username.substring(0, username.length()-1)).get(0);
					redirectToStudentChooseProjectPage(student, request, response);
				}
			}
			//if the radio button is "Professeur"
			else if(role.equals("teacher")) {
				TeacherService teacherService = new TeacherService();
				
				//verify the authentification of login
				int resultVerification = teacherService.verifyLogin(username, password);
				if(resultVerification == 1) {
					errorMessage = "Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Professeur]!";
					stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Teacher teacher = new Teacher();
					teacher = teacherService.getTeacherInfo("idTeacher", username.substring(0, username.length()-1)).get(0);
					redirectToTeacherHomePage(teacher, request, response);
				}
			}
			
			else {
				AdministratorService administratorService = new AdministratorService();
				
				//verify the authentification of login
				int resultVerification = administratorService.verifyLogin(username, password);
				if(resultVerification == 1) {
					errorMessage = "Erreur: Le nom d'utilisateur n'existe pas dans le rôle [Administrateur]!";
					stayOnLoginPage(errorMessage,request,response);
				}
				else if(resultVerification == 2) {
					errorMessage = "Erreur: Le mot de passe n'est pas correct!";
					stayOnLoginPage(errorMessage,request,response);
				}
				else {
					Teacher teacher = new Teacher();
					teacher = administratorService.getAdministratorInfo("idTeacher", username.substring(0, username.length()-1)).get(0);
					redirectToAdministratorHomePage(teacher, request, response);
				}
			}
		}
		else {
			errorMessage = "Erreur: Le nom d'utilisateur et le mot de passe ne peuvent pas être vides!";
			stayOnLoginPage(errorMessage,request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void stayOnLoginPage(String errorMessage, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert(\"" + errorMessage + "\");");
			out.print("window.location.href=\"/DistributionDeProjets/login.jsp\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void redirectToStudentChooseProjectPage(Student student,
			HttpServletRequest request, HttpServletResponse response){
		
		request.setAttribute("name", student.getName());
		request.setAttribute("surname", student.getSurname());
		request.setAttribute("department", student.getDepartment());
		request.setAttribute("grade", String.valueOf(student.getGrade()));
		request.setAttribute("photoPath", student.getPhotoPath());
		
		CourseService courseService = new CourseService();
		int firstSemester = student.getGrade() * 2 - 1;
		request.setAttribute("firstSemester", String.valueOf(firstSemester));
		request.setAttribute("courseListForFirstSemester", courseService.searchCourses("semester", String.valueOf(firstSemester)+"-"+student.getDepartment()));
		int secondSemester = student.getGrade() * 2;
		request.setAttribute("secondSemester", String.valueOf(secondSemester));
		request.setAttribute("courseListForSecondSemester", courseService.searchCourses("semester", String.valueOf(secondSemester)+"-"+student.getDepartment()));
		RequestDispatcher rd = request.getRequestDispatcher("/student_choose_project.jsp");
        try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    public void redirectToTeacherHomePage(Teacher teacher,
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("name", teacher.getName());
    	jsonObj.put("surname", teacher.getSurname());
    	jsonObj.put("department", teacher.getDepartment());
    	jsonObj.put("photoPath", teacher.getPhotoPath());
    	
    	String jsonStr = "";
		try {
			jsonStr = java.net.URLEncoder.encode(String.valueOf(jsonObj),java.nio.charset.StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("window.location.href=\"/DistributionDeProjets/teacher_home.jsp?jsonStrEnc="+jsonStr+"\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void redirectToAdministratorHomePage(Teacher teacher,
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("name", teacher.getName());
    	jsonObj.put("surname", teacher.getSurname());
    	jsonObj.put("department", teacher.getDepartment());
    	jsonObj.put("photoPath", teacher.getPhotoPath());
    	
    	String jsonStr = "";
		try {
			jsonStr = java.net.URLEncoder.encode(String.valueOf(jsonObj),java.nio.charset.StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("window.location.href=\"/DistributionDeProjets/admin_home.jsp?jsonStrEnc="+jsonStr+"\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
