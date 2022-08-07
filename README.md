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

