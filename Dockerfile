FROM adoptopenjdk:8-openj9

MAINTAINER   1419611318@qq.com

ENV LANG en_US.UTF-8

RUN mkdir -p /x-admin-template-4-start

WORKDIR /x-admin-template-4-start

EXPOSE 9999

ADD ./target/x-admin-template-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#CMD ["--spring.profiles.active=test"]
