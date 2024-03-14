# Dockerfile
FROM openjdk:8
MAINTAINER xuch
WORKDIR /root/gasnew
ADD /src/main/resources/application.yml config/
ADD /src/main/resources/application.properties config/
ADD target/gasNew-0.0.1.jar app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ENTRYPOINT ["java","-jar","app.jar"]

