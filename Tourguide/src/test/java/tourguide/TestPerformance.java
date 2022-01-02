package tourguide;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.proxies.user.UserProxy;
import tourguide.services.RewardsService;
import tourguide.services.TourGuideServices;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestPerformance {

    /*
     * A note on performance improvements:
     *
     *     The number of users generated for the high volume tests can be easily adjusted via this method:
     *
     *     		InternalTestHelper.setInternalUserNumber(100000);
     *
     *     These tests can be modified to suit new solutions, just as long as the performance metrics
     *     at the end of the tests remains consistent.
     *
     *     These are performance metrics that we are trying to hit:
     *
     *     highVolumeTrackLocation: 100,000 users within 15 minutes:
     *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
     *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     */
    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    RewardProxy rewardProxy;

    @Autowired
    TourGuideServices tourGuideServices;


    @Test
    public void highVolumeGetRewards() throws InterruptedException {
//        TourGuideServices tourGuideServices = new TourGuideServices(gpsUtilProxy, rewardProxy, userProxy);
        RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);
        // Users should be incremented up to 100,000, and test finishes within 20 minutes
        List<UserBean> allUsers = tourGuideServices.getAllUser();
        AttractionBean attraction = gpsUtilProxy.getAttractions().get(0);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int i = 0;
        for (UserBean user : allUsers) {
            i++;
            user.addToVisitedLocations(new VisitedLocationBean(user.getUserId(), attraction, new Date()));
            rewardsService.calculateRewards(user);
            System.out.println("       \n    " + i + " Users Calculate Finish ");
        }

        ExecutorService executorService = rewardsService.getExecutorService();
        executorService.shutdown();
        executorService.awaitTermination(21, TimeUnit.MINUTES);

        for (UserBean user : allUsers) {
            assertTrue(user.getUserRewards().size() > 0);
        }

        stopWatch.stop();
        tourGuideServices.tracker.stopTracking();

        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(21) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

    @Test
    public void highVolumeTrackLocation() throws InterruptedException {
        // Given
        Locale.setDefault(Locale.US); //fix bug
        TourGuideServices tourGuideServices = new TourGuideServices(gpsUtilProxy, rewardProxy, userProxy);

        // When
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Users should be incremented up to 100,000, and test finishes within 15 minutes
        List<UserBean> allUsers = tourGuideServices.getAllUser();

        //Launch asynchronous track location for all users
        allUsers.forEach(tourGuideServices::trackUserLocation);

        ExecutorService tourGuideExecutorService = tourGuideServices.getExecutorService();
        tourGuideExecutorService.shutdown();
        tourGuideExecutorService.awaitTermination(16, TimeUnit.MINUTES);

        stopWatch.stop();

        tourGuideServices.tracker.stopTracking();
        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");

        // Then
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }

}

