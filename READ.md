Creating an Invoice Handler Application:

First Step:
    Deciding what to use. I choose Spring with mongodb.

Second Step:
    Create Spring project with Spring Initialzr:
        1) Choosing Maven and Java.
        2) Spring Boot 3.3.2 Version.
        3) Editing Project Metadata:
            a) Group: com.invoicehandler
            b) Artifact: webapp
            c) Name: webapp
            d) Description: Web application for creating, reading and listing invoices.
            e) Package name: com.invoicehandler.webapp
            f) Packaging: Jar
            g) Java: 17
        4) Adding dependencies that we will use throughout the application:
            a) Spring Web:
            b) Spring Data JPA:
            c) MySQL Driver:
            d) ThymeLeaf:
            e) Lombok:
            f) Spring Boot DevTools:
            g) Spring Security: 
    After setting up everything, we click on the "GENERATE" button and create a folder on our local computer where we will download the generated .zip with the name that we gave in the name field.
Third Step:
    1. Now we need to set up our mySQL Database. I am using XAMP for this.
    2. After starting Apache webserver and MySQL Database we need to go to the http://localhost/phpmyadmin/ page where through the phpMyAdmin page we can create a New Database with the Table USER and ROLE:
        a)USER: id, name, password, role, lastLogin
        b)ROLE: id, name, desc
    3. After Creating the table I inserted default user and role data to test it.
        a)ROLE data: [
                        {"_id": "1", "desc": "Has access to all the pages and the Admin page.", "name": "ADMIN"},
                        {"_id": "2", "desc": "Can create invoices, see the invoice list, and read the invoices. Does not have permission to the admin page.", "name": "ACCOUNTANT"},
                        {"_id": "3", "desc": "Can see the invoices page and read the invoices.", "name": "USER"}
                     ]
        b)USER data: [
                        {"_id": "1", "name": "admin", "password": "admin", "role": "admin", "last_login": "2024-02-02"},
                        {"_id": "2", "name": "accountant", "password": "accountant", "role": "accountant", "last_login":"2024-02-02"},
                        {"_id": "3", "name": "user", "password": "user", "role": "user", "last_login": "2024-02-02"}
                    ]
    4. Now that we created the database with basic values we need to connect it to our project. At the Project folder lets go to the /src/main/resources/application.properties files.
    5. Here we need to add the following lines:
        a) spring.datasource.url=jdbc:mysql://localhost:3306/invoicehandler?useSSL=false
        b) spring.datasource.username=root
        c) spring.datasource.password=root
Fourth Step:
(Creating files in MVC design pattern)
    1. The folders that I create:
        a) models
        b)
        c)
    2. Creating the UserEntity.java and the Role.java files in the /src/main/java/com.invoicehandler.webapp/models folder.
    3. In the UserEntity.java file we create a private id, name, password, role and lastLogin variables.
        a) For the variable we create an empty constructor and a constructor with our variable.
        b) We do not create the getters and setters because when we created the project we imported the Lombok library, and through this we can use the @Getter and @Setter annotation that solve this for us.
        c) Before the class we call the @Entity annotation that ...
        d) For the Long id variable we call the @Id and the @GeneratedValue(strategy = GenerationType.IDENTITY) annotations to make it autogenerate an id and ...
Fifth Step:
    





