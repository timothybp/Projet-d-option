package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Course;
import models.Project;
import models.Student;
import models.Teacher;
import services.CourseService;
import services.ProjectService;
import services.StudentService;
import services.TeacherService;

/**
 * Servlet implementation class ProposeServlet
 */
public class ProposeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProposeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subject = request.getParameter("subject").trim();
		String description = request.getParameter("description").trim();
		String listSupervisor = request.getParameter("supervisor").replaceAll(" ", "");
		String courseName = request.getParameter("courseName");
		String enterprise = request.getParameter("enterprise").trim();
		String teacherInfo = request.getParameter("teacherInfo");
		String host = request.getParameter("host");
		
		String message = "";
		TeacherService teacherService = new TeacherService();
		CourseService courseService = new CourseService();
		FileController fileCtrl = new FileController();
		
		if(!courseName.equals("Default")) {
			Course course = new Course();
			course = (courseService.searchCourses("nom", courseName.replace("'", "''"))).get(0);
			
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/proposition/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + course.getNom() + "[" + course.getSchoolYear() + "]_pro.txt";
			
			if(!subject.equals("") && !listSupervisor.equals("")){
				String [] supervisors = listSupervisor.split(";");
				Teacher teacher = new Teacher();
				boolean sign = true;
				
				File file = new File(filename);
				List<Teacher> teachers = new ArrayList<Teacher>();
				if(!file.exists() || fileCtrl.judgeSubjectExistInFile(subject,filename)==false){
					for(int i = 0; i < supervisors.length; i++){
						if(supervisors[i].split("_").length == 2){
							String name = supervisors[i].split("_")[0];
							String surname = supervisors[i].split("_")[1];
						
							List<Teacher> listTeacher = teacherService.getTeacherInfo("wholeName", supervisors[i]);
							if(listTeacher.size() != 0) {
								teacher = listTeacher.get(0);
								teachers.add(teacher);
							}
							else{
								message = "Erreur: Le professeur " + name + " " + surname + " n'existe pas!";
								sign = false;
								break;
							}
						}
						else{
							message = "Erreur: Le format de nom de l'encadrant n'est pas correct!";
							sign = false;
							break;
						}
					}
					if(sign == true) {
						Project project = new Project();
						project.setSubject(subject);
						
						if(description.equals(""))
							description = "VIDE";
						project.setDescription(description);
						
						project.setCourse(course);
						
						if(enterprise.equals(""))
							enterprise = "VIDE";
						project.setEnterprise(enterprise);
						
						project.setListTeacher(teachers);
						fileCtrl.recordPropositionToFile(project, filename);
						message = "Succ¨¨s: votre projet est bien enregistr¨¦s!";
					}
				}
				else {
					message = "Erreur: Le sujet [ " + subject + " ] est d¨¦j¨¤ ¨ºtre propos¨¦ "
							+ "pour le cours " + course.getNom() + " en " + course.getSchoolYear() + "!";
				}
			}
			else{
				message = "Erreur: Les champs marqu¨¦e par * rouge ne peuvent pas ¨ºtre vides!";
			}
		}
		else{
			message = "Erreur: Il faut s¨¦lectionner un cours de projet!";
		}
		
		RedirectController redirectCtrl = new RedirectController();
		Teacher teacher = new Teacher();
		teacher = teacherService.getTeacherInfo("wholeName", host).get(0);
		redirectCtrl.redirectToTeacherPage(teacher, message, "teacher_propose_project", "all_null", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
