package controllers.globalControllers;

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
	
	//cette methode est pour v¨¦rifier si l'¨¦tudiant a d¨¦j¨¤ fait les choix
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
	
	//cette m¨¦thode est pour enregistrer les choix d'etudiants dans le fichier temporaire
	public void recordChoiceToFile(List<Long> listIdStudent, List<String> listChoice, String filename){
		String content = "";
		for(long id : listIdStudent){
			content += String.valueOf(id) + ";";
		}
		content = content.substring(0, content.length()-1);
		content += "\t" + listChoice.get(0) + ";" + listChoice.get(1) + ";" + listChoice.get(2); 
		FileWriter fw = null;
		try {
			File file =new File(filename);
			fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//cette m¨¦thode est pour v¨¦rifier si le sujet de projet est existant pour ce cours
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
	
	//cette m¨¦thode est pour enregistrer les proposition de professeur au fichier temporaire
	public void recordPropositionToFile(Project project, String filename){
		String content = project.getSubject() + "\t";
		for(Teacher teacher: project.getListTeacher()){
			content += String.valueOf(teacher.getIdTeacher()) + ";";
		}
		content = content.substring(0, content.length()-1) + "\t";
		content += project.getDescription() + "\t" + project.getEnterprise();
		
		FileWriter fw = null;
		try {
			File file = new File(filename);
			fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//cette m¨¦thode est pour enregistrer les solutions d'affectation aux fichiers temporaires
	public void recordSolutions(List<List<String>> listSolution, File filepath){
		StudentService studentService = new StudentService();
		ProjectService projectService = new ProjectService();
		
		for(int i = 0 ; i < listSolution.size(); i++){
			FileWriter fw = null;
			String filename = filepath + "/solution_" + String.valueOf(i+1) + ".txt";
			
			File f = new File(filename);
			if(f.exists())
				deleteFile(filename);
			
			for(String line: listSolution.get(i)){
				String content = "";
				String studentIds = line.split("\t")[0];
				String idProject = line.split("\t")[1];
				String [] listStudentId = studentIds.split(";");
				content += studentIds + "\t";
				for(String idStudent: listStudentId) {
					Student student = studentService.getStudentInfo("idStudent", idStudent).get(0);
					content += student.getName()+ " " + student.getSurname() + ";";
				}
				content = content.substring(0, content.length() - 1) + "\t";
				content += idProject + "\t";
				Project project = projectService.searchProjectsForOneCourse("idProject", idProject).get(0);
				content += project.getSubject() + "\t";
				
				for(Teacher teacher: project.getListTeacher()){
					content += String.valueOf(teacher.getIdTeacher()) + ";";
				}
				content = content.substring(0, content.length()-1) + "\t";
				for(Teacher teacher: project.getListTeacher()){
					content += teacher.getName() + " " + teacher.getSurname() + ";";
				}
				content = content.substring(0, content.length()-1) + "\t";
				
				content += project.getEnterprise();
				
				try {
					f=new File(filename);
					fw = new FileWriter(f, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println(content);
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//cette m¨¦thode est pour lire les proposition de projets du fichier temporaire
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
	
	//cette m¨¦thode est pour lire les choix d'¨¦tudiants du fichier temporaire
	public List<List<String>> readChoiceFile(String filename){
		FileInputStream inputStream;
		List<List<String>> listChoice = new ArrayList<List<String>>();
		ProjectService projectService = new ProjectService();
		CourseService courseService = new CourseService();
		
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
	            
	            String choiceSubjects = "";
	            String courseName = "";
	            String schoolYear = "";
	            Project project = new Project();
	            for(int j = 0; j < projectIds.length; j++) {
	            	project = projectService.searchProjectsForOneCourse("idProject", projectIds[j]).get(0);
	            	choiceSubjects += project.getSubject() + ";";
	            }
	            if(!choiceSubjects.equals(""))
	            	choiceSubjects = choiceSubjects.substring(0, choiceSubjects.length()-1);
	            listChoiceForEachGroup.add(choiceSubjects);
	            
	            Course course = courseService.searchCourses("idCourse", project.getCourse().getIdCourse()).get(0);
            	courseName = course.getNom();
	            schoolYear = course.getSchoolYear();
            	
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
	
	//cette m¨¦thode est pour lire les noms de fichiers de solution dans le respiratoire temporaire 
	public List<String> readSolutionDir(String filepath){
		int fileAmount = 0;
		File dir = new File(filepath);
		List<String> listFilename = new ArrayList<String>();
		File[] files = dir.listFiles();
		for(int i = 0; i < files.length; i++){
			File f = files[i];
			String filename = f.getName().substring(0,f.getName().length()-4);
			listFilename.add(filename);
		}
		return listFilename;
	}
	
	//cette m¨¦thode est pour lire un record de solution du fichier temporaire
	public List<List<String>> readSolutionRecordFile(String filename){
		FileInputStream inputStream;
		List<List<String>> listRecord = new ArrayList<List<String>>();
		
		try {
			inputStream = new FileInputStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
			
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	        	List<String> listRecordAttribute = new ArrayList<String>();
	            String memberIds = str.split("\t")[0];
	            String memberNames = str.split("\t")[1];
	            String projectIds = str.split("\t")[2];
	            String projectNames = str.split("\t")[3];
	            String supervisorIds = str.split("\t")[4];
	            String supervisorNames = str.split("\t")[5];
	            String enterprise = str.split("\t")[6];
	            
	            listRecordAttribute.add(memberIds);
	            listRecordAttribute.add(memberNames);
	            listRecordAttribute.add(projectIds);
	            listRecordAttribute.add(projectNames);
	            listRecordAttribute.add(supervisorIds);
	            listRecordAttribute.add(supervisorNames);
	            listRecordAttribute.add(enterprise);
	            
	            listRecord.add(listRecordAttribute);
	        }  
	        inputStream.close();  
	        bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return listRecord;
	}
	
	//cette m¨¦thode est pour supprimer le fichier temporaire ou le r¨¦pertoire temporaire
	public void deleteFile(String filename){
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
