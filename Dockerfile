FROM amazoncorretto:17.0.7-alpine
COPY build/libs/plotting-0.0.1-SNAPSHOT.jar plotting.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "plotting.jar"]