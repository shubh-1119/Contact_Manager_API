package com.contactmanager.dao;

import java.util.List;

import com.contactmanager.model.Contact;

public interface ContactDAO {

	void save(List<Contact> contacts);
	void update(Long id, Contact contact);
	void delete(Long id);
	List<Contact> list();
	Contact get(Long id);
	List<Contact> serchContact(String query);
	Long totalContacts();
	List<Contact> list(int pageNumber, int pageSize, String orderBy);
	
}
