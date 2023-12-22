FROM ubuntu:latest
LABEL authors="TGRANT"
ENTRYPOINT ["top", "-b"]
COPY target/BackendNew-0.0.1-SNAPSHOT.java BackendNew-0.0.1-SNAPSHOT.java
ENTRYPOINT ["java","-jar","/BackendNew-0.0.1-SNAPSHOT.java"]