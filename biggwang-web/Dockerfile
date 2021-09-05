FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/biggwang-web.jar
COPY ${JAR_FILE} biggwang-web.jar
ENV	USE_PROFILE dev
ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}","-jar", "biggwang-web.jar"]