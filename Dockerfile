FROM amazoncorretto:21

COPY target/cambio-0.0.1-SNAPSHOT.jar /app/cambio-0.0.1-SNAPSHOT.jar

EXPOSE 8000

CMD ["java", "-jar", "/app/cambio-0.0.1-SNAPSHOT.jar"]