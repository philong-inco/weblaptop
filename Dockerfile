# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Copy toàn bộ project và build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file WAR từ build stage sang run stage
COPY --from=build /app/target/DrComputer-0.0.1-SNAPSHOT.war app.war
EXPOSE 8080

# Khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.war"]
