# Use uma imagem base do Gradle com JDK
FROM gradle:7.6-jdk17 AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos de build e dependências primeiro (para cache)
COPY build.gradle settings.gradle /app/

# Baixa as dependências, o que é útil para cache
RUN gradle build --no-daemon --gradle-user-home /home/gradle/.gradle || return 0

# Agora copia o restante dos arquivos
COPY . .

# Executa o build do projeto
RUN gradle build --no-daemon --gradle-user-home /home/gradle/.gradle

# Usando uma imagem menor para rodar o aplicativo
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expondo a porta que o Spring Boot usa por padrão
EXPOSE 8080
