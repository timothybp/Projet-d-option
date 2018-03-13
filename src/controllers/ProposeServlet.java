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
		String subject = request.getParameter("subject");
		String description = request.getParameter("description");
		String listSupervisor = request.getParameter("supervisor").replaceAll(" ", "");
		String courseName = request.getParameter("courseName");
		String enterprise = request.getParameter("enterprise");
		String teacherInfo = request.getParameter("teacherInfo");
		String host = request.getParameter("host");
		
		String message = "";
		TeacherService teacherService = new TeacherService();
		CourseService courseService = new CourseService();
		
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
				if(!file.exists() ||
						(file.exists() && judgeSubjectExistInFile(subject,filename)==false)){
					for(int i = 0; i < supervisors.length; i++){
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
					if(sign == true) {
						Project project = new Project();
						project.setSubject(subject);
						project.setDescription(description);
						project.setCourse(course);
						project.setEnterprise(enterprise);
						project.setListTeacher(teachers);
						recordPropositionToFile(project, filename);
						message = "Succ¨¨s: votre projet est bien enregistr¨¦s!";
					}
				}
				else {
					System.out.println("ewrewtewtyrerwtewtewtr");
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
	
	public boolean judgeSubjectExistInFile(String subject, String filename){
        FileInputStream inputStream;
        boolean sign = false;
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
            
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	            String subjectRead = subject.split("\t")[0];
	            if(subjectRead.equals(subject)){
	            	sign = true;
	            	break;
	            }
	        }  
	        inputStream.close();  
	        bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return sign;
	}
	
	public void recordPropositionToFile(Project project, String filename){
		String content = project.getSubject() + "\t";
		for(Teacher teacher: project.getListTeacher()){
			content += String.valueOf(teacher.getIdTeacher()) + ";";
		}
		content = content.substring(0, content.length()-1) + "\t";
		content += project.getDescription() + "\t" + project.getEnterprise();
		
		FileWriter fw = null;
		try {
			File f=new File(filename);
			fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
			System.out.println(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
