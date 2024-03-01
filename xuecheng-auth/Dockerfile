FROM openjdk:17

MAINTAINER CrushCola docker@email.com

WORKDIR /root

ADD ./target/xuecheng-auth-1.0-SNAPSHOT.jar xuecheng-auth.jar

CMD ["java", "-version"]

ENTRYPOINT ["java", "-jar", "xuecheng-auth.jar"]

EXPOSE 8074