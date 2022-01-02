package tourguide;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.helper.InternalTestHelper;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.services.RewardsService;
import tourguide.services.TourGuideServices;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestExecutorService {

    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @Autowired
    RewardProxy rewardProxy;


    @Autowired
    TourGuideServices tourGuideServices;

    @Test
    public void testSubmitTrackUserLocationTask_shouldTerminateTheTaskBeforeElapsedTime() throws InterruptedException, ExecutionException {

        RewardsService rewardsService = new RewardsService(gpsUtilProxy, rewardProxy);

        TourGuideServices tourGuideServices = new TourGuideServices(gpsUtilProxy, rewardProxy);
        InternalTestHelper.setInternalUserNumber(0);


        UserBean user = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        Future<VisitedLocationBean> trackUserTask = tourGuideServices.trackUserLocation(user);

        tourGuideServices.tracker.stopTracking();

        Thread.sleep(5000);
        assertTrue(trackUserTask.isDone());
    }
}
