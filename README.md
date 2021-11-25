# README #

This is the bff microservice of porqueeuprogramo restaurant (java spring maven) application.


### How do I get repository ###

* [Repository](https://github.com/porqueeuprogramo/pep-restaurant-ms-bff)

### How do I get set up? ###

* Install Java 8 or later version
* Add Dependencies already presents in pom
* Configure database (docker compose config)
* Clean, install and run app

### How do I set up docker? ###

* change localhost on application yml to your local ip
* mvn clean install

* docker build -t pep-restaurant-ms-bff.jar .
* docker run -p 8080:8080 --name pep-restaurant-ms-bff pep-restaurant-ms-bff.jar
or
* docker-compose up (docker-compose.env has already the env variables values)

### How do I check code quality
* Run the following cmd on sonar:
* mvn sonar:sonar -Dsonar.projectKey=PROJECT_KEY -Dsonar.host.url=http://localhost:9000 -Dsonar.login=TOKEN

### How do I check swagger ui
* Open the following web url: http://localhost:8080/swagger-ui

### Who do I talk to? ###

* discord https://discord.gg/v2MgpUT4QC
* email porqueeuprogramo@gmail.com
