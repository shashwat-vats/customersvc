Here is a `README.md` template for your REST API which performs CRUD operations on customer details, using Java 17, Spring Boot 3.4, and persisting data in an H2 in-memory database.


# Customer SVC API

This is a simple REST API built with **Java 17** and **Spring Boot 3.4** to perform CRUD operations on customer details. The application uses **H2 in-memory database** for data persistence.

## Features

- **Create**: Add new customer details.
- **Read**: Retrieve customer details by ID .
- **Update**: Modify existing customer information.
- **Delete**: Remove customer records by ID.

### API Endpoints

The application exposes the following RESTful API endpoints for interacting with customer details:

1. **Create a New Customer**
   - **Endpoint**: `POST /customer/add`
   - **Request Body**:
     ```json
     {
       "firstName": "John",
       "lastName": "Doe",
       "dateOfBirth": "1990-01-01"
     }
     ```
   - **Response**: Returns the created customer object with an HTTP status of 200.
     ```json
     {
        "customerId": 152,
        "message": "Created Successfully"
   }
     ```
    

2. **Get Customer by ID**
   - **Endpoint**: `GET /customer/{customerId}`
   - **Response**: Returns the customer details for the specified `customerId`.
     ```json
     {
       "customerId": 1,
       "firstName": "John",
       "lastName": "Doe",
       "dateOfBirth": "1990-01-01"
     }
     ```
   - **Error Response**: If no customer is found, 
    ```json
    {
     "message": "Requested customerId is not in database",
     "status": 404,
     "timeStamp": "2024-11-28T22:56:59.3983474"
     }
    ```
3. **Update Customer**
   - **Endpoint**: `PUT /customer/{customerId}`
   - **Request Body**:
     ```json
     {
       "firstName": "Johnathan",
       "lastName": "Doe",
       "dateOfBirth": "1990-01-01"
     }
     ```
   - **Response**: Returns the updated customer object.
     ```json
     {
       "customerId": 1,
       "firstName": "Johnathan",
       "lastName": "Doe",
       "dateOfBirth": "1990-01-01"
     }
     ```

4. **Delete Customer**
   - **Endpoint**: `DELETE /customer/{customerId}`
   - **Response**: Returns HTTP status `204 No Content` upon successful deletion of the customer.

---


## Technologies Used

- **Java 17**: The application is built with Java 17.
- **Spring Boot 3.4**: Spring Boot framework to handle the web and REST functionality.
- **H2 Database**: H2 in-memory database for data persistence during testing or development.
- **Maven**: For managing project dependencies.

## Setup and Installation

### Prerequisites

1. **Java 17**: Ensure that you have JDK 17 installed on your machine.
2. **Maven**: Ensure that Maven is installed to build and run the project.

### Steps to Run the Application

1. **Import the Project**:

2. **Build the Project**:
   To build the project, run the following Maven command:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   To run the application, execute the following command:
   ```bash
   mvn spring-boot:run
   ```

   The API will be available at `http://localhost:8080`.

4. **Accessing H2 Console (Optional)**:
   If you want to interact with the H2 in-memory database, you can access the H2 console by navigating to:
   ```
   http://localhost:8080/h2-console
   ```
   Use the following JDBC URL:
   ```
   jdbc:h2:mem:testdb
   ```

   Username: `any`  
   Password: `any`

### Configuration

- The application configuration (such as database settings) can be found in the `src/main/resources/application.properties` file.
- By default, Spring Boot auto-configures H2 for in-memory use.

---

## Example API Requests

### 1. **Create Customer**

```bash
curl --location 'http://localhost:8080/customer/add' \
--header 'Content-Type: application/json' \
--data '{
    "firstName":john ,
    "lastName":"cena",
    "dateOfBirth": "1996-08-01"
}'
```

### 2. **Get Customer by ID**

```bash
curl --location 'http://localhost:8080/customer/fetch/1' \
--header 'Accept: application/json'
```

### 3. **Update Customer**

```bash
curl --location --request PUT 'http://localhost:8080/customer/update/1' \
--header 'Content-Type: application/json' \
--data '{
    "firstName":"Jon" ,
    "lastName":"Cena",
    "dateOfBirth": "1996-08-24"
}'
```

### 5. **Delete Customer**

```bash
curl --location --request DELETE 'http://localhost:8080/customer/remove/1'
```

---

## Testing

You can run unit tests using Maven:

```bash
mvn test
```

This will run the tests and validate the API functionality.

---