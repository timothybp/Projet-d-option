package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import daos.ProjectDao;
import models.Course;
import models.Project;
import models.Student;
import models.Teacher;

public class ProjectService {
	
	private ProjectDao projectDao;
	private EntityManager em;
	
	public ProjectService() {
		projectDao = new ProjectDao();
		em = projectDao.connect();
	}
	
	public List<Project> searchProjectsForOneCourse(String AttributeName, String AttributeValue) {		
		String query = "";
		if(AttributeName.equals("idCourse")){
			String idCourse = AttributeValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise, course.idCourse"
					+ " FROM Project project JOIN project.course course"
					+ " WHERE course.idCourse = '" + idCourse + "'" ;
		}
		
		if(AttributeName.equals("idProject")){
			String idProject = AttributeValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise, course.idCourse"
					+ " FROM Project project JOIN project.course course"
					+ " WHERE project.idProject = " + idProject;
		}
		
		List resultProject = projectDao.select(query,em);
		List<Project> listProject = new ArrayList<Project>();
		for(int i = 0; i < resultProject.size(); i++){
			Project project = new Project();
			Object [] obj1 = (Object[])resultProject.get(i);
			project.setIdProject((Integer)obj1[0]);
			project.setSubject((String)obj1[1]);
			project.setDescription((String)obj1[2]);
			project.setEnterprise((String)obj1[3]);
			
			Course course = new Course();
			course.setIdCourse((String)obj1[4]);
			project.setCourse(course);
			
			query = "SELECT teacher.idTeacher, teacher.name, teacher.surname"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " WHERE project.idProject = " + project.getIdProject();
			List resultTeacher = projectDao.select(query,em);
			List<Teacher> listTeacher = new ArrayList<Teacher>();
			for(int j = 0; j < resultTeacher.size(); j++) {
				Teacher teacher = new Teacher();
				Object [] obj2 = (Object[])resultTeacher.get(j);
				teacher.setIdTeacher((Integer)obj1[0]);
				teacher.setName((String)obj2[1]);
				teacher.setSurname((String)obj2[2]);
				listTeacher.add(teacher);
			}
			
			query = "SELECT student.idStudent, student.name, student.surname"
					+ " FROM Project project JOIN project.listStudent student"
					+ " WHERE project.idProject = " + project.getIdProject();
			List resultStudent = projectDao.select(query,em);
			List<Student> listStudent = new ArrayList<Student>();
			for(int k = 0; k < resultStudent.size(); k++) {
				Student student = new Student();
				Object [] obj3 = (Object[])resultStudent.get(i);
				student.setIdStudent((Integer)obj3[0]);
				student.setName((String)obj3[1]);
				student.setSurname((String)obj3[2]);
				listStudent.add(student);
			}
			
			project.setListTeacher(listTeacher);
			project.setListStudent(listStudent);
			listProject.add(project);
		}
		return listProject;
	}
}
