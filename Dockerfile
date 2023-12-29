### Use to build: docker build -t tg:v1 .
### Use to run: docker run -p9090:9090 tg:v1

#Type
FROM openjdk:21

#Label
LABEL authors="TGRANT"

#Exposes port
EXPOSE 9090
EXPOSE 5432

#Creates directory
RUN mkdir -p /app

#Sets working directory
WORKDIR /app

#Copies app to container in /app
COPY target/BackendNew-0.0.1-SNAPSHOT.jar /app

#Starts app from location when container is run
ENTRYPOINT ["java","-jar","BackendNew-0.0.1-SNAPSHOT.jar"]
