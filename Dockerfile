FROM openjdk:14
EXPOSE 9000
ADD /target/marketList-0.0.1-SNAPSHOT.jar marketList.jar
ENTRYPOINT [ "java", "-jar", "marketList.jar"]