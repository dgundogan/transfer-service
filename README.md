# 'Transfer Service' Rest Service

`transfer-service` is a REST microservice application.
The service API should accept as input source account number, destination account number and the amount.
It returns the appropriate response.

## Implementation

Following tech spec is used for the TDD based implementation.

- *Java*
- *Spring Boot*
- *maven*
- *JUnit*
- *h2*

The project is organized as a *maven* project and in order to compile, test and create a package *maven* is used.

### Building the application

You could use maven to test and build the jar file.

* In the root directory of the project run the following commands

```bash
# Compile
mvn -B clean compile

#Test
mvn -B clean test


#Create the package
mvn -B clean package

```

## Using the API

### Running the application

* Start the application with the following command:

```bash

#Under the root folder of the project

java -jar target/transfer-service-1.0-SNAPSHOT.jar

```


### Request

The endpoint of the application as given in the following table.


|End Point                                                 | Operation    |Port  |
|----------------------------------------------------------|--------------|------|
|http://localhost:8080/accounts/{accountId}/transfer       |POST          | 8080 |

Sample post url : http://localhost:8080/accounts/000-111-222/transfer

* Sample Transaction Request
```json

{
  "toAccountNumber" : "000-117-222",
  "amount" : 50.40
}

```

## Database

TABLE NAME : ACCOUNT

 |Column Name      | Type                | Not Null |
 |-----------------|---------------------|----------|
 |ACCOUNT_NUMBER   | STRING              | Y        |
 |BALANCE          | DOUBLE              | Y        |

TABLE NAME : ACCOUNT_TRANSACTION

 |Column Name                  | Type                | Not Null |
 |-----------------------------|---------------------|----------|
 |ID                           | BIGINT              | Y        |
 |ACCOUNT_NUMBER               | STRING              | Y        |
 |DEST_ACC_NUMBER              | STRING              | Y        |
 |AMOUNT                       | DOUBLE              | Y        |