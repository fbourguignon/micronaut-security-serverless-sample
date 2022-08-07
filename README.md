# micronaut-security-serverless-sample
Sample serverless application using Micronaut (Security, Data, AWS), API Gateway and GraalVM

- [x] [Micronaut 3.5.3](https://micronaut.io/)
- [x] [SAM](https://aws.amazon.com/pt/serverless/sam/)
- [x] [Cockroach](https://www.cockroachlabs.com/)
- [x] [TestContainers](https://www.testcontainers.org/modules/databases/jdbc/)
- [x] Java 11


### Build for JVM execution
```console
micronaut-security-serverless-sample % make build
```

### Build for GraalVM execution
```console
micronaut-security-serverless-sample % make build-graalvm
```

### Start Application
This command starts the docker containers, sam local api and build the project.
```console
micronaut-security-serverless-sample % make run
```

-----

## Local Execution

### Create user
```json
POST /users/register HTTP/1.1
Host: localhost:3000
Content-Type: application/json
        
{
"email": "user@gmail.com",
"password": "12345678"
}
```
### Authenticate user
```json
POST /login HTTP/1.1
Host: localhost:3000
Content-Type: application/json
        
{
"username": "user@gmail.com",
"password": "12345678"
}
```

### Cockroach client

```console
http://localhost:8080
```
![picture](img/cockroach.png)


