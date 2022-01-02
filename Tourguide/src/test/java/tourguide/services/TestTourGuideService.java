package tourguide.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.reward.ProviderBean;
import tourguide.beans.user.UserBean;
import tourguide.dto.AttractionsDto;
import tourguide.dto.UserLastLocationDto;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.proxies.user.UserProxy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestTourGuideService {

    @Autowired
    GpsUtilProxy gpsUtil;

    @Autowired
    RewardProxy rewardCentral;

    @Autowired
    UserProxy userProxy;

    @Autowired
    TourGuideServices tourGuideServices;

    @Test
    public void getUserLocationTest() throws ExecutionException, InterruptedException {

        // Given
        tourGuideServices.userProxy.getUserAllInternalUser(5);
        UserBean userBean = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        // When
        VisitedLocationBean visitedLocationBean = tourGuideServices.getUserLocation(userBean);
        tourGuideServices.tracker.stopTracking();

        // Then
        assertEquals(visitedLocationBean.userId, userBean.getUserId());
    }

    @Test
    public void getTrackUserLocationTest() throws ExecutionException, InterruptedException {

        // Given
        TourGuideServices tourGuideServices = new TourGuideServices(gpsUtil, rewardCentral, userProxy);
        UserBean userBean = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        // When
        VisitedLocationBean visitedLocationBean = tourGuideServices.trackUserLocation(userBean).get();
        tourGuideServices.tracker.stopTracking();

        // Then
        assertEquals(visitedLocationBean.userId, userBean.getUserId());

    }

    @Test
    public void getUserTest() {
        // Given
        String userName = "internalUser0";

        // When
        UserBean userBean = tourGuideServices.getUser(userName);

        // Then
        assertEquals(userBean.getUserName(), userName);
    }

    @Test
    public void getListAllUserTest() {

        // Given
        List<UserBean> userList;
        tourGuideServices.getUserAllInternalUser(2);

        // When
        userList = tourGuideServices.getAllUser();

        // Then
        assertEquals(2, userList.size());
    }

    @Test
    public void getAllUserLastVisitedLocationTest() throws ExecutionException, InterruptedException {

        // Given
        List<UserLastLocationDto> listLocation;
        tourGuideServices.getUserAllInternalUser(10);

        // When
        listLocation = tourGuideServices.getAllUserLastVisitedLocation();

        // Then
        assertEquals(10, listLocation.size());
    }

    @Test
    public void getClosestAttractions_shouldReturn5ClosestAttractionSortedByProximityTest() throws InterruptedException, ExecutionException {
        tourGuideServices.getUserAllInternalUser(10);
        TourGuideServices tourGuideService = new TourGuideServices(gpsUtil, rewardCentral);

        UserBean user = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        VisitedLocationBean userLocation = tourGuideService.trackUserLocation(user).get();

        Map<Double, AttractionBean> attractions = tourGuideService.getClosestAttractions(userLocation, 5);

        tourGuideService.tracker.stopTracking();

        // List assert
        assertEquals(5, attractions.size());

        // Sort assert
        Iterator<Double> it = attractions.keySet().iterator();
        while (it.hasNext()) {
            Double value = it.next();
            if (it.hasNext()) {
                assertTrue(value < it.next());
            }
        }
    }

    @Test
    public void mapClosestFiveTouristAttractionsToTheUserTest() throws InterruptedException, ExecutionException {

        UserBean user = new UserBean(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        tourGuideServices.trackUserLocation(user).get();

        List<AttractionsDto> attractions = tourGuideServices.mapClosestFiveTouristAttractionsToTheUser(user, 5);

        tourGuideServices.tracker.stopTracking();

        assertEquals(5, attractions.size());
        assertNotNull(attractions.get(0).getDistance());
        assertNotNull(attractions.get(0).getLocation());
        assertNotNull(attractions.get(0).getName());
        assertNotNull(attractions.get(0).getUserLocation());

    }

    @Test
    public void getTripDealsTest() {

        // Given
        tourGuideServices.userProxy.getUserAllInternalUser(5);
        UserBean user = new UserBean(UUID.randomUUID(), "internalUser3", "000", "internalUser3@tourGuide.com");

        // When
        List<ProviderBean> providers = tourGuideServices.getTripDeals(user.getUserName(), 2, 2, 9);
        tourGuideServices.tracker.stopTracking();

        // Then
        assertEquals(5, providers.size());
    }

}

