# eir-code-service

This is one of the main component in postcoder where all the business logic resides for Irish address lookup for the URL https://developers.alliescomputing.com/postcoder-web-api/address-lookup/eircode.
Eir-code-service is based on Spring boot which runs on a separate docker container and it is one of the client of eureka server. It fetches address details from third-party API's and caches it in the redis server. When second query gets, it does not take it from API's instead retrieves it from the redis cache.
For more information https://github.com/manojbest/eir-code-service

### Installation

```sh
$ cd eir-code-service
$ mvn package
```

Run application form http://localhost:8081

Get Swagger documentation from http://localhost:8081/docs
