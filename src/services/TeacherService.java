package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import daos.TeacherDao;
import models.Student;
import models.Teacher;

public class TeacherService {
	private TeacherDao teacherDao;
	private EntityManager em;
	
	public TeacherService() {
		teacherDao = new TeacherDao();
		em = teacherDao.connect();
	}

	//verify if the teacher can successfully login
	public int verifyLogin(String username, String password) {
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
	
	//rechercher la liste de professeur selon des diff¨¦rents attributs
	public List<Teacher> getTeacherInfo(String attributeName, String attributeValue) {
		String query = "";
		
		if(attributeName.equals("idTeacher")){
			String idTeacher = attributeValue;
			query = "SELECT teacher.idTeacher, teacher.name, teacher.surname,"
					+ " teacher.department, teacher.role, teacher.photoPath,"
					+ " teacher.birthday, teacher.password, teacher.email, teacher.sex"
					+ " FROM Teacher teacher"
					+ " WHERE teacher.idTeacher = " + idTeacher;
		}
		
		if(attributeName.equals("wholeName")){
			String name = attributeValue.split("_")[0];
			String surname = attributeValue.split("_")[1];
			query = "SELECT teacher.idTeacher, teacher.name, teacher.surname,"
					+ " teacher.department, teacher.role, teacher.photoPath,"
					+ " teacher.birthday, teacher.password, teacher.email, teacher.sex"
					+ " FROM Teacher teacher"
					+ " WHERE teacher.name = '" + name + "'"
					+ " AND teacher.surname = '" + surname + "'";
		}
		
		List resultTeacher = teacherDao.select(query,em);
		List<Teacher> listTeacher = new ArrayList<Teacher>();
		for(int i = 0; i < resultTeacher.size(); i++){
			Object [] obj = (Object[])resultTeacher.get(i);
			
			Teacher teacher = new Teacher();
			teacher.setIdTeacher((Long)obj[0]);
			teacher.setName((String)obj[1]);
			teacher.setSurname((String)obj[2]);
			teacher.setDepartment((String)obj[3]);
			teacher.setRole((String)obj[4]);
			teacher.setPhotoPath((String)obj[5]);
			teacher.setBirthday((Date)obj[6]);
			teacher.setPassword((String)obj[7]);
			teacher.setEmail((String)obj[8]);
			teacher.setSex((String)obj[9]);
			listTeacher.add(teacher);
		}
		return listTeacher;
	}
	
	//modifier le role de professeur
	public void modifyTeacherRole(Teacher teacher) {
		teacherDao.update(teacher, em);
	}
}
