FROM openjdk:8-jre-slim-stretch

ENV BASE_DIR /opt
ENV SPRING_PROFILES_ACTIVE default
ENV PORT 8080

ARG APP

WORKDIR $BASE_DIR

COPY build/libs/$APP.jar .

RUN ln -s $APP.jar app

CMD ["/bin/sh", "-c", "java -Duser.timezone=Europe/Warsaw -Xmx512m -jar /opt/app --spring.profiles.active=$SPRING_PROFILES_ACTIVE --server.port=$PORT"]
