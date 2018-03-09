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
	
	public CourseService() {
		courseDao = new CourseDao();
	}
	
	public List<Course> searchCourseForOneSemester(int semester, EntityManager em) {
		String query = "SELECT course.idCourse, course.nom, course.beginDate, course.endDate,"
				+ " course.hours, course.membreAmount, course.weights,"
				+ " course.choosingDeadline, course.teacher" 
				+ " FROM Course course"
				+ " WHERE course.semester = " + semester 
				+ " AND course.schoolYear = '" + judgeSchoolYear() + "'";
		List result = courseDao.select(query,em);
		
		List<Course> listCourse = new ArrayList<Course>();
		for(int i = 0; i < result.size(); i++ ) {
			Course course = new Course();
			Object [] obj = (Object[])result.get(i);
			course.setIdCourse((Integer)obj[0]);
			course.setNom((String)obj[1]);
			course.setBeginDate(convertDateFormat((Date)obj[2]));
			course.setEndDate(convertDateFormat((Date)obj[3]));
			course.setHours((Integer)obj[4]);
			course.setMembreAmount((Integer)obj[5]);
			course.setWeights((Float)obj[6]);
			course.setChoosingDeadline(convertDateFormat((Date)obj[7]));
			course.setTeacher((Teacher)obj[8]);
			course.setSemester(semester);
			course.setSchoolYear(judgeSchoolYear());
			course.setListProject(new ArrayList<Project>());
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
}
