FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src/ src/
COPY docker/entrypoint.sh entrypoint.sh

VOLUME /app/target

ENTRYPOINT ["sh", "entrypoint.sh"]
