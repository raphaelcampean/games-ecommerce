# Estágio de Build (usando Maven e Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
# O nome do JAR deve bater com o artifactId do seu pom.xml
COPY --from=build /target/store-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]