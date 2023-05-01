# Project Management
This application allows you to manage a project and its tasks. 

## Building the Project

To build the project, you need to follow these steps:

1. Clone the repository using Git: `git clone https://github.com/Flockk/kanban-board.git`
2. Navigate to the project directory: `cd <project_name>`
3. Install dependencies: `mvn install`
4. Build the application: `mvn package`

## Running the Application

To run the application, you need to follow these steps:

1. Create a PostgreSQL database using the psql utility or any other convenient way.
2. Configure the connection to the database in the application.properties file. To do this, edit the application.properties file as follows:


```properties
# Database connection URL
spring.datasource.url=jdbc:postgresql://localhost:5432/<your_database>
# Database username
spring.datasource.username=<your_username>
# Database password
spring.datasource.password=<your_password>
```
