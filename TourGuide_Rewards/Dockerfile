FROM java:8
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=build/libs/tourguide_rewards-v1.jar
COPY ${JAR_FILE} tourguide_rewards.jar
ENTRYPOINT ["java","-Duser.country=EN","-Duser.language=en","-jar","tourguide_rewards.jar"]