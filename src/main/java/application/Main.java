package application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Person;

public class Main {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("person-jpa");
		EntityManager em = emf.createEntityManager();

//		write to the database
//		Person person = new Person(null, "João Bruno", "joao@gmail.com");
//		em.getTransaction().begin();
//		em.persist(person);
//		em.getTransaction().commit();

//		retrieve from database
		Person dbPerson = em.find(Person.class, 1);
		System.out.println(dbPerson);
		
//		remove from database
		em.getTransaction().begin();
		em.remove(dbPerson);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}

}
