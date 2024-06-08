
FROM maven:3.6.3-openjdk-11 as build

WORKDIR /app

COPY pom.xml .

COPY src/ src/

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/ClusteredDataWarehouse-1.0-SNAPSHOT.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "ClusteredDataWarehouse-1.0-SNAPSHOT.jar"]
