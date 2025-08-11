package com.contactmanager.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.model.Contact;
import com.contactmanager.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	ServletContext servletContext;
	
	// save contacts
	@PostMapping
	public ResponseEntity<String> saveContact(@Valid @RequestBody List<Contact> contact, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>("Validation Failed : "+ bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		contactService.save(contact);
		return new ResponseEntity<>("Contact created successfully", HttpStatus.CREATED);
	}
	
	//update contact
	@PutMapping("/{id}")
	public ResponseEntity<String> updateContact(@Valid @PathVariable("id") Long id, @RequestBody Contact contact, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>("Validation Failed : "+ bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		contactService.update(id, contact);
		return new ResponseEntity<>("Contact updated successfully", HttpStatus.OK);
	}
	 
	// delete contact
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable("id") Long id){
			contactService.delete(id);
		return new ResponseEntity<>("Contact Deleted successfully", HttpStatus.OK);
	}
	
	//get contact by id
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable Long id){
		Contact contact = contactService.get(id);
		if(contact != null) {
			return new ResponseEntity<>(contact, HttpStatus.OK);
		}else {
			return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
		} 
	}
	
	//get searched contact by name or email
	@GetMapping("/search")
	public ResponseEntity<List<Contact>> serchContact(@RequestParam("query") String query){
		List<Contact> contacts = contactService.serchContact(query);
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}
	
	//get total numbers of contacts
	@GetMapping("/counts")
	public ResponseEntity<Long> totalContacts(){
		Long count = contactService.totalContacts();
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	//get all contacts + with pagination
	@GetMapping
	public ResponseEntity<List<Contact>> getlist(
			@RequestParam(defaultValue = "1") int pageNumber, 
			@RequestParam(defaultValue = "10") int pageSize, 
			@RequestParam(defaultValue = "firstName") String orderBy){
		List<Contact> contacts = contactService.list(pageNumber, pageSize, orderBy);
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}
	
	@PostMapping("/{id}/image")
	public ResponseEntity<String> uploadImage(@PathVariable("id") Long id, MultipartFile file){
		if(file.isEmpty()) {
			return new ResponseEntity<>("Select File to upload", HttpStatus.BAD_REQUEST);
		}
		
		try {
			Contact contact = contactService.get(id);
			if(contact == null) {
				return new ResponseEntity<>("Contact not available with id: "+id, HttpStatus.NOT_FOUND);			
			}
			
			String rootPath = servletContext.getRealPath("/");
			File uploadDir = new File(rootPath + "resources" + File.separator + "images"); 
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			String originalFileName = file.getOriginalFilename();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String uniqueFileName = UUID.randomUUID() + fileExtension;
			
			File dest = new File(uploadDir, uniqueFileName);
			
			//saving file
			file.transferTo(dest);
			
			//update the contact
			String relativePath = "/resources/images/" + uniqueFileName;
			contact.setProfileImagePath(relativePath);
			contactService.update(id, contact);		
			return new ResponseEntity<>("File uploaded successfully" + uploadDir, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("File upload Error" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
