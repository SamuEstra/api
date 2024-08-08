# Usa una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo de build/libs al contenedor
COPY build/libs/tesina_app-0.0.1-SNAPSHOT.jar /app/tesina-app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Define las variables de entorno

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/tesina-app.jar"]