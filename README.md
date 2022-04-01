# Laboration 1

#### Fyra studenter genereras vid uppstart ####
 - Bertil Eriksson, bertil.eriksson@gmail.com, 073-8492384
 - Gunnar Göransson, gunnar.goransson@gmail.com, 073-3514861
 - Berit Svensson, berit.svensson@gmail.com, 073-3794882
 - Lisa Johansson, lisa.johansson@gmail.com, 073-5822493

### **-Find student by ID**
@GET
http://localhost:8080/student-management-system/api/v1/students/{id}
>Om student inte finns så skickas status code 404 Not found(Custom exception)


### **-Get all students**
@GET
http://localhost:8080/student-management-system/api/v1/students/


### **-Find student CUSTOM**
@GET
http://localhost:8080/student-management-system/api/v1/students/custom/
>Query parameters: (firstname, lastname, email, phonenumber)

>Om ingen parameter är vald skickas status code 400 Bad Request(Custom exception)


### **-Add new student**
@POST
http://localhost:8080/student-management-system/api/v1/students/
>Om för- och efternamn inte finns och email redan finns så skickas code 400 Bad Request(Custom exception)

	{
		"firstName": "Johan",
		"lastName": "Nilsson",
		"email": "johan.nilsson@gmail.com",
		"phoneNumber": "076-5429515"
	}	
	
	
### **-Update one student**
@PUT
http://localhost:8080/student-management-system/api/v1/students
>Om id inte finns med, för- och efternamn inte finns och email redan finns så skickas code 400 Bad Request(Custom exception)

	{
		"id": 1,
		"firstName": "David",
		"lastName": "Johansson",
		"email": "David.Johansson@gmail.com",
		"phoneNumber": "076-6718264"
	}	
	
	
### **-Update one student CUSTOM**
@PATCH
http://localhost:8080/student-management-system/api/v1/students/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

	{
		"firstName": "Anna",
		"lastName": "Hansson",
		"email": "anna.hansson@gmail.com",
		"phoneNumber": "076-8244684"
	}	


	
	
### **-Delete one student**
@Delete
http://localhost:8080/student-management-system/api/v1/students/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)
	
