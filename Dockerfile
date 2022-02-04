FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=deploy","/app.jar"]