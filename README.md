# Wirecard System of Payment
This project is an exemple, about the service of payment of the Wirecard, for an test, developed with Spring Boot, JPA (Hibernate), H2, Maven.

## Run your project
For Started the project, is necessary, to do download the repository locally and to import with maven in your IDE.
######  Run the class WirecardPaymentsApplication.

## The architecture and the design adopted to solve the challenges.
The project was constructed in the model Rest with Spring, JPA (Hibernate) and H2 (DB memory), following the priciples of clean architecture, without border contracts (Gateway, Governance, etc).

####  1: Register a new Client: POST (http://localhost:8000/client)
##### Json of input in the database H2, as example.
{
    "name": "Wirecard"
}

####  2: Later, you have register a new Buyer: POST (http://localhost:8000/buyer)
##### Json of input in the database H2, as example.
{
    "email": "teste@gmail.com",
    "cpf": "12345678911",
    "nome": "Teste"
}

####  3: So, you create a new Payment with CREDIT_CARD option: POST (http://localhost:8000/payment)
##### Json of input in the database H2, as example.
{
  "amount": "123456.00",
  "paymentMethod":{
    "method": "CREDIT_CARD",
    "creditCard":{
      "brand": "Elo",
      "holder":{
        "name": "Andrei",
        "birthDate": "01-01-1991",
        "documentNumber": "98765432199"
      },
      "cardNumber": "1234567891234567",
      "expirationDate": "20-02-2020",
      "cvv": "890"
    }
  },
  "buyer":{
    "id": "1"
  },
  "client":{
    "id": "1"
  }
}

####  4: With that you create a new Payment with BILLET option: POST (http://localhost:8000/payment)
##### Json of input in the database H2, as example.
{
  "amount": "123456.00",
  "paymentMethod":{
    "method": "BILLET"
  },
  "buyer":{
    "id": "1"
  },
  "client":{
    "id": "1"
  }
}

####  5: To get the information of a buyers by its id: GET (http://localhost:8080/v1/buyer/list) or (http://localhost:8080/v1/buyer/list/{id})

####  6: To get the information of a clients by its id: GET (http://localhost:8080/v1/client/list) or (http://localhost:8080/v1/client/list/{id})

####  7: To get the information of a credit-cards by its id: GET (http://localhost:8080/v1/credit-card/list) or (http://localhost:8000/buyer/{id})

####  8: To get the information of a payments by its id: GET (http://localhost:8080/v1/payment/list) or (http://localhost:8080/v1/payment/list/{id})

## Attachments
####### Collections of calls to Postman.
