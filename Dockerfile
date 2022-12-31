FROM gradle:7.5.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17-jdk-slim-buster
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/biblioteca-infantil-all.jar /app/biblioteca-infantil.jar
ENTRYPOINT ["java","-jar","/app/biblioteca-infantil.jar"]
#CMD [ "tail", "-f" , "/dev/null" ]
