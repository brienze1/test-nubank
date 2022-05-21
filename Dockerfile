# Download prebuilt maven with Java 11 image
FROM maven:3.8.5-jdk-11-slim as build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# copy files into docker container
ADD pom.xml $HOME
ADD src $HOME/src

# create the application jar
RUN --mount=type=cache,target=/root/.m2 mvn clean install

# Download prebuilt Java 11 jdk image
FROM openjdk:11

# coppying the application JAR
COPY --from=build /usr/app/target/nubank-challenge.jar /app/app.jar

# running the application
CMD ["java", "-jar", "/app/app.jar"]