package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
					+ " WHERE project.idProject = " + String.valueOf(project.getIdProject());
			List resultTeacher = projectDao.select(query,em);
			List<Teacher> listTeacher = new ArrayList<Teacher>();
			for(int j = 0; j < resultTeacher.size(); j++) {
				Teacher teacher = new Teacher();
				Object [] obj2 = (Object[])resultTeacher.get(j);
				teacher.setIdTeacher((Long)obj2[0]);
				teacher.setName((String)obj2[1]);
				teacher.setSurname((String)obj2[2]);
				listTeacher.add(teacher);
			}
			
			query = "SELECT student.idStudent, student.name, student.surname"
					+ " FROM Project project JOIN project.listStudent student"
					+ " WHERE project.idProject = " + String.valueOf(project.getIdProject());
			List resultStudent = projectDao.select(query,em);
			List<Student> listStudent = new ArrayList<Student>();
			for(int k = 0; k < resultStudent.size(); k++) {
				Student student = new Student();
				Object [] obj3 = (Object[])resultStudent.get(k);
				student.setIdStudent((Long)obj3[0]);
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
	
	public List<Project> ViewMyOwnProjets(String attechement, String idTeacher) {
		String query = "";
		String indicatorType = attechement.split("_")[0];
		String indicatorValue = attechement.split("_")[1];
		
		if(indicatorType.equals("all")){
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher;
		}
		
		if(indicatorType.equals("idProject")) {
			String idProject = indicatorValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher
					+ " AND project.idProject = " + idProject;
		}
		
		if(indicatorType.equals("subject")) {
			String subject = indicatorValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher
					+ " AND project.subject = '" + subject + "'";
		}
		
		if(indicatorType.equals("semester")) {
			String semester = indicatorValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " JOIN project.course course"
					+ " WHERE teacher.idTeacher = " + idTeacher
					+ " AND course.semester = " + semester;
		}
		
		if(indicatorType.equals("startYear")) {
			String startYear = indicatorValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " JOIN project.course course"
					+ " WHERE teacher.idTeacher = " + idTeacher
					+ " AND DATE(course.beginDate) LIKE '%" + startYear + "%'";
		}
		
		if(indicatorType.equals("endYear")) {
			String endYear = indicatorValue;
			query = "SELECT project.idProject, project.subject,"
					+ " project.description, project.enterprise"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " JOIN project.course course"
					+ " WHERE teacher.idTeacher = " + idTeacher
					+ " AND DATE(course.endDate) LIKE '%" + endYear + "%'";
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
			
			query = "SELECT teacher.idTeacher, teacher.name, teacher.surname"
					+ " FROM Project project JOIN project.listTeacher teacher"
					+ " WHERE project.idProject = " + String.valueOf(project.getIdProject());
			List resultTeacher = projectDao.select(query,em);
			List<Teacher> listTeacher = new ArrayList<Teacher>();
			for(int j = 0; j < resultTeacher.size(); j++) {
				Teacher teacher = new Teacher();
				Object [] obj2 = (Object[])resultTeacher.get(j);
				teacher.setIdTeacher((Long)obj2[0]);
				teacher.setName((String)obj2[1]);
				teacher.setSurname((String)obj2[2]);
				listTeacher.add(teacher);
			}
			
			query = "SELECT student.idStudent, student.name, student.surname"
					+ " FROM Project project JOIN project.listStudent student"
					+ " WHERE project.idProject = " + String.valueOf(project.getIdProject());
			List resultStudent = projectDao.select(query,em);
			List<Student> listStudent = new ArrayList<Student>();
			for(int k = 0; k < resultStudent.size(); k++) {
				Student student = new Student();
				Object [] obj3 = (Object[])resultStudent.get(k);
				student.setIdStudent((Long)obj3[0]);
				student.setName((String)obj3[1]);
				student.setSurname((String)obj3[2]);
				listStudent.add(student);
			}
			
			query = "SELECT course.idCourse, course.nom, course.semester, course.schoolYear,"
					+ " course.beginDate, course.endDate"
					+ " FROM Project project JOIN project.course course"
					+ " WHERE project.idProject = " + String.valueOf(project.getIdProject());
			List resultCourse = projectDao.select(query,em);
			Course course = new Course();
			Object [] obj4 = (Object[])resultCourse.get(0);
			course.setIdCourse((String)obj4[0]);
			course.setNom((String)obj4[1]);
			course.setSemester((Integer)obj4[2]);
			course.setSchoolYear((String)obj4[3]);
			CourseService courseService = new CourseService();
			course.setBeginDate(courseService.convertDateFormat((Date)obj4[4]));
			course.setEndDate(courseService.convertDateFormat((Date)obj4[5]));
			
			project.setListTeacher(listTeacher);
			project.setListStudent(listStudent);
			project.setCourse(course);
			listProject.add(project);
		}
		return listProject;
	}
	
	public void save(Project project) {
		projectDao.insert(project, em);
	}
	
	public void upadateProjectInfo(Project project) {
		projectDao.update(project, em);
	}
}
