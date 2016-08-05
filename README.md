# Walmart-Ticket-Master-App

This REST API service was built to implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance  venue.

  * This service allows a customer to know number of available seats in the venue optionally by levels.
  * The held seat will be expired if the held seat is not booked in 60 seconds.
  * Implemented the project using Maven latest version (3.3.9), SpringBoot, HSQLDB(Hyper SQL) for In-Memory Database.
  * The user can send a request by http URL to interact with the system. The system return JSON formatted response.
  * The user can also send a request by curl commands to interact with the service.
  * You need JDK 1.8 and Maven 3.3.* instlled and configured to your system.
  * The user can interact with the service by using POSTMAN application to POST the Body in JSON format and GET in JSON format
  
# Steps to build and run the project

## 1. Building the project using Maven

1. Firstly, Download this project to the desktop or otherwise it can be cloned in the GitHub Desktop.
2. Unzip the file to a location if the file is downloaded.
3. Now, open the terminal and change the directory to the directory of the project where pom.xml file is present.
4. This project already consists built jar file in target folder but if needed to build, use the following command to build the project.
        
         ``` mvn clean install ```
        
5. The test cases designed to test the project also run after the above command. If only test cases need to be run, the following command can be used to acheive it.
      
            ``` mvn test ```
            
        




