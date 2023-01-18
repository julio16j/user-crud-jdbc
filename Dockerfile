FROM adoptopenjdk/maven-openjdk11 as build
WORKDIR /workspace/app
COPY src ./src
COPY pom.xml ./
RUN mvn clean install

FROM adoptopenjdk/openjdk11
COPY --from=build workspace/app/target/user-crud-jdbc.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]