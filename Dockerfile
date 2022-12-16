#####################
# build the jar
#####################
FROM gradle:jdk15 as builder
COPY --chown=gradle:gradle application /application
WORKDIR /application
RUN gradle clean build jar

#####################
# run the server
#####################
FROM eclipse-temurin
EXPOSE 8282
COPY --from=builder /application/build/libs/miniktor-0.0.1.jar .
WORKDIR /
CMD java -jar ./miniktor-0.0.1.jar