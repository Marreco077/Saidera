# Etapa de build (usando imagem com Maven)
FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /app

# Copiar o conteúdo da pasta saidera (incluindo o pom.xml) para o contêiner
COPY saidera/ .

# Construir o projeto com Maven
RUN mvn clean package -DskipTests

# Etapa final (runtime)
FROM openjdk:17-jdk-slim

WORKDIR /saidera

# Copiar o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
