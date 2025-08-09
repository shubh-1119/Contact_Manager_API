### **Contact Manager API**

This is a RESTful API for managing contacts, built using Spring MVC, Hibernate, and MySQL. It provides a full set of features for creating, reading, updating, and deleting contact information, along with several advanced functionalities.

#### 

#### 

#### **Features**

* **CRUD Operations**: Complete functionality to Create, Read, Update, and Delete individual contacts.  
* **Bulk Insertion**: An endpoint to efficiently create multiple contacts in a single request.  
* **Data Validation**: Implemented validation to ensure that contact data (e.g., email format, required fields) is correct before saving it to the database.  
* **Pagination & Sorting**: The /contacts endpoint supports pagination and sorting by different fields, making it suitable for handling large datasets.  
* **Search Functionality**: A search endpoint to query contacts by first name or email.  
* **Contact Analytics**: A simple analytics endpoint that returns the total count of contacts in the database.

#### 

#### 

#### **Technologies Used**

* **Framework**: Spring MVC  
* **ORM**: Hibernate  
* **Database**: MySQL  
* **Build Tool**: Maven  
* **Server**: Apache Tomcat

#### 

#### **API Endpoints**

All endpoints use the base URL: http://localhost:8080/contact-manager-api/contacts

| Method | Endpoint | Description |
| :---- | :---- | :---- |
| POST | / | Creates one or more contacts. |
| GET | / | Retrieves all contacts with optional pagination and sorting. |
| GET | /{id} | Retrieves a single contact by its ID. |
| PUT | /{id} | Updates an existing contact by its ID. |
| DELETE | /{id} | Deletes a contact by its ID. |
| GET | /search?query=john | Searches for contacts by first name or email. |
| GET | /count | Returns the total number of contacts. |

