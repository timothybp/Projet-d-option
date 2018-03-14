package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import models.Course;
import models.Project;
import models.Student;
import models.Teacher;
import services.CourseService;
import services.ProjectService;
import services.StudentService;
import services.TeacherService;

public class FileController {
	
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
	
	public boolean judgeSubjectExistInFile(String subject, String filename){
        FileInputStream inputStream;
        boolean sign = false;
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
            
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	            String subjectRead = str.split("\t")[0];
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
	
	public List<Project> readPropositionFile(String filename){
		FileInputStream inputStream;
		List<Project> listProject = new ArrayList<Project>();
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
            
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	        	Project project = new Project();
	            String subject = str.split("\t")[0];
	            String listSupervisorId = str.split("\t")[1];
	            String description = str.split("\t")[2];
	            String enterprise = str.split("\t")[3];
	            
	            project.setSubject(subject);
	            if(description.equals("VIDE"))
	            	description = "";
	            project.setDescription(description);
	            if(enterprise.equals("VIDE"))
	            	description = "";
	            project.setEnterprise(enterprise);
	            
	            String [] filePath = filename.split("/");
	            String courseName = filePath[filePath.length-1].split("\\[")[0];
	            String courseSchoolYear = filePath[filePath.length-1].split("\\[")[1].split("\\]")[0];
	            CourseService courseService = new CourseService();
	            Course course = courseService.searchCourses("nom+schoolYear", courseName.replaceAll("'", "''") + "_" + courseSchoolYear).get(0);
	            project.setCourse(course);
	            System.out.println(project.getCourse().getNom());
	            String [] idSupervisors = listSupervisorId.split(";");
	            TeacherService teacherService = new TeacherService();
	            List<Teacher> listTeacher = new ArrayList<Teacher>();
	            for(int i = 0; i < idSupervisors.length; i++){
	            	Teacher teacher = teacherService.getTeacherInfo("idTeacher", idSupervisors[i]).get(0);
	            	listTeacher.add(teacher);
	            }
	            project.setListTeacher(listTeacher);
	            listProject.add(project);
	        }  
	        inputStream.close();  
	        bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return listProject;
	}
	
	public List<List<String>> readChoiceFile(String filename){
		FileInputStream inputStream;
		List<List<String>> listChoice = new ArrayList<List<String>>();
		
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
            
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	        	List<String> listChoiceForEachGroup = new ArrayList<String>();
	            String memberIds = str.split("\t")[0];
	            String choiceIds = str.split("\t")[1];
	            
	            listChoiceForEachGroup.add(memberIds);
	            
	            String [] studentIds = memberIds.split(";");
	            StudentService studentService = new StudentService();
	            String memberWholeNames = "";
	            for(int i = 0; i < studentIds.length; i++){
	            	Student student = studentService.getStudentInfo("idStudent", studentIds[i]).get(0);
	            	memberWholeNames += student.getName() + " " + student.getSurname() + ";";
	            }
	            if(!memberWholeNames.equals(""))
	            	memberWholeNames = memberWholeNames.substring(0, memberWholeNames.length()-1);
	            listChoiceForEachGroup.add(memberWholeNames);
	            
	            listChoiceForEachGroup.add(choiceIds);
	            
	            String [] projectIds = choiceIds.split(";");
	            ProjectService projectService = new ProjectService();
	            String choiceSubjects = "";
	            String courseName = "";
	            String schoolYear = "";
	            for(int j = 0; j < projectIds.length; j++) {
	            	Project project = projectService.searchProjectsForOneCourse("idProject", projectIds[j]).get(0);
	            	choiceSubjects += project.getSubject() + ";";
	            	courseName = project.getCourse().getNom();
	            	schoolYear = project.getCourse().getSchoolYear();
	            }
	            if(!choiceSubjects.equals(""))
	            	choiceSubjects = choiceSubjects.substring(0, choiceSubjects.length()-1);
	            listChoiceForEachGroup.add(choiceSubjects);
	            listChoiceForEachGroup.add(courseName);
	            listChoiceForEachGroup.add(schoolYear);
	            
	            listChoice.add(listChoiceForEachGroup);
	        }  
	        inputStream.close();  
	        bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return listChoice;
	}
	public void deleteFile(String filename){
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
