package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Course;
import models.Project;
import models.Student;
import models.Teacher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import services.CourseService;
import services.ProjectService;
import services.StudentService;

public class RedirectController {
	
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
	
	public void redirectToStudentPage(Student student, String message,String pageName,
			HttpServletRequest request, HttpServletResponse response){
		
		JSONObject jsonObjStudent = new JSONObject();
		jsonObjStudent.put("name", student.getName());
		jsonObjStudent.put("surname", student.getSurname());
		jsonObjStudent.put("department", student.getDepartment());
		jsonObjStudent.put("grade", String.valueOf(student.getGrade()));
		jsonObjStudent.put("photoPath", student.getPhotoPath());

		CourseService courseService = new CourseService();
		int firstSemester = student.getGrade() * 2 - 1;
		jsonObjStudent.put("firstSemester", String.valueOf(firstSemester));
		JSONArray jsonArrayFirstCourse = new JSONArray();
		for(Course course:courseService.searchCourses("semester", String.valueOf(student.getGrade() * 2 - 1)+"-"+student.getDepartment())){
			JSONObject jsonObjCourse = new JSONObject();
			jsonObjCourse.put("nom", course.getNom().replace("'", "^"));
			jsonObjCourse.put("responsable", course.getTeacher().getName() + " " + course.getTeacher().getSurname());
			 
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			jsonObjCourse.put("beginDate", sdf1.format(course.getBeginDate()));
			jsonObjCourse.put("endDate", sdf1.format(course.getEndDate()));
			jsonObjCourse.put("choosingDeadline", sdf2.format(course.getChoosingDeadline()));
			 
			jsonObjCourse.put("heures", String.valueOf(course.getHours()));
			jsonObjCourse.put("poids", String.valueOf(course.getWeights()));
			jsonObjCourse.put("memberAmount",String.valueOf(course.getMembreAmount()));
			jsonObjCourse.put("schoolYear", course.getSchoolYear());
			
			JSONArray jsonArrayProject = new JSONArray();
			for(Project project : course.getListProject()){
				JSONObject jsonObjProject = new JSONObject();
			 	jsonObjProject.put("idProject", String.valueOf(project.getIdProject()));
			 	jsonObjProject.put("subject",project.getSubject());
			 	jsonObjProject.put("description", project.getDescription());
			 	jsonObjProject.put("enterprise", project.getEnterprise());
			 	String supervisors = "";
			 	for(Teacher teacher: project.getListTeacher()){
			 		supervisors += teacher.getName() + " " + teacher.getSurname() + ";";
			 	}
			 	if(supervisors.length() != 0)
			 		jsonObjProject.put("supervisors", supervisors.substring(0, supervisors.length()-1));
			 	else
			 		jsonObjProject.put("supervisors", supervisors);
			 	jsonArrayProject.add(jsonObjProject);
			 }
			 jsonObjCourse.put("projects", jsonArrayProject);
			 jsonArrayFirstCourse.add(jsonObjCourse);
		}
		jsonObjStudent.put("courseListForFirstSemester", jsonArrayFirstCourse);
		
		int secondSemester = student.getGrade() * 2;
		jsonObjStudent.put("secondSemester", String.valueOf(secondSemester));
		JSONArray jsonArraySecondCourse = new JSONArray();
		for(Course course:courseService.searchCourses("semester", String.valueOf(student.getGrade() * 2)+"-"+student.getDepartment())){
			JSONObject jsonObjCourse = new JSONObject();
			jsonObjCourse.put("nom", course.getNom());
			jsonObjCourse.put("responsable", course.getTeacher().getName() + " " + course.getTeacher().getSurname());
			 
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			jsonObjCourse.put("beginDate", sdf1.format(course.getBeginDate()));
			jsonObjCourse.put("endDate", sdf1.format(course.getEndDate()));
			jsonObjCourse.put("choosingDeadline", sdf2.format(course.getChoosingDeadline()));
			 
			jsonObjCourse.put("heures", String.valueOf(course.getHours()));
			jsonObjCourse.put("poids", String.valueOf(course.getWeights()));
			jsonObjCourse.put("memberAmount",String.valueOf(course.getMembreAmount()));
			
			JSONArray jsonArrayProject = new JSONArray();
			for(Project project : course.getListProject()){
				JSONObject jsonObjProject = new JSONObject();
			 	jsonObjProject.put("idProject", String.valueOf(project.getIdProject()));
			 	jsonObjProject.put("subject",project.getSubject());
			 	jsonObjProject.put("description", project.getDescription());
			 	jsonObjProject.put("enterprise", project.getEnterprise());
			 	String supervisors = "";
			 	for(Teacher teacher: project.getListTeacher()){
			 		supervisors += teacher.getName() + " " + teacher.getSurname() + ";";
			 	}
			 	if(supervisors.length() != 0)
			 		jsonObjProject.put("supervisors", supervisors.substring(0, supervisors.length()-1));
			 	else
			 		jsonObjProject.put("supervisors", supervisors);
			 	jsonArrayProject.add(jsonObjProject);
			 }
			 jsonObjCourse.put("projects", jsonArrayProject);
			 jsonArraySecondCourse.add(jsonObjCourse);
		}
		jsonObjStudent.put("courseListForSecondSemester", jsonArraySecondCourse);
		
		String jsonStr = "";
		try {
			jsonStr = java.net.URLEncoder.encode(String.valueOf(jsonObjStudent),java.nio.charset.StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			if(!message.equals(""))
				out.print("alert(\"" + message + "\");");
			out.print("window.location.href=\"/DistributionDeProjets/" + pageName + ".jsp?jsonStrEnc="+jsonStr+"\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    public void redirectToTeacherPage(Teacher teacher, String message, String pageName,
    		String attachement, HttpServletRequest request, HttpServletResponse response) {
    	
    	JSONObject jsonObjTeacher = new JSONObject();
    	jsonObjTeacher.put("name", teacher.getName());
    	jsonObjTeacher.put("surname", teacher.getSurname());
    	jsonObjTeacher.put("department", teacher.getDepartment());
    	jsonObjTeacher.put("photoPath", teacher.getPhotoPath());
    	
    	CourseService courseSercice = new CourseService();
    	List<Course> listCourse = courseSercice.searchCourses("schoolYear+department", teacher.getDepartment());
    	JSONArray jsonArrayCourse = new JSONArray();
    	for(Course course: listCourse){
    		JSONObject jsonObjCourse = new JSONObject();
    		jsonObjCourse.put("nom", course.getNom());
    		jsonArrayCourse.add(jsonObjCourse);
    	}
    	
    	jsonObjTeacher.put("listCourse", jsonArrayCourse);
    	jsonObjTeacher.put("schoolYear", courseSercice.judgeSchoolYear());
    	 
    	ProjectService projectService = new ProjectService();
    	List<Project> listProject = projectService.ViewMyOwnProjets(attachement, String.valueOf(teacher.getIdTeacher()));
    	JSONArray jsonArrayProject = new JSONArray();
    	for(Project project: listProject){
    		JSONObject jsonObjProject = new JSONObject();
    		jsonObjProject.put("idProject", project.getIdProject());
    		jsonObjProject.put("subject", project.getSubject());
    		jsonObjProject.put("description", project.getDescription());
    		
    		String supervisorNames = "";
    		for(Teacher supervisor: project.getListTeacher()) {
    			supervisorNames += supervisor.getName() + " " + supervisor.getSurname() + ";";
    		}
    		if(!supervisorNames.equals(""))
    			supervisorNames = supervisorNames.substring(0, supervisorNames.length() - 1);
    		jsonObjProject.put("supervisorNames", supervisorNames);
    		
    		jsonObjProject.put("enterprise", project.getEnterprise());
    		jsonObjProject.put("courseName", project.getCourse().getNom());
    		jsonObjProject.put("semester", project.getCourse().getSemester());
    		jsonObjProject.put("projectYear", project.getCourse().getSchoolYear());
    		
    		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		jsonObjProject.put("startDate", df.format(project.getCourse().getBeginDate()));
    		jsonObjProject.put("endDate", df.format(project.getCourse().getEndDate()));
    		
    		String studentNames = "";
    		for(Student student : project.getListStudent()) {
    			studentNames += student.getName() + " " + student.getSurname() + ";";
    		}
    		if(!studentNames.equals(""))
    			studentNames = studentNames.substring(0, studentNames.length() - 1);
    		jsonObjProject.put("studentNames", studentNames);
    		
    		jsonArrayProject.add(jsonObjProject);
    	}
    	
    	
    	jsonObjTeacher.put("listProject", jsonArrayProject);
    	jsonObjTeacher.put("selectedOption", attachement.split("_")[0]);
    	
    	String jsonStr = "";
		try {
			jsonStr = java.net.URLEncoder.encode(String.valueOf(jsonObjTeacher),java.nio.charset.StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			if(!message.equals(""))
				out.print("alert(\"" + message + "\");");
			out.print("window.location.href=\"/DistributionDeProjets/" + pageName + ".jsp?jsonStrEnc="+jsonStr+"\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public void redirectToAdministratorPage(Teacher teacher, String message,String pageName,
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("name", teacher.getName());
    	jsonObj.put("surname", teacher.getSurname());
    	jsonObj.put("department", teacher.getDepartment());
    	jsonObj.put("photoPath", teacher.getPhotoPath());
    	
    	CourseService courseSercice = new CourseService();
    	jsonObj.put("schoolYear", courseSercice.judgeSchoolYear());
    	
    	
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
			if(!message.equals(""))
				out.print("alert(\"" + message + "\");");
			out.print("window.location.href=\"/DistributionDeProjets/" + pageName + ".jsp?jsonStrEnc="+jsonStr+"\"");
			out.print("</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
