ALTER TABLE POST
  ALTER COLUMN create_date SET DEFAULT CURRENT_TIMESTAMP;

-- Users
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES
  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'sam@mail.com', 'sam', 'Sam', 'Wilson',
   1);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES
  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'john@gmail.com', 'john', 'John', 'Doe', 1);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'ana@mail.com', 'ana', 'Ana', 'Mull', 1);

-- Roles
INSERT INTO ROLE (role_id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE (role_id, role)
VALUES (2, 'ROLE_USER');

-- User Roles
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 2);

-- Posts
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (1, 1, 'Title 1',
        '"Spring Boot is an open source Java-based framework used to create a Micro Service. It is developed by Pivotal Team. It is easy to create a stand-alone and production ready spring applications using Spring Boot. Spring Boot contains a comprehensive infrastructure support for developing a micro service and enables you to develop enterprise-ready applications that you can “just run”."',
        --         CURRENT_TIMESTAMP());
        {ts '2016-10-19 11:10:13.247'});
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (2, 1, 'Title 2',
        '"Spring Boot automatically configures your application based on the dependencies you have added to the project by using @EnableAutoConfiguration annotation. For example, if MySQL database is on your classpath, but you have not configured any database connection, then Spring Boot auto-configures an in-memory database. The entry point of the spring boot application is the class contains @SpringBootApplication annotation and the main method. Spring Boot automatically scans all the components included in the project by using @ComponentScan annotation."',
        --         CURRENT_TIMESTAMP());
        {ts '2016-11-10 11:10:13.247'});
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (3, 1, 'Title 3',
        '"Handling dependency management is a difficult task for big projects. Spring Boot resolves this problem by providing a set of dependencies for developers convenience. For example, if you want to use Spring and JPA for database access, it is sufficient if you include spring-boot-starter-data-jpa dependency in your project. Note that all Spring Boot starters follow the same naming pattern spring-boot-starter- *, where * indicates that it is a type of the application."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (4, 1, 'Title 4',
        '"The entry point of the Spring Boot Application is the class contains @SpringBootApplication annotation. This class should have the main method to run the Spring Boot application. @SpringBootApplication annotation includes Auto- Configuration, Component Scan, and Spring Boot Configuration."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (5, 1, 'Title 5',
        '"In Spring Boot, we can use Spring Framework to define our beans and their dependency injection. The @ComponentScan annotation is used to find beans and the corresponding injected with @Autowired annotation. If you followed the Spring Boot typical layout, no need to specify any arguments for @ComponentScan annotation. All component class files are automatically registered with Spring Beans."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (6, 1, 'Title 6',
        '"Spring Boot Actuator provides secured endpoints for monitoring and managing your Spring Boot application. By default, all actuator endpoints are secured. In this chapter, you will learn in detail about how to enable Spring Boot actuator to your application."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (7, 2, 'Title 7',
        '"One of the ways to Bootstrapping a Spring Boot application is by using Spring Initializer. To do this, you will have to visit the Spring Initializer web page www.start.spring.io and choose your Build, Spring Boot Version and platform. Also, you need to provide a Group, Artifact and required dependencies to run the application."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (8, 2, 'Title 8',
        '"Spring Boot team provides a list of dependencies to support the Spring Boot version for its every release. You do not need to provide a version for dependencies in the build configuration file. Spring Boot automatically configures the dependencies version based on the release. Remember that when you upgrade the Spring Boot version, dependencies also will upgrade automatically. Note − If you want to specify the version for dependency, you can specify it in your configuration file. However, the Spring Boot team highly recommends that it is not needed to specify the version for dependency."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (9, 2, 'Title 9',
        '"Cross-Origin Resource Sharing (CORS) is a security concept that allows restricting the resources implemented in web browsers. It prevents the JavaScript code producing or consuming the requests against different origin. For example, your web application is running on 8080 port and by using JavaScript you are trying to consuming RESTful web services from 9090 port. Under such situations, you will face the Cross-Origin Resource Sharing security issue on your web browsers."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (10, 2, 'Title 10',
        '"Eureka Server is an application that holds the information about all client-service applications. Every Micro service will register into the Eureka server and Eureka server knows all the client applications running on each port and IP address. Eureka Server is also known as Discovery Server."',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (11, 3, 'Title 11',
        '"Apache Kafka is an open source project used to publish and subscribe the messages based on the fault-tolerant messaging system. It is fast, scalable and distributed by design. If you are a beginner to Kafka, or want to gain a better understanding on it, please refer to this link − www.tutorialspoint.com/apache_kafka/"',
        CURRENT_TIMESTAMP());
INSERT INTO POST (post_id, user_id, title, body, create_date)
VALUES (12, 3, 'Title 12',
        '"Spring Boot provides an easy way to write a Unit Test for Rest Controller file. With the help of SpringJUnit4ClassRunner and MockMvc, we can create a web application context to write Unit Test for Rest Controller file. Unit Tests should be written under the src/test/java directory and classpath resources for writing a test should be placed under the src/test/resources directory."',
        CURRENT_TIMESTAMP());

-- Comments
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 1,
        '"Google Cloud Platform provides a cloud computing services that run the Spring Boot application in the cloud environment. In this chapter, we are going to see how to deploy the Spring Boot application in GCP app engine platform. First, download the Gradle build Spring Boot application from Spring Initializer page www.start.spring.io"',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 2,
        '"Hystrix is a library from Netflix. Hystrix isolates the points of access between the services, stops cascading failures across them and provides the fallback options. For example, when you are calling a 3rd party application, it takes more time to send the response. So at that time, the control goes to the fallback method and returns the custom response to your application."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 3,
        '"Spring Boot provides a very good support to create a DataSource for Database. We need not write any extra code to create a DataSource in Spring Boot. Just adding the dependencies and doing the configuration details is enough to create a DataSource and connect the Database."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (6, 1,
        '"If a Spring Boot Security dependency is added on the classpath, Spring Boot application automatically requires the Basic Authentication for all HTTP Endpoints. The Endpoint “/” and “/home” does not require any authentication. All other Endpoints require authentication. For adding a Spring Boot Security to your Spring Boot application, we need to add the Spring Boot Starter Security dependency in our build configuration file."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (6, 2,
        '"Authorization Server is a supreme architectural component for Web API Security. The Authorization Server acts a centralization authorization point that allows your apps and HTTP endpoints to identify the features of your application."',
        CURRENT_TIMESTAMP());
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (6, 3,
        '"JWT Token is a JSON Web Token, used to represent the claims secured between two parties. You can learn more about the JWT token at www.jwt.io/. Now, we are going to build an OAuth2 application that enables the use of Authorization Server, Resource Server with the help of a JWT Token. You can use the following steps to implement the Spring Boot Security with JWT token by accessing the database."',
        CURRENT_TIMESTAMP());