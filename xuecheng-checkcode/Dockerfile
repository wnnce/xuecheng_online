FROM openjdk:17

MAINTAINER CrushCola docker@email.com

WORKDIR /root

ADD ./target/xuecheng-checkcode-1.0-SNAPSHOT.jar xuecheng-checkcode.jar

CMD ["java", "-version"]

ENTRYPOINT ["java", "-jar", "xuecheng-checkcode.jar"]

EXPOSE 8075