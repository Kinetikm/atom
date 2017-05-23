FROM openjdk:8-jre-alpine
MAINTAINER Ilya Lysko
COPY webapp.build/libs/auth-1.0-SNAPSHOT.jar /services/auth.jar
#EXPOSE will not open port to outer world, just inside cloud

EXPOSE 8080
CMD ["java", "-jar", "/services/auth.jar"]