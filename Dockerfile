# Download prebuilt Java 11 jdk image
FROM openjdk:11

# coppying the application JAR
COPY target/nubank-challenge.jar /app.jar

# running the application
CMD ["java", "-jar", "/app.jar"]