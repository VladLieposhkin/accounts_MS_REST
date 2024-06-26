FROM eclipse-temurin:21.0.2_13-jdk-jammy as build

ARG JAR_FILE
WORKDIR /build

ADD $JAR_FILE application.jar
RUN java -Djarmode=layertools -jar application.jar extract --destination extracted

FROM eclipse-temurin:21.0.2_13-jdk-jammy

#RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
#USER spring-boot:spring-boot-group
VOLUME /tmp
WORKDIR /application

#ENV port 8081
#ENV datasource.url 'jdbc:postgresql://host.docker.internal:5432/accounts'
#ENV datasource.user 'accounts'
#ENV datasource.password 'accounts'


COPY --from=build /build/extracted/dependencies .
COPY --from=build /build/extracted/spring-boot-loader .
COPY --from=build /build/extracted/snapshot-dependencies .
COPY --from=build /build/extracted/application .

ENTRYPOINT exec java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher ${0} ${@}

#docker build --build-arg JAR_FILE=accounts-service/target/accounts-service-1.0.0-exec.jar -t accounts-service:latest .

#docker run --rm -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker --name accounts-service accounts-service:latest

