FROM amazoncorretto:21

WORKDIR /app

COPY ./target/awsdemo-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "awsdemo-0.0.1-SNAPSHOT.jar"]