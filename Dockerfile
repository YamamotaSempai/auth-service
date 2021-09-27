FROM openjdk:11
MAINTAINER aidos.jantleu@gmail.com
COPY target/auth-api-*.war auth-api-.jar
ENTRYPOINT ["java","-jar","/auth-api-.jar"]