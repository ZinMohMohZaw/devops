FROM amazoncorretto:25
COPY ./target/seMethods-0.1.0.3-jar-with-dependencies.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "app.jar"]