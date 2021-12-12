package TourGuide_User.services;

import TourGuide_User.domain.User;
import TourGuide_User.helper.InternalTestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Service
public class UserServices {

    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/
    private static final String tripPricerApiKey = "test-server-api-key";
    private final Logger logger = LoggerFactory.getLogger(UserServices.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10000);
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    private final Map<String, User> internalUserMap = new HashMap<>();
//    public Tracker tracker;
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    boolean testMode = true;

    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            TourGuide_User.domain.User user = new TourGuide_User.domain.User(UUID.randomUUID(), userName, phone, email);
//            generateUserLocationHistory(user);
            internalUserMap.put(userName, user);
        });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

        //retourne la map des users
        public List<User> getAllUsers() {
            initializeInternalUsers(); // generate User
            return new ArrayList<>(new ArrayList<>(internalUserMap.values()));
        }

    public User getUser(String userName) {
        initializeInternalUsers(); // generate User
        return internalUserMap.get(userName);
    }


//    public User getUser(String userName) {
//
//        return User;
//    }

//    private final GpsUtilProxy gpsUtil;
//     private final RewardsService rewardsService;
////    private final RewardCentral rewardCentral;

////    private TripPricer tripPricer = new TripPricer();
//
//
//    // Constructor
////    public TourGuideService(GpsUtil gpsUtil, RewardsService rewardsService, UserService userService) {
////        this.gpsUtil = gpsUtil;
////        this.rewardsService = rewardsService;
////        this.userService = userService;
////
////        if (testMode) {
////            logger.info("TestMode enabled");
////            logger.debug("Initializing users");
////            initializeInternalUsers();
////            logger.debug("Finished initializing users");
////        }
////        tracker = new Tracker(this);
////        addShutDownHook();
////        rewardCentral = null;
////
////    }
//
//    public UserServices(GpsUtilProxy gpsUtil) {
//        this.gpsUtil = gpsUtil;
//    }
//
//
////    public VisitedLocation getUserLocation(User user) {
////        return (user.getVisitedLocations().size() > 0) ?
////                user.getLastVisitedLocation() :
////                trackUserLocation(user);
////    }
////
////    public VisitedLocation trackUserLocation(User user) throws NumberFormatException {
////        GpsUtil gpsUtil = new GpsUtil();
////        VisitedLocation visitedLocationGps = gpsUtil.getUserLocation(user.getUserId());
////        user.addToVisitedLocations(visitedLocationGps);
////        rewardsService.calculateRewards(user);
////        return visitedLocationGps;
////    }
////
////    //retourne une liste de provider d'un user
////    public List<Provider> getTripDeals(User user) {
////        int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
////        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
////                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
////        user.setTripDeals(providers);
////        return providers;
////    }
//

//
//    //retourne la liste des attractions à proximité de la position
////    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
////        GpsUtil gpsUtil = new GpsUtil();
////        List<Attraction> nearbyAttractions = new ArrayList<>();
////        for (Attraction attraction : gpsUtil.getAttractions()) {
////            if (rewardsService.isWithinAttractionProximity(attraction, visitedLocation.location)) {
////                nearbyAttractions.add(attraction);
////            }
////        }
////        return nearbyAttractions;
////    }
//
//    //ajoute un arret
////    private void addShutDownHook() {
////        Runtime.getRuntime().addShutdownHook(new Thread(tracker::stopTracking));
////    }
//

//
////    public void addToVisitedLocations(VisitedLocation visitedLocation) {
////        logger.debug("Add visited location: " + visitedLocation);
////        visitedLocations.add(visitedLocation);
////    }
////
//    private void generateUserLocationHistory(User user) {
//        IntStream.range(0, 3).forEach(i -> {
//            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
//        });
//    }

//    private double generateRandomLongitude() {
//        double leftLimit = -180;
//        double rightLimit = 180;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private double generateRandomLatitude() {
//        double leftLimit = -85.05112878;
//        double rightLimit = 85.05112878;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private Date getRandomTime() {
//        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
//        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
//    }

//
//    private double generateRandomLongitude() {
//        double leftLimit = -180;
//        double rightLimit = 180;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private double generateRandomLatitude() {
//        double leftLimit = -85.05112878;
//        double rightLimit = 85.05112878;
//        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
//    }
//
//    private Date getRandomTime() {
//        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
//        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
//    }


}
