# Walmart-Ticket-Master-App

This REST API service was built to implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance  venue.

  * Implemented the project using Maven latest version (3.3.9), SpringBoot, HSQLDB(Hyper SQL) for In-Memory Database.
  * The user can send a request by http URL to interact with the system. The system return JSON formatted response.
  * The user can also send a request by curl commands to interact with the service.
  * You need JDK 1.8 and Maven 3.3.* instlled and configured to your system.
  * The user can interact with the service by using POSTMAN application to POST the Body in JSON format and GET in JSON format.
  * This service allows a customer to know number of available seats in the venue optionally by levels.
  * This service allows a customer to hold a number of seats in the venue optionally by min level and max level.
  * The held seat will be expired if the held seat is not booked in 60 seconds.
  * This service allows a customer to completely reserve specifc held seats which is checked by hold Id and customer Email. 
  
# Steps to build and run the project

## 1. Building the project using Maven

1. Firstly, Download this project to the desktop or otherwise it can be cloned in the GitHub Desktop.
2. Unzip the file to a location if the file is downloaded.
3. Now, open the terminal and change the directory to the directory of the project where pom.xml file is present.
4. This project already consists built jar file in target folder but if needed to build, use the following command to build the project.
        
          mvn clean install 
        
5. The test cases designed to test the project also run after using the above command. If only test cases are needed to be run, the following command can be used to acheive it.
      
             mvn test 
            
## 2. Running the Application

1. Once the jar is created in the target folder of the project directory, then change the directory to the target folder from the terminal.

2. Now here to run the Spring Boot Application, execute the following command.

             java -jar ticket-master-0.0.1-SNAPSHOT.jar 

3. This command will now start the application on the In built Apache Tomcat Server on localhost 8080.

## 3. Using the Application

### 1. Find number of seats available in the venue optionally by level Id.

1. The total number of seats in the venue available after the holds and the bookings can be known by using the following HTTP URL.

    Http GET request can be done using the POSTMAN application or a normal browser.
    
          GET, http://localhost:8080/api/v1/seats  
       or
                         
    Http Request can also be sent using curl commands.
    
         curl http://localhost:8080/api/v1/seats 
        
    The response to this request should return the total number of seats available in the venue.
        
            6250
            
2. The total number of seats in the venue available after the holds and the bookings in specified by level.     
   Http GET request
        
               GET, http://localhost:8080/api/v1/seats/{level} 
   or
   
   Curl Command
   
                curl http://localhost:8080/api/v1/seats/{level} 
    
    {level} = 1 , 2, 3 , 4
    
### 2. Find and Hold a specific number of seats optionally by level.

1. Hold Seats in the venue irrespective to the venue level. The default level starts from level 1.
                
   URL
   
            POST , http://localhost:8080/api/v1/hold/
                
   JSON format Request body
   
           {
            "numSeats"  : 1200,
            "custEmail": "mc5410@nyu.edu"
            }
            
   Response for the above URL in JSON format.
   
        {
            "dbId": 1,
            "levelId": 1,
            "seats": 1200,
            "modified": 1470360838631,
            "emailAddress": "mc5410@nyu.edu"
        }
        
2. Hold Seats in the venue by providing the minimum level and maximum level to hold the seats.

    URL 
    
            POST , http://localhost:8080/api/v1/hold/
            
    JSON format Request body
   
           {
            "numSeats"  : 1200,
            "custEmail": "mc5410@nyu.edu",
            "minLevel" : 2,
            "maxLevel" : 4
            }
            
   Response for the above URL in JSON format.
   
        {
            "dbId": 1,
            "levelId": 2,
            "seats": 1200,
            "modified": 1470360838631,
            "emailAddress": "mc5410@nyu.edu"
        }  
        
3. Reserve a specfic held seat by provding the dbId and customer email address.

    URL 
          
            POST, http://localhost:8080/api/v1/reserve/

    JSON format Request body
        
            {
                "seatHoldId"  : 1,
                "customerEmail": "mc5410@nyu.edu"
             }
             
     Response for the above URL in JSON format.
     
             {
                "id": "402881465658184e0156585e091c0000",
                "holdId": 1,
                "levelId": 1,
                "custemail": "mc5410@nyu.edu",
                "numberOfSeats": 1200
              }

# Assumptions