#####################
# build the jar
#####################
FROM arm64v8/gradle:jdk15 as builder
COPY --chown=gradle:gradle application /application
WORKDIR /application
RUN gradle wrapper --gradle-version 7.6
RUN ./gradlew shadowJar

#####################
# run the server
#####################
FROM eclipse-temurin
EXPOSE 8282
COPY --from=builder /application/build/libs/memohero-1.1.2-all.jar .
WORKDIR /
CMD java -jar ./memohero-1.1.2-all.jar