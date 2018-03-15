package models;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Project {
	
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProject;
	
	private String subject;
	private String description;
	private String enterprise;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="idCourse")
	private Course course;
	
	@ManyToMany
	@NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="project_supervisor",  
            joinColumns={@JoinColumn(name="idProject")},  
            inverseJoinColumns={@JoinColumn(name="idTeacher")})  
	private List<Teacher> listTeacher;
	
	@ManyToMany
	@NotFound(action=NotFoundAction.IGNORE)
    @JoinTable(name="project_student",  
            joinColumns={@JoinColumn(name="idProject")},  
            inverseJoinColumns={@JoinColumn(name="idStudent")})  
	private List<Student> listStudent;

	
	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Teacher> getListTeacher() {
		return listTeacher;
	}

	public void setListTeacher(List<Teacher> listTeacher) {
		this.listTeacher = listTeacher;
	}

	public List<Student> getListStudent() {
		return listStudent;
	}

	public void setListStudent(List<Student> listStudent) {
		this.listStudent = listStudent;
	}
}
