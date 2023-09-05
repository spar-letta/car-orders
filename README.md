Spring Boot Microservices - Car ordering rest api

How to run the application using Docker

- 
	- Clone the repository to your local workstation
        - Go inside each folder to build the applications by Running mvn clean install -DskipTests=true.
        - After that create images inside each folder by running docker build -t <name of appliaction> . .
        - Under the root folder locate docker-compose.yml file and replace images names to match the ones created in each folder
        - Then run docker-compose up to start the application

