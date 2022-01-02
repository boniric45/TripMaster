# TripMaster

To create the docker containers, enter the following commands in a terminal:

1 - docker build -t tourguide_gpsutil .
2 - docker  run -d -p 8081:8081 --name Tourguide_gpsutil tourguide_gpsutil

3 - docker build -t tourguide_user .
4 - docker  run -d -p 8082:8082 --name Tourguide_user tourguide_user

5 - docker build -t tourguide_rewards .
6 - docker  run -d -p 8083:8083 --name Tourguide_rewards tourguide_rewards