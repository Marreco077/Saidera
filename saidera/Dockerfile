# Etapa de build (usando imagem com Maven)
FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /app

# Copiar todos os arquivos do projeto para dentro do contêiner
COPY . /app

# Construir o projeto com Maven
RUN mvn clean package -DskipTests

# Etapa final (runtime)
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /saidera

# Copiar o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
