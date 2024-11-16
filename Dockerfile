FROM openjdk:20-jdk-slim
WORKDIR /app
COPY target/WebApplicationBookstore.jar /app/bookstore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bookstore.jar"]
