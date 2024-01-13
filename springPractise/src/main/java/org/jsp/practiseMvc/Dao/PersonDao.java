package org.jsp.practiseMvc.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsp.practiseMvc.Dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {
	
//	@Autowired
	private static EntityManager manager = Persistence.createEntityManagerFactory("practise").createEntityManager();
	private static EntityTransaction t = manager.getTransaction();
	
	public Person savePerson(Person p) {
		manager.persist(p);
		t.begin();
		t.commit();
		return p;
	}
	
	public Person updatePerson(Person p) {
		Person per = manager.find(Person.class, p.getId());
		if(per!=null) {
			per.setName(p.getName());
			per.setEmail(p.getEmail());
			per.setPhone(p.getPhone());
			per.setPassword(p.getPassword());
			manager.merge(per);
			t.begin();
			t.commit();
			return p;
		}
		return null;
	}
	
	public Person findById(int id) {
		return manager.find(Person.class, id);
	}
	
	public Person verifyByPhoneAndPassword(long phone,String password) {
		Query q = manager.createQuery("select p from Person p where p.phone=?1 and p.password=?2");
		q.setParameter(1, phone);
		q.setParameter(2, password);
		try {
			return (Person)q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	
	public Person verifyByEmailAndPassword(String email,String password) {
		Query q = manager.createQuery("select p from Person p where p.email=?1 and p.password=?2");
		q.setParameter(1, email);
		q.setParameter(2, password);
		try {
			return (Person)q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	
	public Person verifyByIdAndPassword(int id,String password) {
		Query q = manager.createQuery("select p from Person p where p.id=?1 and p.password=?2");
		q.setParameter(1, id);
		q.setParameter(2, password);
		try {
			return (Person)q.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	
	public boolean deleteById(int id) {
		Person p = findById(id);
		if(p!=null) {
			manager.remove(p);
			t.begin();
			t.commit();
			return true;
		}
		return false;
	}
	
}
