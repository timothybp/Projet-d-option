package services;

import java.util.List;

import javax.persistence.EntityManager;

import daos.StudentDao;
import models.Student;

public class StudentService {
	private StudentDao studentDao;
	
	public StudentService() {
		studentDao = new StudentDao();
	}
	
	//connect database
	public EntityManager connectDatabase() {
		EntityManager em = studentDao.connect();
		return em;
	}
	
	//verify if the student can successfully login
	public int verifyLogin(String username, String password, EntityManager em) {
		String idStudent = username.substring(0, username.length()-1);
		if (idStudent.length() == 0 || (idStudent.length() != 0 && isNumeric(idStudent) == false)) {
			return 1;
		}
		else {
			String query = "SELECT student.password" 
					+ " FROM Student student"
					+ " WHERE student.idStudent = " + idStudent;
			List result = studentDao.select(query,em);
			if(result.size() == 0){
				return 1;
			}
			else {
				if(!password.equals(result.get(0).toString())){
					return 2;
				}
				else{
					return 0;
				}
			}
		}
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
	
	public Student getStudentInfo(String username, EntityManager em) {
		String idStudent = username.substring(0, username.length()-1);
		String query = "SELECT student.name, student.surname, student.department, student.grade, student.photoPath" 
				+ " FROM Student student"
				+ " WHERE student.idStudent = " + idStudent;
		List result = studentDao.select(query,em);
		Object [] obj = (Object[])result.get(0);
		
		Student student = new Student();
		student.setName((String)obj[0]);
		student.setSurname((String)obj[1]);
		student.setDepartment((String)obj[2]);
		student.setGrade((Integer)obj[3]);
		student.setPhotoPath((String)obj[4]);
		
		return student;
		
		
	}
}
