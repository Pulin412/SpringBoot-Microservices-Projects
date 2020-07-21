FROM openjdk:8u151-jdk-alpine3.7

EXPOSE 8090

ENV APP_HOME /usr/src/app

COPY target/myblog-0.0.1-SNAPSHOT.jar $APP_HOME/myblog.jar

WORKDIR $APP_HOME

ENTRYPOINT exec java -jar myblog.jar