package tourguide;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.proxies.user.UserProxy;
import tourguide.services.TourGuideServices;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestExecutorService {

    @Autowired
    UserProxy userProxy;

    @Test
    public void submitTrackUserLocationTask_shouldTerminateTheTaskBeforeElapsedTimeTest() throws InterruptedException, ExecutionException {

        // Given
        userProxy.getUserAllInternalUser(10);
        TourGuideServices tourGuideServices = new TourGuideServices();

        // When
        UserBean user = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        Future<VisitedLocationBean> trackUserTask = tourGuideServices.trackUserLocation(user);

        tourGuideServices.tracker.stopTracking();

        // Then
        Thread.sleep(5000);
        assertTrue(trackUserTask.isDone());
    }
}
