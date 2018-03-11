package services;

import java.util.List;

import javax.persistence.EntityManager;

import daos.TeacherDao;

public class AdministratorService {
	private TeacherDao teacherDao;
	private EntityManager em;
	
	public AdministratorService() {
		teacherDao = new TeacherDao();
		em = teacherDao.connect();
	}

	//verify if the administrator can successfully login
	public int verifyLogin(String username, String password) {
		String idTeacher = username.substring(0, username.length()-1);
		if (idTeacher.length() == 0 || (idTeacher.length() != 0 && isNumeric(idTeacher) == false)) {
			return 1;
		}
		else {
			String query = "SELECT teacher.password, teacher.role" 
					+ " FROM Teacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher;
			List result = teacherDao.select(query,em);
			Object [] obj = (Object[])result.get(0);
			
			if(result.size() == 0 || (String)obj[1] == null || !((String)obj[1]).equals("admin")) {
				return 1;
			}
			else {
				if(!password.equals(obj[0].toString())){
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
