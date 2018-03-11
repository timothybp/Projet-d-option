package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import daos.StudentDao;
import models.Student;

public class StudentService {
	private StudentDao studentDao;
	private EntityManager em;
	
	public StudentService() {
		studentDao = new StudentDao();
		em = studentDao.connect();
	}
	
	//verify if the student can successfully login
	public int verifyLogin(String username, String password) {
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
	
	public List<Student> getStudentInfo(String attributeName, String attributeValue) {
		String query = "";
		
		if(attributeName.equals("idStudent")){
			String idStudent = attributeValue;
			query = "SELECT student.idStudent, student.name, student.surname, student.department, student.grade, student.photoPath" 
					+ " FROM Student student"
					+ " WHERE student.idStudent = " + idStudent;
		}
		
		if(attributeName.equals("wholeName")){
			String name = attributeValue.split("_")[0];
			String surname = attributeValue.split("_")[1];
			query = "SELECT student.idStudent, student.name, student.surname, student.department, student.grade, student.photoPath" 
					+ " FROM Student student"
					+ " WHERE student.name = '" + name + "'"
					+ " AND student.surname = '" + surname + "'";
		}
		
		List result = studentDao.select(query,em);
		
		List<Student> listStudent = new ArrayList<Student>();
		for(int i = 0; i < result.size(); i++){
			Object [] obj = (Object[])result.get(i);
			
			Student student = new Student();
			student.setIdStudent((Long)obj[0]);
			student.setName((String)obj[1]);
			student.setSurname((String)obj[2]);
			student.setDepartment((String)obj[3]);
			student.setGrade((Integer)obj[4]);
			student.setPhotoPath((String)obj[5]);
			listStudent.add(student);
		}
		
		
		return listStudent;
	}
}
