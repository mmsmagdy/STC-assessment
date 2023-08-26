FROM openjdk:11-jre-slim

COPY target/stc-assessment-*.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]