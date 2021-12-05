FROM java:8
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=build/libs/tourguide_user-v1.jar
COPY ${JAR_FILE} tourguide_user.jar
ENTRYPOINT ["java","-Duser.country=EN","-Duser.language=en","-jar","tourguide_user.jar"]