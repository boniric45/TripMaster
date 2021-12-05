FROM java:8
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=build/libs/tourguide_gpsutil-v1.jar
COPY ${JAR_FILE} tourguide_gpsutil.jar
ENTRYPOINT ["java","-Duser.country=EN","-Duser.language=en","-jar","tourguide_gpsutil.jar"]

