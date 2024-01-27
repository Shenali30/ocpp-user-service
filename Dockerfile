FROM arm64v8/eclipse-temurin:latest AS builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} userservice-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar userservice-0.0.1-SNAPSHOT.jar extract

FROM arm64v8/eclipse-temurin:latest
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
COPY src/main/resources/application-dev.yml /application/BOOT-INF/classes/application.yml
WORKDIR /home/app-user
WORKDIR /
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "org.springframework.boot.loader.JarLauncher"]