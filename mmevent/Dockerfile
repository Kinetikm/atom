FROM jetty:9.4.5-alpine
MAINTAINER Ilya Lysko
ADD build/libs/frontend-1.0-SNAPSHOT.war /var/lib/jetty/webapps/ROOT.war

COPY build/libs/mmevent-1.0-SNAPSHOT.jar /services/mmevent.jar

#EXPOSE will not open port to outer world, just inside cloud
EXPOSE 8282
CMD ["java", "-jar", "/services/mmevent.jar"]