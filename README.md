# Laboration 1

#### Fyra studenter genereras vid uppstart ####
 - Bertil Eriksson, bertil.eriksson@gmail.com, 073-8492384
 - Gunnar Göransson, gunnar.goransson@gmail.com, 073-3514861
 - Berit Svensson, berit.svensson@gmail.com, 073-3794882
 - Lisa Johansson, lisa.johansson@gmail.com, 073-5822493

## <p align="center" font-size="5">Student requests</p>

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

------------------

# Laboration 2

#### Fyra lärare och fem ämnen genereras vid uppstart ####
>Lärare
 - Sven Gunnarsson, sven.gunnarsson@gmail.com, 073-5468517
 - Anna Mårtensson, anna.martensson@gmail.com, 073-4825823
 - Frida Karlsson, frida.karlsson@gmail.com, 073-9755814
 - Gun Persson, gun.persson@gmail.com, 073-2504816
>Ämnen
 - Matematik, 
 	- Lärare
 		- Sven Gunnarsson
 	- Elever
 		- Bertil Eriksson
 		- Gunnar Göransson
 - Engelska
   	- Lärare
 		- Anna Mårtensson
 	- Elever
 		- Bertil Eriksson
 		- Gunnar Göransson
 		- Berit Svensson
 - Svenska
     - Lärare
 		- Frida Karlsson
 	- Elever
 		- Bertil Eriksson
 		- Berit Svensson
 - Gymnastik
   	- Lärare
 		- Gun Persson
 	- Elever
 		- Gunnar Göransson
 		- Lisa Johansson
 - Tyska
     - Lärare
 		- Frida Karlsson
 	- Elever
 		- Lisa Johansson
 
## <p align="center" font-size="5">Teacher requests</p>

### **-Find teacher by ID**
@GET
http://localhost:8080/student-management-system/api/v1/teachers/{id}
>Om läraren inte finns så skickas status code 404 Not found(Custom exception)


### **-Get all teachers**
@GET
http://localhost:8080/student-management-system/api/v1/teachers/


### **-Find teacher CUSTOM**
@GET
http://localhost:8080/student-management-system/api/v1/teachers/custom/
>Query parameters: (firstname, lastname, email, phonenumber)

>Om ingen parameter är vald skickas status code 400 Bad Request(Custom exception)


### **-Add new teacher**
@POST
http://localhost:8080/student-management-system/api/v1/teachers/
>Om för- och efternamn inte finns och email redan finns så skickas code 400 Bad Request(Custom exception)

	{
		"firstName": "Håkan",
		"lastName": "Nilsson",
		"email": "hakan.nilsson@gmail.com",
		"phoneNumber": "076-5444827"
	}	
	
	
### **-Update one teacher**
@PUT
http://localhost:8080/student-management-system/api/v1/teachers
>Om id inte finns med, för- och efternamn inte finns och email redan finns så skickas code 400 Bad Request(Custom exception)

	{
		"id": 2,
		"firstName": "Magnus",
		"lastName": "Lennartsson",
		"email": "magnus.lennartsson@gmail.com",
		"phoneNumber": "076-4815527"
	}
	
	
### **-Update one teacher CUSTOM**
@PATCH
http://localhost:8080/student-management-system/api/v1/teachers/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

	{
		"firstName": "Lena",
		"lastName": "Magnusson",
		"email": "lena.magnusson@gmail.com",
		"phoneNumber": "076-6844492"
	}	


	
	
### **-Delete one teacher**
@Delete
http://localhost:8080/student-management-system/api/v1/students/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

## <p align="center" font-size="5">Teacher requests</p>

### **-Find Subject by ID**
@GET
http://localhost:8080/student-management-system/api/v1/subjects/{id}
>Om ämnet inte finns så skickas status code 404 Not found(Custom exception)

### **-Find Subject of teacher**
@GET
http://localhost:8080/student-management-system/api/v1/subjects/teachers/{id}

### **-Find Subject of student**
@GET
http://localhost:8080/student-management-system/api/v1/subjects/students/{id}

### **-Get all subjects**
@GET
http://localhost:8080/student-management-system/api/v1/subjects/

### **-Add new subject**
@POST
http://localhost:8080/student-management-system/api/v1/subjects/

	{
		"name": "Gymnastik"
	}
>Om ämnet redan finns så skickas code 400 Bad Request(Custom exception)

### **-Add teacher to subject**
@PATCH
http://localhost:8080/student-management-system/api/v1/subjects/{id}/teachers/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

### **-Add student to subject**
@PATCH
http://localhost:8080/student-management-system/api/v1/subjects/{id}/students/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

>Om studenten redan finns så skickas code 400 Bad Request(Custom exception)

### **-Remove teacher from subject**
@PATCH
http://localhost:8080/student-management-system/api/v1/subjects/{id}/removeteacher
>Query parameters: (email)

>Om id inte hittas skickas status code 404 Not found(Custom exception)

>Om lärare inte finns eller hittas så skickas code 500 Internal server error(Custom exception)

### **-Remove student from subject**
@PATCH
http://localhost:8080/student-management-system/api/v1/subjects/{id}/removestudent
>Query parameters: (email)

>Om id inte hittas skickas status code 404 Not found(Custom exception)

>Om student inte finns eller hittas så skickas code 500 Internal server error(Custom exception)

### **-Delete one subject**
@Delete
http://localhost:8080/student-management-system/api/v1/subjects/{id}
>Om id inte hittas skickas status code 404 Not found(Custom exception)

