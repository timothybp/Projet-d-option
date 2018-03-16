package controllers.globalControllers;

import java.io.File;
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
    		String attachment, HttpServletRequest request, HttpServletResponse response) {
    	
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
    	List<Project> listProject = projectService.ViewMyOwnProjets(attachment, String.valueOf(teacher.getIdTeacher()));
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
    	jsonObjTeacher.put("selectedOption", attachment.split("_")[0]);
    	
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
    		String attachment, HttpServletRequest request, HttpServletResponse response) {
    	
    	JSONObject jsonObjAdmin = new JSONObject();
    	jsonObjAdmin.put("name", teacher.getName());
    	jsonObjAdmin.put("surname", teacher.getSurname());
    	jsonObjAdmin.put("department", teacher.getDepartment());
    	jsonObjAdmin.put("photoPath", teacher.getPhotoPath());
    	
    	CourseService courseSercice = new CourseService();
    	List<Course> listCourse = courseSercice.searchCourses("schoolYear+department", teacher.getDepartment());
    	JSONArray jsonArrayCourse = new JSONArray();
    	for(Course course: listCourse){
    		JSONObject jsonObjCourse = new JSONObject();
    		jsonObjCourse.put("idCourse", String.valueOf(course.getIdCourse()));
    		jsonObjCourse.put("nom", course.getNom());
    		jsonArrayCourse.add(jsonObjCourse);
    	}
    	jsonObjAdmin.put("listCourse", jsonArrayCourse);
    	jsonObjAdmin.put("schoolYear", courseSercice.judgeSchoolYear());
    	
    	
    	File propositionDir = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/proposition/");
    	JSONArray jsonArraycourseForProposition = new JSONArray();
		if(propositionDir.exists()){
			for(File file : propositionDir.listFiles()) {
	            if(file.isFile()) {
	            	JSONObject jsonObjcourseForProposition = new JSONObject();
	            	String filename = file.getName();
	            	String courseName = filename.split("\\[")[0];
	            	String schoolYear = filename.split("\\[")[1].split("\\]")[0];
	            	
	            	jsonObjcourseForProposition.put("courseName", courseName +" #" + schoolYear);
	            	jsonArraycourseForProposition.add(jsonObjcourseForProposition);
	            }
	        }
		}
		jsonObjAdmin.put("courseForProposition", jsonArraycourseForProposition);
		
		File choiceDir = new File(this.getClass().getClassLoader().getResource("/").getPath()+ "/temp/choice/");
    	JSONArray jsonArraycourseForChoice = new JSONArray();
		if(choiceDir.exists()){
			for(File file : choiceDir.listFiles()) {
	            if(file.isFile()) {
	            	JSONObject jsonObjcourseForChoice = new JSONObject();
	            	String filename = file.getName();
	            	String courseName = filename.split("\\[")[0];
	            	String schoolYear = filename.split("\\[")[1].split("\\]")[0];
	            
	            	jsonObjcourseForChoice.put("courseName", courseName +" #" + schoolYear);
	            	jsonArraycourseForChoice.add(jsonObjcourseForChoice);
	            }
	        }
		}
		jsonObjAdmin.put("courseForChoice", jsonArraycourseForChoice);
		
		FileController fileCtrl = new FileController();
		JSONArray jsonArrayProject = new JSONArray();
		JSONArray jsonArrayChoice = new JSONArray();
		JSONArray jsonArraySolution = new JSONArray();
		JSONArray jsonArrayRecord = new JSONArray();
		
		if(!attachment.equals("")){
			String readDir = attachment.split("#")[0];
			String otherInfo = attachment.split("#")[1];
			
			if(readDir.equals("pro")){
				String filename = otherInfo;
				List<Project> listProject = fileCtrl.readPropositionFile(filename);
				for(Project project:listProject){
					JSONObject jsonObjProject = new JSONObject();
					jsonObjProject.put("subject", project.getSubject());
					jsonObjProject.put("description", project.getDescription());
					jsonObjProject.put("enterprise", project.getEnterprise());
					jsonObjProject.put("idCourse", String.valueOf(project.getCourse().getIdCourse()));
					jsonObjProject.put("selectedCourseName", project.getCourse().getNom() + " #" + 
					project.getCourse().getSchoolYear());
					String supervisorName = "";
					for(Teacher supervisor: project.getListTeacher()){
						supervisorName += supervisor.getName() + " " + supervisor.getSurname() + ";";
					}
					if(!supervisorName.equals(""))
						supervisorName = supervisorName.substring(0, supervisorName.length()-1);
					jsonObjProject.put("supervisors", supervisorName);
					jsonArrayProject.add(jsonObjProject);
				}
			}
			
			if(readDir.equals("etu")){
				String filename = otherInfo;
				List<List<String>> listChoice = fileCtrl.readChoiceFile(filename);
				for(List<String> listChoiceForEachGroup: listChoice){
					JSONObject jsonObjChoice = new JSONObject();
					jsonObjChoice.put("memberIds", listChoiceForEachGroup.get(0));
					jsonObjChoice.put("memberWholeNames", listChoiceForEachGroup.get(1));
					jsonObjChoice.put("choiceIds",listChoiceForEachGroup.get(2));
					jsonObjChoice.put("choiceSubject", listChoiceForEachGroup.get(3));
					jsonObjChoice.put("selectedCourseName", listChoiceForEachGroup.get(4) + " #" + 
							listChoiceForEachGroup.get(5));
					jsonArrayChoice.add(jsonObjChoice);
				}
			}
			
			if(readDir.equals("sol") || readDir.equals("rec")){
				JSONObject jsonObjSolution = new JSONObject();
				String filepath = otherInfo.split("@")[0];
				String selectedCourseName = otherInfo.split("@")[1];
				String selectedCourseSchoolYear = otherInfo.split("@")[2];
				
				List<String> listSolutionFilename = fileCtrl.readSolutionDir(filepath);
				String solutionFileNames = "";
				for(String solutionFilename : listSolutionFilename){
					solutionFileNames += solutionFilename + ";";
				}
				solutionFileNames = solutionFileNames.substring(0, solutionFileNames.length()-1);
				jsonObjSolution.put("solutionFileNames", solutionFileNames);
				jsonObjSolution.put("selectedCourseName", selectedCourseName);
				jsonObjSolution.put("selectedCourseSchoolYear",selectedCourseSchoolYear);
				jsonObjSolution.put("solutionFilePath", filepath);
				jsonArraySolution.add(jsonObjSolution);
				
				if(readDir.equals("rec")){
					String filename = otherInfo.split("@")[3];
					String selectedSolution = otherInfo.split("@")[4];
					List<List<String>> listRecord = fileCtrl.readSolutionRecordFile(filename);
					for(List<String> record: listRecord){
						JSONObject jsonObjRecord = new JSONObject();
						jsonObjRecord.put("memberIds", record.get(0));
						jsonObjRecord.put("memberNames", record.get(1));
						jsonObjRecord.put("projectIds", record.get(2));
						jsonObjRecord.put("projectNames", record.get(3));
						jsonObjRecord.put("supervisorNames", record.get(4));
						jsonObjRecord.put("enterprise", record.get(5));
						jsonObjRecord.put("selectedCourseName", selectedCourseName);
						jsonObjRecord.put("selectedCourseSchoolYear",selectedCourseSchoolYear);
						jsonObjRecord.put("selectedSolution", selectedSolution);
						jsonArrayRecord.add(jsonObjRecord);
					}
				}
			}
		}
		jsonObjAdmin.put("listProject", jsonArrayProject);
		jsonObjAdmin.put("listChoice", jsonArrayChoice);
		jsonObjAdmin.put("listSolution", jsonArraySolution);
		jsonObjAdmin.put("listRecord", jsonArrayRecord);
		
    	String jsonStr = "";
		try {
			jsonStr = java.net.URLEncoder.encode(String.valueOf(jsonObjAdmin),java.nio.charset.StandardCharsets.UTF_8.toString());
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
