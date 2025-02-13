# OVC minimal backend

The minimal backend for the ovc-videotest that can generate Jitsi JWT tokens

Tech:

* java 17+
* `spring-boot`
* `maven`


# Running the application

Java 17 is expected

* In the base source direcctory that  will be called `${project.basedir}`
* Build `./mvnw clean package`. This will produce `target/ovc-minimal-backend-0.0.1-SNAPSHOT.jar`
* Run with `java -jar target/ovc-minimal-backend-0.0.1-SNAPSHOT.jar`

OR Maven

* Run in the `${project.basedir}` the Maven command `./mvnw spring-boot:run`. This will build and run automatically.

# Configuration

 `${project.basedir}/src/main/resources/application.properties` contains the default values.

The app can be configured by placing a custom  `application.properties` into `${project.basedir}/config/`.

Refer to https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html
for the spring boot priorities when loading external config files.

Important properties

```properties
server.port=8080

jwt.secret=SECRET_SECRET_SECRET_SECRET_SECRET
jwt.issuer=ovc
jwt.audience=ovc
jwt.subject=*
jwt.expirationMinutes=60
jwt.defaultUserName=User

room.prefix=videotest
```

Configurations can be set  from  environment variables, example docker-compose

```yaml
version: "3.7"
services:
  backend:
    build: .
    environment:
      - jwt.secret=${JWT_APP_SECRET}
      - jwt.videotest.secret=${JWT_VIDEOTEST_SECRET}
    ports:
      - "8080:8080"
```

Environment variables will have priority over config files.


## Build

Refer to the README.md

## Docker

After funning will start a `tomcat` server on port `8080`





