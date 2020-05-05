# Grofers App

Developed an end to end **Grofers web Application using Spring MVC** with multiple modules

### Functionalities:

1. User Registeration

2. **CRUD Operations** like

* User can add the address
* Admin can get all saved address
* Admin can delete the saved address

3. **Spring Security**

* User can login the site
* The entire site will change according the role. Whether the client is User or Admin
* user can logout after completing.

4. **Spring WebFlow**

* after adding stores the User can checkout using spring WebFlow
* Confirming User Details
* Confirming Shipping and Billing Address
* Receipt
* If the user cancel the webflow it will go to cancel Page
* If the user submits the checkout it will go to thank you page with the timing of delivery Report

### Tools and Technologies:

* **Technology** : Java, Spring MVC, Hibernate, Maven.
* **Application Servicer**: Apache Maven
* **Database** : PostgreSQL.

### Installation:

1. Development Platform - Eclipse / IntelliJ Idea
   * [Download Eclipse](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/mars2)
   * [Download IntelliJ Idea](https://www.jetbrains.com/idea/download/#section=windows)
2. Server - Apache Maven

   * [Download Apache Server](https://tomcat.apache.org/download-70.cgi)

3. Build Tool - Maven

   * [Download Maven](https://maven.apache.org/download.cgi)

4. Database - 

   * [Download PostgreSQL](https://www.postgresql.org/download/)


6. Installation of maven in eclipse - [Click Here](https://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation)

7. Clone the repository and import it to eclipse

8. Run your PostgreSQL Database.

9. Configure your databse configuration in **application-context.xml**

   * Database properties:


        <!-- database properties DataSource -->

            <bean id="dataSource"
              class="org.springframework.jdbc.datasource.DriverManagerDataSource">
              <property name="driverClassName" value=YOUR DB DRIVER CLASS NAME" />
              <property name="url" value="YOUR DB URL" />
              <property name="username" value="YOUR DB USERNAME" />
              <property name="password" value="YOUR DB PASSWORD" />
            </bean>


10. Run the server.

#### Somethings wrong!!

If you find that something's wrong with this package, you can let me know by raising an issue on the GitHub issue tracker, 
or take it as a task and 🧑‍💻 resolve it 💪 --> create a PullRequest 🛠.

#### Contribution

Contributors are most welcome.

### ScreenShots:

<h2>Database schema</h2>

<img src="/images/DB-Schema.png">