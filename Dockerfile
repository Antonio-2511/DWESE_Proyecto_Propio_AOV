# =========================
# STAGE 1 - BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos todo
COPY . .

# Construimos el jar
RUN mvn clean package -DskipTests

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]