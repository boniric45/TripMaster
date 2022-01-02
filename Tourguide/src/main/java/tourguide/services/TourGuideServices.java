package tourguide.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.LocationBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.reward.ProviderBean;
import tourguide.beans.user.UserBean;
import tourguide.beans.user.UserPreferencesBean;
import tourguide.beans.user.UserRewardBean;
import tourguide.dto.AttractionsDto;
import tourguide.dto.UserLastLocationDto;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.proxies.reward.TripPricerProxy;
import tourguide.proxies.user.UserProxy;
import tourguide.tracker.Tracker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;


@Service
public class TourGuideServices {

    @Autowired
    UserProxy userProxy;
    @Autowired
    GpsUtilProxy gpsUtilProxy;
    @Autowired
    RewardProxy rewardProxy;
    @Autowired
    TripPricerProxy tripPricerProxy;

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private static final String tripPricerApiKey = "test-server-api-key";
    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);//1000
    public Tracker tracker = new Tracker(this);
    boolean testMode = true;

    private RewardsService rewardsService;
    private Logger logger = LoggerFactory.getLogger(TourGuideServices.class);
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    private int attractionProximityRange = 200;

    // Constructor
    public TourGuideServices(GpsUtilProxy gpsUtil, RewardProxy rewardProxy) {
        this.gpsUtilProxy = gpsUtil;
        this.rewardProxy = rewardProxy;

        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            logger.debug("Finished initializing users");
        }
        tracker = new Tracker(this);
        addShutDownHook();
    }

    // Constructor
    public TourGuideServices(GpsUtilProxy gpsUtilProxy, RewardProxy rewardProxy, UserProxy userProxy) {
        this.gpsUtilProxy = gpsUtilProxy;
        this.rewardProxy = rewardProxy;
        this.userProxy = userProxy;

        if (testMode) {
            logger.info("TestMode enabled");
            getUserAllInternalUser(50000);
            logger.debug("Initializing users");
            logger.debug("Finished initializing users");
        }
        tracker = new Tracker(this);
        addShutDownHook();
    }

    // Default Constructor
    public TourGuideServices(){}

    // Stop Tracking
    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }

    /**
     * Get Location User by Tourguide_Gpsutil
     *
     * @return Location visited
     * @author Boniface Eric
     * 12/12/2021
     */
    public VisitedLocationBean  getUserLocation(UserBean user) throws InterruptedException {
        VisitedLocationBean visitedLocation = null;
        try {
            if (user.getVisitedLocations().size()>0){
            } else {
             visitedLocation =  trackUserLocation(user).get();
            }

        }
        catch (InterruptedException | ExecutionException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return visitedLocation;
//        return (user.getVisitedLocations().size() > 0) ? user.getLastVisitedLocation() : trackUserLocation(user).get(); // Todo
    }


    /**
     * Get Visited Location User by Tourguide_Gpsutil
     *
     * @param user
     * @return Future Location visited
     * @author Boniface Eric
     * 12/12/2021
     */
    public Future<VisitedLocationBean> trackUserLocation(UserBean user) {
        logger.debug("Creating parallel task getUserLocation for user: " + user.getUserName());
        //Creating parallel task getUserLocation
        return executorService.submit(() -> {
            VisitedLocationBean visitedLocation = gpsUtilProxy.getUserLocation(user.getUserId());
            logger.debug("Getting userLocation: " + visitedLocation);
            user.addToVisitedLocations(visitedLocation);
//            rewardsService.calculateRewards(user);
            return visitedLocation;
        });
    }

    /**
     * Get  User by Tourguide_User
     *
     * @return User
     * @Param userName
     * @author Boniface Eric
     * 12/12/2021
     */
    public UserBean getUser(String userName) {
        return userProxy.getUser(userName);
    }

    /**
     * Get  List All User by Tourguide_User
     *
     * @return List of User générated
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<UserBean> getAllUser() {
        return new ArrayList<>(userProxy.userAll());
    }

    /**
     * Get  User Generated by Tourguide_User
     *
     * @return List User Generated
     * @Param numberUser
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<UserBean> getUserAllInternalUser(int numberUser) {
        return new ArrayList<>(userProxy.getUserAllInternalUser(numberUser));
    }

    /**
     * Get  Rewards by userName
     *
     * @return List<UserRewardBean>
     * @Param user
     * @author Boniface Eric
     * 12/12/2021
     */
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
    public List<UserLastLocationDto> getAllUserLastVisitedLocation() throws ExecutionException, InterruptedException {
        List<UserLastLocationDto> listLocation = new ArrayList<>();
        for (UserBean user : getAllUser()) {
            System.out.println(user.getUserName());
            UserLastLocationDto lastLocation = new UserLastLocationDto();
            VisitedLocationBean visitedLocationBean = this.getUserLocation(user); // Get Location with userId
            lastLocation.setUserId(user.getUserId().toString());
            lastLocation.setLastLocation(visitedLocationBean.location);
            listLocation.add(lastLocation);
        }
        return listLocation;
    }

    /**
     * Get the closest attractions from actual user location
     *
     * @param userLocation actual user location
     * @param amount       Number of attraction to retrieve
     * @return SortedMap<Double, Attraction> a sorted map of attraction, first is the closest
     * @author Boniface Eric
     * 12/12/2021
     */
    public SortedMap<Double, AttractionBean> getClosestAttractions(VisitedLocationBean userLocation, int amount) {
        SortedMap<Double, AttractionBean> map = new TreeMap<>();
        SortedMap<Double, AttractionBean> attractions = new TreeMap<>();

        //Create a sorted map of all attractions where the key is the distance between the user and the attraction
        for (AttractionBean attraction : gpsUtilProxy.getAttractions()) {
            Double userDistance = getDistance(userLocation.location, new LocationBean(attraction.latitude, attraction.longitude));
            map.put(userDistance, attraction);
        }

        //Extract the amount of attraction we want
        Iterator<Double> it = map.keySet().iterator();
        int i = 0;
        while (i < amount && it.hasNext()) {
            Double distance = it.next();
            AttractionBean attraction = map.get(distance);
            attractions.put(distance, attraction);
            i++;
        }

        return attractions;
    }

    /**
     * Get  Near by Attraction by userName
     *
     * @return List<AttractionsDto>
     * @Param user and number user location
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<AttractionsDto> mapClosestFiveTouristAttractionsToTheUser(UserBean user, int numberOfAttractions) {
        List<VisitedLocationBean> visitedLocation = this.generateUserLocationHistory(user, 5);
        user.setVisitedLocations(visitedLocation);
        List<AttractionsDto> attractionsDtoList = new ArrayList<AttractionsDto>();
        SortedMap<Double, AttractionBean> attractions = getClosestAttractions(user.getLastVisitedLocation(), numberOfAttractions);
        attractions.forEach((distance, attraction) -> {
            AttractionsDto attractionDto = new AttractionsDto();

            attractionDto.setName(attraction.attractionName);
            attractionDto.setLocation(new LocationBean(attraction.latitude, attraction.longitude));
            attractionDto.setUserLocation(user.getLastVisitedLocation().location);
            attractionDto.setDistance(distance);
            attractionDto.setRewardPoint(rewardProxy.getAttractionRewardPoints(attraction.attractionId, user.getUserId()));

            attractionsDtoList.add(attractionDto);
        });

        return attractionsDtoList;

    }

    /**
     * Get  Distance
     *
     * @return double statuteMiles
     * @Param loc1 and loc2 (LocationBean)
     * @author Boniface Eric
     * 12/12/2021
     */
    public double getDistance(LocationBean loc1, LocationBean loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

    /**
     * Get  Attraction Rewards Points with Micro Service Tourguide_Rewards
     *
     * @return int (Number Points)
     * @Param attraction and user
     * @author Boniface Eric
     * 12/12/2021
     */
    public int getRewardPoints(AttractionBean attraction, UserBean user) {
        return rewardProxy.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
    }

    /**
     * Get  Boolean if attraction is near
     *
     * @return Boolean
     * @Param visitedLocation and attraction
     * @author Boniface Eric
     * 12/12/2021
     */
    private boolean nearAttraction(VisitedLocationBean visitedLocation, AttractionBean attraction) {
        return !(getDistance(attraction, visitedLocation.location) > proximityBuffer);
    }

    /**
     * Get  Boolean if attraction is near
     *
     * @return boolean
     * @Param attraction and location
     * @author Boniface Eric
     * 12/12/2021
     */
    public boolean isWithinAttractionProximity(AttractionBean attraction, LocationBean location) {
        return !(getDistance(attraction, location) > attractionProximityRange);
    }

    /**
     * Get Random Longitude
     *
     * @return double
     * @author Boniface Eric
     * 12/12/2021
     */
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Get Random Latitude
     *
     * @return double
     * @author Boniface Eric
     * 12/12/2021
     */
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Get Random Time
     *
     * @return Date
     * @author Boniface Eric
     * 12/12/2021
     */
    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    /**
     * Generate User Location History
     *
     * @return List<VisitedLocationBean>
     * @Param user and numberUserLocation
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<VisitedLocationBean> generateUserLocationHistory(UserBean user, int numberUserLocation) {
        List<VisitedLocationBean> listVisitedLocation = new ArrayList<>();
        IntStream.range(0, numberUserLocation).forEach(i -> {
            LocationBean locationBean = new LocationBean(generateRandomLatitude(), generateRandomLongitude());
            listVisitedLocation.add(new VisitedLocationBean(user.getUserId(), locationBean, getRandomTime()));
        });
        return listVisitedLocation;
    }

    /**
     * Get  Attraction Rewards Points
     *
     * @return int (Number Points)
     * @Param attractionId and userId
     * @author Boniface Eric
     * 12/12/2021
     */
    public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
        return rewardProxy.getAttractionRewardPoints(attractionId, userId);
    }

    /**
     * Return a random provider name using tripPricer API
     *
     * @param apiKey
     * @param adults
     * @return String
     * @author Boniface Eric
     * 06/12/2021
     */
    public String getProviderName(String apiKey, int adults) {
        return tripPricerProxy.getProviderName(apiKey, adults);
    }

    /**
     * Get  Price
     *
     * @return List<ProviderBean>
     * @Param apiKey, attractionId, adults, children, nightsStay, rewardsPoints
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<ProviderBean> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
        return tripPricerProxy.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    /**
     * Get  Attractions by Micro Service GpsUtil
     *
     * @return List<AttractionBean>
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<AttractionBean> getAttractions() {
        return gpsUtilProxy.getAttractions();
    }

    /**
     * Get Trip Deals
     *
     * @return List<ProviderBean>
     * @Param userName, numberOfAdults, numberOfChildren, tripDuration
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<ProviderBean> getTripDeals(String userName, int numberOfAdults, int numberOfChildren, int tripDuration) {
        UserPreferencesBean userPreferencesBean = new UserPreferencesBean();
        UserBean user = userProxy.getUser(userName);

        userPreferencesBean.setUserName(userName);
        userPreferencesBean.setCurrency("Euros");
        userPreferencesBean.setAttractionProximity(5);

        userPreferencesBean.setNumberOfAdults(numberOfAdults);
        userPreferencesBean.setNumberOfChildren(numberOfChildren);
        userPreferencesBean.setTripDuration(tripDuration);
        user.setUserPreferences(userPreferencesBean);

        int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();

        List<AttractionBean> attractionList = getAttractions();
        UUID attractionId = attractionList.get(0).attractionId;

        List<ProviderBean> providers = tripPricerProxy.getPrice(tripPricerApiKey, attractionId, user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);

        user.setTripDeals(providers);
        return providers;
    }

    // Executor Service
    public ExecutorService getExecutorService() {
        return executorService;
    }
}

