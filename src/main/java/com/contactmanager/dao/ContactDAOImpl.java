package com.contactmanager.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.contactmanager.model.Contact;

@Repository
@Transactional
public class ContactDAOImpl implements ContactDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(List<Contact> contacts) {
		Session session = sessionFactory.getCurrentSession();
		for(Contact contact: contacts) {
			session.save(contact);
		}
	}
	

	@Override
	public void update(Long id, Contact contact) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Contact existingContact = session.get(Contact.class, id);
		if (existingContact != null) {
			existingContact.setFirstName(contact.getFirstName());
			existingContact.setLastName(contact.getLastName());
			existingContact.setEmail(contact.getEmail());
			existingContact.setPhoneNumber(contact.getPhoneNumber());
		}
		session.update(existingContact);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Contact contact = session.get(Contact.class, id);
		if (contact != null) {
			session.delete(contact);
		}
	}

	@Override
	public List<Contact> list() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query<Contact> query = session.createQuery("from Contact", Contact.class);
		return query.list();
	}

	@Override
	public Contact get(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Contact contact = session.get(Contact.class, id);
		return contact;
	}

	@Override
	public List<Contact> serchContact(String query){
		Session session = sessionFactory.getCurrentSession();
		Query<Contact> hqlQuery = session.createQuery("FROM Contact WHERE firstName LIKE :query OR lastName LIKE :query OR email LIKE :query", Contact.class);
		hqlQuery.setParameter("query", "%"+ query +"%");
		return hqlQuery.list();
	}

	@Override
	public Long totalContacts() {
		Session session = sessionFactory.getCurrentSession();
//		return session.createQuery("select count(*) from Contact",Long.class).uniqueResult();
		Long hqlQuery = (Long) session.createQuery("select count(*) from Contact").uniqueResult();
		return hqlQuery;
	}
	
	@Override
	public List<Contact> list(int pageNumber, int pageSize, String orderBy){
		Session session = sessionFactory.getCurrentSession();
		String hql="from Contact order by " + orderBy;
		Query<Contact> query = session.createQuery(hql, Contact.class);
		
		//pagination
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	
	
}
