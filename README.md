# HSBC Library

SpringBoot standalone Library application.
## Configuration


## Building

Use Maven to resolve project dependencies and build the project JAR.

```bash
mvn clean package
```

## Testing

The application can be run as a standalone Jar

```bash
java -jar target/HsbcBookLibrary-1.0-SNAPSHOT.jar
```


## Usage example

With the application running, navigate to http://localhost:8080/library/v2/api-docs using a tool that can format JSON

Application endpoints may be curl'd like:

```bash

curl --header "Content-Type: application/json" --request POST --data '{"firstName":"Nick","lastName":"Cooper"}' http://localhost:8080/library/addCustomer
  
curl --header "Content-Type: application/json" --request POST --data '{"firstName":"Nick","lastName":"Cooper"}' http://localhost:8080/library/getCustomer

curl --header "Content-Type: application/json" --request POST --data '{"isbn":"9876","title":"goodbye","author":"me","quantity":"1"}' http://localhost:8080/library/addBook

curl --header "Content-Type: application/json" --request POST --data '{"isbn":"9876"}' http://localhost:8080/library/getBook

curl --header "Content-Type: application/json" --request POST --data '{"isbn": "9876", "dailyFee":"2.99"}' http://localhost:8080/library/addRentalFee

curl --header "Content-Type: application/json" --request POST --data '{"isbn": "9876"}' http://localhost:8080/library/getRentalFee

curl --header "Content-Type: application/json" --request POST --data '{"isbn": "9876"}' http://localhost:8080/library/getCurrentRentalFee

curl --header "Content-Type: application/json" --request POST --data '{"customerFirstName":"Nick","customerLastName":"Cooper","isbn":"9876","numberOfDaysRental":"17"}' http://localhost:8080/library/addRental

curl --header "Content-Type: application/json" --request POST --data '{"customerFirstName":"Nick","customerLastName":"Cooper"}' http://localhost:8080/library/getRentals

```

