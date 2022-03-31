# Laboration 1


### **-Find student by ID**
@GET
http://localhost:8080/student-management-system/api/v1/students/1
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
		"phoneNumber": "0765429515"
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
		"phoneNumber": "0766718264"
	}	
	
	
### **-Update one student CUSTOM**
@PATCH
http://localhost:8080/student-management-system/api/v1/students/1
>Query parameters: (firstname, lastname, email, phonenumber) 

>Om id inte hittas skickas status code 404 Not found(Custom exception)
	
	
### **-Delete one student**
@Delete
http://localhost:8080/student-management-system/api/v1/students/1
>Om id inte hittas skickas status code 404 Not found(Custom exception)
	
