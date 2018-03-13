package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import models.Course;
import models.Project;
import models.Student;
import services.CourseService;
import services.ProjectService;
import services.StudentService;

/**
 * Servlet implementation class ChooseServlet
 */
public class ChooseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("course").replace("'", "''");
		String listMember = request.getParameter("members").replace(" ", "");
		String host = request.getParameter("host");
		String choice1 = request.getParameter("choice1");
		String choice2 = request.getParameter("choice2");
		String choice3 = request.getParameter("choice3");
		List<String> listChoice = new ArrayList<String>();
		listChoice.add(choice1);
		listChoice.add(choice2);
		listChoice.add(choice3);
		
		String message = "";
		CourseService courseService = new CourseService();
		StudentService studentService = new StudentService();
		
		
		if(!courseName.equals("")){
			
			Course course = new Course();
			course = (courseService.searchCourses("nom", courseName)).get(0);
			int grade;
			if(course.getSemester()%2 == 0)
				grade = (course.getSemester())/2;
			else
				grade = (course.getSemester() + 1)/2;
			File filepath = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/choice/");
			if(!filepath.exists())
				filepath.mkdirs();
			String filename = filepath + "/" + course.getNom() + "[" + course.getSchoolYear() + "]_etu.txt";
			
			
			if(!listMember.equals("")){
				if(!choice1.equals("") && !choice2.equals("") && !choice3.equals("")){
					String [] members = listMember.split(";");
					if(members.length == course.getMembreAmount() ||
							(course.getMembreAmount() != 1 && members.length == course.getMembreAmount()-1)){
						Student student = new Student();
						boolean sign = true;
						List<Long> listIdStudent = new ArrayList<Long>();
						for(int i = 0; i < members.length; i++){
							String name = members[i].split("_")[0];
							String surname = members[i].split("_")[1];
							
							List<Student> listStudent = studentService.getStudentInfo("wholeName", members[i]);
							if(listStudent.size() != 0) {
								student = listStudent.get(0);
								if((student.getDepartment()).equals(course.getDepartment()) &&
										(student.getGrade()) == grade){
									File file = new File(filename);
									if(!file.exists() ||
											(file.exists() && judgeStudentExistInFile(student.getIdStudent(),filename)==false)){
										
										ProjectService projectService = new ProjectService();
										for(int j = 0; j < 3; j++){
											List<Project> listProject = new ArrayList<Project>();
											listProject = projectService.searchProjectsForOneCourse("idProject",listChoice.get(j));
											if(listProject.size() == 0 || (listProject.size() != 0 && !listProject.get(0).getCourse().getIdCourse().equals(course.getIdCourse()))){			
												sign = false;
												message = "Erreur: Le sujet dont id = " + listChoice.get(j) + " n'existe pas pour le cours " + course.getNom() +"!";
												break;
											}
										}
										if(sign == false)
											break;
										else
											listIdStudent.add(student.getIdStudent());
									}
									else{
										sign = false;
										message = "Erreur: L'��tudiant " + name + " " + surname + " a d��j�� fait son choix!";
										break;
									}
								}
								else {
									sign = false;
									message = "Erreur: L'��tudiant " + name + " " + surname + " n'est pas en " 
												+ course.getDepartment() + String.valueOf(grade) + "!";
									break;
								}
							}
							else {
								sign = false;
								message = "Erreur: L'��tudiant " + name + " " + surname + " n'existe pas!";
								break;
							}
						}
						if(sign == true) {
							recordChoiceToFile(listIdStudent, listChoice, filename);
							message = "Succ��s: Vos voeux sont bien enregistr��s!";
						}
					}
					else{
						if(course.getMembreAmount() == 1)
							message = "Erreur: Ce projet doivoint ��tre fait en 1 personne!";
						else
							message = "Erreur: Ce projet doivoint ��tre fait en " 
										+ String.valueOf(course.getMembreAmount()-1) + "ou " 
										+ String.valueOf(course.getMembreAmount()) + " personnes!";
					}
				}
				else{
					message = "Erreur: Il faut remplir tous les 3 voeux!";
				}
			}
			else{
				message = "Erreur: Il faut saisir les noms des membres!";
			}
		}
		else{
			message = "Erreur: Il faut choisir un cours!";
		}
		RedirectController redirectCtrl = new RedirectController();
		
		Student student = new Student();
		student = studentService.getStudentInfo("wholeName", host).get(0);
		
		redirectCtrl.redirectToStudentPage(student,message, "student_choose_project",request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public boolean judgeStudentExistInFile(long idStudent, String filename){
		FileInputStream inputStream;
        boolean sign = false;
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
            
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	            String listStudent = str.split("\t")[0];
	            String [] students = listStudent.split(";");
	            
	            for(int i = 0; i < students.length; i++) {
	            	if(idStudent == Long.parseLong(students[i])){
	            		sign = true;
	            		break;
	            	}
	            }
	            if(sign == true)
	            	break;
	        }  
	        inputStream.close();  
	        bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return sign;
	}
	
	public void recordChoiceToFile(List<Long> listIdStudent, List<String> listChoice, String filename){
		String content = "";
		for(long id : listIdStudent){
			content += String.valueOf(id) + ";";
		}
		content = content.substring(0, content.length()-1);
		content += "\t" + listChoice.get(0) + ";" + listChoice.get(1) + ";" + listChoice.get(2); 
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