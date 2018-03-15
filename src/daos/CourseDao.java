package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import models.Course;

public class CourseDao {
	
	public EntityManager connect(){
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("projects_distribution");
		EntityManager em=emf.createEntityManager();
		return em;
	}
	
	public List select(String queryString,EntityManager em){
		Query query = em.createQuery(queryString); 
		List result = query.getResultList(); 
		return result;
	}
	
	public void insert(Course course, EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(course);
        transaction.commit();
	}
	
	public void update(Course course, EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        System.out.println(course.getIdCourse()+ " " + String.valueOf(course.getTeacher().getIdTeacher()));
        em.merge(course);
        transaction.commit();
	}
}
