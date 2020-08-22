FROM node:lts-alpine as vue-build
WORKDIR /app
COPY /frontend/ ./
RUN ls
RUN npm install
RUN npm run build

FROM maven:3.6.3-openjdk-11-slim as maven-build
WORKDIR /app
COPY /src ./src
COPY /pom-docker.xml ./pom.xml
COPY --from=vue-build /app/target/dist/ ./target/classes/static/
RUN mvn clean package
ENTRYPOINT ["java","-jar","/app/target/pricer-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080