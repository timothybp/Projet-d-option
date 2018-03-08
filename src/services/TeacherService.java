package services;

import java.util.List;

import javax.persistence.EntityManager;

import daos.TeacherDao;

public class TeacherService {
	private TeacherDao teacherDao;
	
	public TeacherService() {
		teacherDao = new TeacherDao();
	}
	
	//connect database
	public EntityManager connectDatabase() {
		EntityManager em = teacherDao.connect();
		return em;
	}
	
	//verify if the teacher can successfully login
	public int verifyLogin(String username, String password, EntityManager em) {
		String idTeacher = username.substring(0, username.length()-1);
		if (idTeacher.length() == 0 || (idTeacher.length() != 0 && isNumeric(idTeacher) == false)) {
			return 1;
		}
		else {
			String query = "SELECT teacher.password" 
					+ " FROM Teacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher;
			List result = teacherDao.select(query,em);
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
}
