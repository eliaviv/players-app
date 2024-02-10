FROM openjdk:18.0.2.1-slim

LABEL maintainer="eli.aviv6@gmail.com"

RUN apt-get update

RUN apt-get install -y curl procps net-tools vim iputils-ping telnet util-linux

RUN mkdir /application

COPY ./target/players-app-0.0.1-SNAPSHOT.jar /application/app.jar

WORKDIR /application

CMD ["java", "-jar", "app.jar"]