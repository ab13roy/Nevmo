FROM amazoncorretto:17.0.11-alpine AS build

RUN apk add --no-cache bash \
    && apk add --no-cache curl \
    && curl -sSL https://services.gradle.org/distributions/gradle-8.11.1-bin.zip -o gradle.zip \
    && unzip gradle.zip \
    && mv gradle-8.11.1 /opt/gradle \
    && ln -s /opt/gradle/bin/gradle /usr/local/bin/gradle \
    && rm gradle.zip

WORKDIR /app

COPY build.gradle settings.gradle /app/

COPY src /app/src

RUN gradle build --no-daemon

FROM amazoncorretto:17.0.11-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
