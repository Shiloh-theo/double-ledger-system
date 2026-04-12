# Step 1: Build the app
FROM maven:3.9.9-eclipse-temurin-22 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the app
FROM eclipse-temurin:22-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 10000

# PORT is injected by Render at runtime. Falls back to 10000 locally.
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-10000}"]
