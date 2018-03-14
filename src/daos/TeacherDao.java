package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import models.Teacher;

public class TeacherDao {
	
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
	
	public void update(Teacher teacher,EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(teacher);
        transaction.commit();
	}
}
