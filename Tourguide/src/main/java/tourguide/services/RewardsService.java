package tourguide.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.LocationBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.beans.user.UserRewardBean;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RewardsService {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    public static int attractionProximityRange = 200;
    private final GpsUtilProxy gpsUtil;
    private final RewardProxy rewardsCentral;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10000);
    Logger logger = LoggerFactory.getLogger(RewardsService.class);
    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;

    // Constructor
    public RewardsService(GpsUtilProxy gpsUtil, RewardProxy rewardCentral) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentral = rewardCentral;
    }

    // Executor Service
    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }

    /**
     * Calculate Rewards
     *
     * @Param user
     * @author Boniface Eric
     * 12/12/2021
     */
    public void calculateRewards(UserBean user) {

        List<VisitedLocationBean> userLocations = user.getVisitedLocations();
        List<AttractionBean> attractions = gpsUtil.getAttractions();

        logger.debug("Entering calculate rewards for user: " + user.getUserName());
        for (VisitedLocationBean visitedLocation : userLocations) {
            for (AttractionBean attraction : attractions) {
                if (user.getUserRewards().stream().noneMatch(r -> r.attraction.attractionName.equals(attraction.attractionName))) {
                    if (nearAttraction(visitedLocation, attraction)) {
                        logger.debug("Creating parallel task to retrieve user rewards for attraction: " + attraction.attractionName);
                        executorService.submit(() -> {
                            user.addUserReward(new UserRewardBean(visitedLocation, attraction, getRewardPoints(attraction, user)));
                        });
                    }
                }
            }
        }
    }

    public boolean isWithinAttractionProximity(AttractionBean attraction, LocationBean location) {
        return !(getDistance(attraction, location) > attractionProximityRange);
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
     * Get  Attraction Rewards Points
     *
     * @return int (Number Points)
     * @Param attraction and user
     * @author Boniface Eric
     * 12/12/2021
     */
    public int getRewardPoints(AttractionBean attraction, UserBean user) {
        return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
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
}
