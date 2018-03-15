package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import daos.CourseDao;
import models.Course;
import models.Project;
import models.Teacher;

public class CourseService {
	
	private CourseDao courseDao;
	private EntityManager em;
	
	public CourseService() {
		courseDao = new CourseDao();
		em = courseDao.connect();
	}
	
	public List<Course> searchCourses(String attributeName, String attributeValue) {
		String query = "";
		
		if(attributeName.equals("idCourse")){
			String idCourse = attributeValue;
			query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
					+ " course.hours, course.membreAmount, course.weights, course.choosingDeadline,"
					+ " course.semester, course.department, course.teacher" 
					+ " FROM Course course"
					+ " WHERE course.idCourse = '" + idCourse + "'";
		}
		
		if(attributeName.equals("schoolYear+department")){
			String department = attributeValue;
			query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
					+ " course.hours, course.membreAmount, course.weights, course.choosingDeadline,"
					+ " course.semester, course.department, course.teacher" 
					+ " FROM Course course"
					+ " WHERE course.department = '" + department + "'"
					+ " AND course.schoolYear = '" + judgeSchoolYear() + "'";
		}
		
		if(attributeName.equals("semester")){
			String semester = attributeValue.split("-")[0];
			String department = attributeValue.split("-")[1];
			query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
					+ " course.hours, course.membreAmount, course.weights, course.choosingDeadline,"
					+ " course.semester, course.department, course.teacher" 
					+ " FROM Course course"
					+ " WHERE course.semester = " + semester
					+ " AND course.department = '" + department + "'"
					+ " AND course.schoolYear = '" + judgeSchoolYear() + "'";
		}
		
		if(attributeName.equals("nom")){
			String nom = attributeValue;
			query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
					+ " course.hours, course.membreAmount, course.weights, course.choosingDeadline,"
					+ " course.semester, course.department, course.teacher" 
					+ " FROM Course course"
					+ " WHERE course.nom = '" + nom + "'"
					+ " AND course.schoolYear = '" + judgeSchoolYear() + "'";
		}
		
		if(attributeName.equals("nom+schoolYear")){
			String nom = attributeValue.split("_")[0];
			String schoolYear = attributeValue.split("_")[1];
			query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
					+ " course.hours, course.membreAmount, course.weights, course.choosingDeadline,"
					+ " course.semester, course.department, course.teacher" 
					+ " FROM Course course"
					+ " WHERE course.nom = '" + nom + "'"
					+ " AND course.schoolYear = '" + schoolYear + "'";
		}
		
		List result = courseDao.select(query,em);
		
		List<Course> listCourse = new ArrayList<Course>();
		ProjectService projectService = new ProjectService();
		for(int i = 0; i < result.size(); i++ ) {
			Course course = new Course();
			Object [] obj = (Object[])result.get(i);
			course.setIdCourse((String)obj[0]);
			course.setNom((String)obj[1]);
			course.setBeginDate(convertDateFormat((Date)obj[2]));
			course.setEndDate(convertDateFormat((Date)obj[3]));
			course.setHours((Integer)obj[4]);
			course.setMembreAmount((Integer)obj[5]);
			course.setWeights((Float)obj[6]);
			course.setChoosingDeadline(convertDateFormat((Date)obj[7]));
			course.setSemester((Integer)obj[8]);
			course.setDepartment((String)obj[9]);
			course.setTeacher((Teacher)obj[10]);
			course.setSchoolYear(judgeSchoolYear());
			course.setListProject(projectService.searchProjectsForOneCourse("idCourse",(String)obj[0]));
			listCourse.add(course);
		}
		return listCourse;
	}
	
	public String judgeSchoolYear() {
		Date date=new Date();    
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int currentMonth = Integer.parseInt(sdf.format(date).split("/")[1]);
		int currentYear = Integer.parseInt(sdf.format(date).split("/")[2]);
		String currentSchoolYear;
		if(currentMonth < 9) {
			currentSchoolYear = String.valueOf(currentYear - 1) + "-" + String.valueOf(currentYear);
		}
		else {
			currentSchoolYear = String.valueOf(currentYear) + "-" + String.valueOf(currentYear + 1);
		}
		return currentSchoolYear;
	}
	
	public Date convertDateFormat(Date dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formatDate = sdf.format(dateTime);  
		Date date = null;
		try {
			date = sdf.parse(formatDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public Date recoverDateFormat(String dateTime) {
		String date = dateTime.split(" ")[0];
		String day = date.split("/")[0];
		String month = date.split("/")[1];
		String year = date.split("/")[2];
		
		String time = dateTime.split(" ")[1];
		String hour = time.split(":")[0];
		String minute = time.split(":")[1];
		String second = time.split(":")[2];
		
		String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = new Date(); 
		try {
			newDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	
	//Verify if all characters are numeric
	public boolean isNumeric(String str){
		for(int i = 0;i < str.length();i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	public boolean judgeDateFormat(String dateStr) {
		String [] date = dateStr.split("/");
		if(date.length == 3){
			if(isNumeric(date[0]) == true && isNumeric(date[1]) == true && isNumeric(date[2]) == true){
				if((Integer.valueOf(date[0]) >= 1 && Integer.valueOf(date[0]) <= 31) &&
						(Integer.valueOf(date[1]) >= 1 && Integer.valueOf(date[1]) <= 12) &&
						date[2].length() == 4) {
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public void save(Course course) {
		courseDao.insert(course, em);
	}
	
	public void updateCourseInfo(Course course) {
		courseDao.update(course,em);
	}
}
