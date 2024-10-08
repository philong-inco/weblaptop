# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn clean package -DskipTests --no-transfer-progress

# Kiểm tra nội dung thư mục target
RUN ls /app/target

# Run stage
FROM openjdk:17-slim
WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]
