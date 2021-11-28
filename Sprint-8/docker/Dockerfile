FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
ARG PROFILE=qa
ENV PROFILE="${PROFILE}"

EXPOSE 9090

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

CMD ["--spring.profiles.active=${PROFILE}"]