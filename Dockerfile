FROM openjdk
WORKDIR /app
COPY /target/instagram-api-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar", "instagram-api-0.0.1-SNAPSHOT.jar"]