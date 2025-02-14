## Используем официальный образ Java в качестве базового
#FROM openjdk:17-jdk-slim
#
## Устанавливаем рабочую директорию
#WORKDIR /app
#
## Копируем JAR-файл приложения в контейнер
#COPY target/Weather-0.0.1-SNAPSHOT.jar app.jar
#
## Указываем команду для запуска приложения
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
## Открываем порт, на котором будет работать приложение
#EXPOSE 8080

# Используем официальный образ Java
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml
COPY pom.xml .

# Копируем mvnw и mvnw.cmd в контейнер
COPY mvnw .
COPY mvnw.cmd .

# Копируем каталог .mvn для Maven Wrapper
COPY .mvn .mvn

# Устанавливаем права на выполнение для mvnw
RUN chmod +x mvnw

# Копируем src для сборки
COPY src ./src

# Сборка приложения
RUN ./mvnw package -DskipTests

# Копируем собранный jar файл
COPY target/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
