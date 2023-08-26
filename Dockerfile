FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/file-upload-download.jar .

EXPOSE 8080

CMD ["java", "-jar", "file-upload-download.jar"]