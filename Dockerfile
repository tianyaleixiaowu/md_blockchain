FROM daocloud.io/brave8/maven-jdk8

ADD md_blockchain-0.0.1-SNAPSHOT.jar /tmp/app.jar
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tmp/app.jar"]