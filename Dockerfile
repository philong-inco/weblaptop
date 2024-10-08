# Stage 1 - Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy toàn bộ project vào image
COPY . .

# Build project và tạo file JAR
RUN mvn clean package -DskipTests

# Stage 2 - Run
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file JAR từ stage build sang stage run
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Mở port 8080
EXPOSE 8080

# Khởi chạy ứng dụng
CMD ["java", "-jar", "demo.jar"]
