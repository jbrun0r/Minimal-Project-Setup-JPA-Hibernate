# Object-Relational Mapping with JPA/Hibernate

This project is intended for studying and configuring object-relational mapping with JPA/Hibernate. The goal is to demonstrate how to perform mapping between Java objects and MySQL database tables using the Java Persistence API (JPA) in conjunction with the Hibernate framework.

## Overview of Object-Relational Mapping

Object-relational mapping is a technique used to connect the object-oriented world of the Java programming language with a relational database, where data is stored in tables. This connection allows Java entities to be persistent, meaning their states can be maintained and retrieved from the database.

In addition to mapping itself, other issues are also addressed in the context of persistence, such as the persistence context (objects that are or are not tied to a connection at a given moment), identity map (cache of already loaded objects), and lazy loading, which are important to ensure data integrity and improve performance of operations.

## JPA (Java Persistence API)

The Java Persistence API (JPA) is the standard specification of the Java EE platform for object-relational mapping and data persistence. It defines a set of interfaces and annotations used to map Java entities to database tables.

JPA is just a specification and requires a concrete implementation to work correctly. In this project, we will use Hibernate as the JPA implementation.

### Application Architecture with JPA

An application using JPA has some key classes:

1. **EntityManager:** An EntityManager object encapsulates a connection to the database and allows performing data access operations (insertion, removal, deletion, updating) on entities monitored by it in a persistence context.

2. **EntityManagerFactory:** It is responsible for instantiating EntityManager objects.

## Hibernate

Hibernate is a popular open-source Object-Relational Mapping (ORM) framework that implements the JPA specification. It simplifies the process of mapping Java objects to database tables and provides powerful features for managing database operations.

Key Features of Hibernate:

- **Automatic SQL Generation:** Hibernate automatically generates SQL statements for CRUD (Create, Read, Update, Delete) operations based on the defined JPA annotations in the entity classes.

- **Lazy Loading:** Hibernate supports lazy loading, where related entities are loaded only when they are accessed, reducing unnecessary database queries and improving performance.

- **Caching:** Hibernate provides caching mechanisms that help optimize data retrieval and improve application performance by storing frequently accessed data in memory.

- **Transaction Management:** Hibernate manages transactions to ensure data consistency and integrity during database operations.

- **Query Language:** Hibernate Query Language (HQL) allows writing database queries using object-oriented syntax, which is independent of the database-specific SQL.

- **Integration with JPA:** As an implementation of JPA, Hibernate seamlessly integrates with the Java Persistence API, allowing developers to use JPA's standardized features and annotations.

## Creating a Simple Application

In this project, we will create an application that instantiates three objects of the "Pessoa" (Person) class and displays their data on the screen.

Steps to create the application:

1. Create the "Person" class in the "domain" (domain) package and perform the mappings:

```java
package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;

    // ... (getters, setters, and constructors)
}
```

2. Add Hibernate as a dependency in the project's "pom.xml" file and update Maven:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.joao</groupId>
	<artifactId>JPA-maven</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<properties>
		<maven.compiler.source>11</maven.compiler.source>
		<maven.compiler.target>11</maven.compiler.target>
	</properties>
	<dependencies>

		<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>5.4.12.Final</version>
		</dependency>

		<!--
		https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>5.4.12.Final</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.19</version>
		</dependency>

	</dependencies>
</project>
```

For this project, we have included the Hibernate core and entity manager dependencies in the `pom.xml` file to utilize the Hibernate ORM capabilities with JPA for handling object-relational mapping and data persistence.

3. Configure JPA through the "persistence.xml" file:

- ###### Create a folder named "META-INF" inside the "resources" folder.

- ###### Inside the "META-INF" folder, create a file named "persistence.xml" with the following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
	version="2.1">
	<persistence-unit name="person-jpa" transaction-type="RESOURCE_LOCAL">
		<properties>
			<property name="javax.persistence.jdbc.url"
				value="jdbc:mysql://localhost/jpa?useSSL=FALSE&amp;serverTimezone=UTC" />

			<property name="javax.persistence.jdbc.driver"
				value="com.mysql.jdbc.Driver" />

			<property name="javax.persistence.jdbc.user" value="root" />
			<property name="javax.persistence.jdbc.password" value="" />
			<property name="hibernate.hbm2ddl.auto" value="update" />

			<property name="hibernate.dialect"
				value="org.hibernate.dialect.MySQL8Dialect" />

		</properties>
	</persistence-unit>
</persistence>
```

4. Create a Main class in the "application" package to perform the CRUD operations

```java
package application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Person;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("person-jpa");
		EntityManager em = emf.createEntityManager();

//		write to the database
		Person person = new Person(null, "João Bruno", "joao@gmail.com");
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();

//		retrieve from database
		Person dbPerson = em.find(Person.class, 1);
		System.out.println(dbPerson);

//		remove from database
		em.getTransaction().begin();
		em.remove(dbPerson);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

}

```

# Starting Apache and MySQL Servers

To run the JPA/Hibernate project and perform the CRUD operations successfully, you need to ensure that the Apache server and MySQL are properly started. Follow the steps below to start the servers using XAMPP:

1. Install XAMPP on your computer if you haven't already. You can download XAMPP from the official website: [https://www.apachefriends.org/index.html](https://www.apachefriends.org/index.html)

2. After installing XAMPP, run the XAMPP Control Panel.

3. Start both Apache and MySQL services by clicking on the "Start" button next to each of them.

4. Once the Apache and MySQL servers are running, you can proceed with running your JPA/Hibernate project and performing the CRUD operations.

With the Apache and MySQL servers running, your JPA/Hibernate project will be able to connect to the MySQL database as specified in the `persistence.xml` file. This will allow you to execute the CRUD operations successfully.

Remember to shut down the Apache and MySQL servers properly when you are done with your JPA/Hibernate project by clicking on the "Stop" buttons in the XAMPP Control Panel.

If you encounter any issues with the server connections or the JPA/Hibernate setup, make sure to check your database configuration and ensure that the correct database URL, username, and password are provided in the `persistence.xml` file. Also, check that the required dependencies and libraries are correctly included in the Maven `pom.xml` file.
