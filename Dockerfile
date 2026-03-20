FROM eclipse-temurin:21-jdk
LABEL authors="Igor Pribanic"
COPY target/TreciParcijalniTest-0.0.1-SNAPSHOT.jar treci-parcijalni-test.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/treci-parcijalni-test.jar"]