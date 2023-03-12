FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/transaction-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]