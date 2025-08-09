package com.contactmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.dao.ContactDAO;
import com.contactmanager.model.Contact;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDAO contactDAO;
	
	@Override
	public void save(List<Contact> contacts) {
		contactDAO.save(contacts);
	}

	@Override
	public void update(Long id, Contact contact) {
		contactDAO.update(id, contact);
	}

	@Override
	public void delete(Long id) {
		contactDAO.delete(id);

	}

	@Override
	public List<Contact> list() {
		List<Contact> list = contactDAO.list();
		return list;
	}

	@Override
	public Contact get(Long id) {
		Contact contact = contactDAO.get(id);
		return contact;
	}
	
	@Override
	public List<Contact> serchContact(String query){
		return contactDAO.serchContact(query);
	}

	@Override
	public Long totalContacts() {
		return contactDAO.totalContacts();
	}
	
	@Override
	public List<Contact> list(int pageNumber, int pageSize, String orderBy){
		return contactDAO.list(pageNumber, pageSize, orderBy);
	}
	
}
