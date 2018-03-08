package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Course {
	
	@Id
	private int idCourse;
	
	private String nom;
	
	private Date beginDate;
	private Date endDate;
	private int duration;
	private int semester;
	private int membreAmount;
	private float weight;
	private Date choosingDeadline;
	
	@ManyToOne
    @JoinColumn(name="idResponsable")
	private Teacher teacher;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="course")
	private List<Project> listProject;
	
	
	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Date getChoosingDeadline() {
		return choosingDeadline;
	}

	public void setChoosingDeadline(Date choosingDeadline) {
		this.choosingDeadline = choosingDeadline;
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
