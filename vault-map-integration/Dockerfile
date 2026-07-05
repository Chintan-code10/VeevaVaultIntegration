# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven wrapper (if present)
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw || true

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline || mvn dependency:go-offline

# Copy source
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests


# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]