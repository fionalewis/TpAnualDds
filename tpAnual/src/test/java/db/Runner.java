package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;



public class Runner {

	public static void main(String[] args) {
		
		
		EntityManager entityManager = PerThreadEntityManagers.getEntityManager();//un sigletone del entitymanager
		EntityTransaction transaccion = entityManager.getTransaction();
	}

}
