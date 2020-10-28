FROM openjdk:11-jdk-slim

WORKDIR /app

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 9998

ENTRYPOINT ["java","-Djava.securityegd=file:/dev/./urandom", "-Dspring.profiles.active=${ENV}", "-cp","/app:/app/lib/*","mavier.food.menu.FoodGeneratorApplication"]