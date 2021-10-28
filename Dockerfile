FROM openjdk:11
ADD target/pep-restaurant-ms-bff.jar pep-restaurant-ms-bff.jar
ENTRYPOINT ["java", "-jar", "pep-restaurant-ms-bff.jar"]
EXPOSE 8080
