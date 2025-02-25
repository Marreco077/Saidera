# Etapa de build com OpenJDK 17
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copiar os arquivos do projeto para dentro do contêiner
COPY . .

# Construir o projeto usando Maven
RUN ./mvnw clean package -DskipTests

# Etapa final (runtime) com OpenJDK 17
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
