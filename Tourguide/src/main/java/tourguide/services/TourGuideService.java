package tourguide.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourguide.beans.gpsutil.LocationBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.dto.UserLastLocationDto;
import tourguide.beans.user.UserRewardBean;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.proxies.user.UserProxy;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


@Service
public class TourGuideService {

    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @Autowired
    RewardProxy rewardProxy;

    @Autowired
    UserProxy userProxy;

    private Logger logger = LoggerFactory.getLogger(TourGuideService.class);

    public VisitedLocationBean getUserLocation(UserBean user) {
        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    public VisitedLocationBean trackUserLocation(UserBean user) throws NumberFormatException {
        VisitedLocationBean visitedLocationGps = gpsUtilProxy.getUserLocation(user.getUserId());
        user.addToVisitedLocations(visitedLocationGps);
//        rewardProxy.calculateRewards(user);
        return visitedLocationGps;
    }

    public UserBean getUser(String userName) {
        return userProxy.getUser(userName);
    }

    public List<UserBean> getAllUser() {
        return new ArrayList<>(userProxy.userAll());
    }

    public List<UserRewardBean> getUserRewards(UserBean user) {
        return user.getUserRewards();
    }

    /**
     * Create a list of all user last visited location
     *
     * @return List<UserLastLocationDto>
     * @author Boniface Eric
     * 13/11/2021
     */
    public List<UserLastLocationDto> getAllUserLastVisitedLocation() {
        List<UserLastLocationDto> listLocation = new ArrayList<>();
        for (UserBean user : getAllUser()) {
            UserLastLocationDto lastLocation = new UserLastLocationDto();
            lastLocation.setUserId(user.getUserId().toString());
            System.out.println(user.getLocation());
            lastLocation.setLastLocation(user.getLastVisitedLocation().location);
            listLocation.add(lastLocation);
        }
        return listLocation;
    }

    //génère l'historique de la position d'un user
    private void generateUserLocationHistory(UserBean user) {
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocationBean(user.getUserId(), new LocationBean(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    // génère une longitude aléatoire
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    // génère une latitude aléatoire
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}
