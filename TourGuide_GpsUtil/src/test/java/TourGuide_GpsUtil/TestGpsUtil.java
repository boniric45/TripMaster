package TourGuide_GpsUtil;

import TourGuide_GpsUtil.controller.GpsUtilController;
import TourGuide_GpsUtil.services.GpsUtilServices;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestGpsUtil {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomeTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to GpsUtil")));
    }

    @Test
    public void getAttractionsRegisteredTest() {

        // Given
        GpsUtilServices gpsUtilServices = new GpsUtilServices();
        GpsUtilController gpsUtilController = new GpsUtilController(gpsUtilServices);

        // When
        List<Attraction> attractionList = gpsUtilController.getAttractions();

        // Then
        assertEquals(26, attractionList.size());
    }

    @Test
    public void getUserLocationTest() {

        // Given
        GpsUtilServices gpsUtilServices = new GpsUtilServices();
        GpsUtilController gpsUtilController = new GpsUtilController(gpsUtilServices);
        UUID userId = UUID.randomUUID();

        // Given
        VisitedLocation visitedLocation = gpsUtilController.getUserLocation(userId);

        // Then
        Assertions.assertNotNull(visitedLocation.location);
    }

}
