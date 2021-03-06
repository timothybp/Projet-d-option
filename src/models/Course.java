package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Course {
	
	@Id
	private String idCourse;
	
	private String nom;
	
	private Date beginDate;
	private Date endDate;
	private int hours;
	private int semester;
	private int membreAmount;
	private float weights;
	private Date choosingDeadline;
	private String schoolYear;
	private String department;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="idResponsable")
	private Teacher teacher;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="course")
	private List<Project> listProject;
	
	
	public String getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(String idCourse) {
		this.idCourse = idCourse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}
	
	public int getMembreAmount() {
		return membreAmount;
	}

	public void setMembreAmount(int membreAmount) {
		this.membreAmount = membreAmount;
	}

	public float getWeights() {
		return weights;
	}

	public void setWeights(float weights) {
		this.weights = weights;
	}

	public String getSchoolYear() {
		return schoolYear;
	}
	
	public Date getChoosingDeadline() {
		return choosingDeadline;
	}

	public void setChoosingDeadline(Date choosingDeadline) {
		this.choosingDeadline = choosingDeadline;
	}
	
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Project> getListProject() {
		return listProject;
	}

	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}
}
