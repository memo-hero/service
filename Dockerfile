#####################
# build the jar
#####################
FROM arm64v8/gradle:jdk15 as builder
COPY --chown=gradle:gradle application /application
WORKDIR /application
RUN gradle clean build jar

#####################
# run the server
#####################
FROM eclipse-temurin
EXPOSE 8282
COPY --from=builder /application/build/libs/memohero-0.0.4.jar .
WORKDIR /
CMD java -jar ./memohero-0.0.4.jar