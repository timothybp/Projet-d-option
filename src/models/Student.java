package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Student {
	
	@Id
	private long idStudent;
	
	private String surname;
	private String name;
	private Date birthday;
	private String sex;
	private String department;
	private int grade;
	private String email;
	private String password;
	private String photoPath;
	
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="listStudent")
	private List<Project> listProject;

	
	public long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(long idStudent) {
		this.idStudent = idStudent;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}
	
}
