package daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ProjectDao {

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
}
