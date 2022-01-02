package TourGuide_Rewards.tripPricer;

import TourGuide_Rewards.controller.TripPricerController;
import TourGuide_Rewards.services.TripPricerServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTripPricer {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHomeTest() throws Exception {
        mockMvc.perform(get("/tripPricer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to TripPricer")));
    }

    @Test
    public void getPriceTest() {

        // Given
        TripPricerServices tripPricerServices = new TripPricerServices();
        TripPricerController tripPricerController = new TripPricerController(tripPricerServices);
        int numberOfAdults = 2;
        int numberOfChildren = 3;
        int tripDuration = 14;

        // When
        List<Provider> providers = tripPricerController.getPrice("", UUID.randomUUID(), numberOfAdults,
                numberOfChildren, tripDuration, 10);

        // Then
        assertEquals(5, providers.size());

    }

    @Test
    public void getProviderNameTest() {
        // Given
        TripPricerServices tripPricerServices = new TripPricerServices();
        TripPricerController tripPricerController = new TripPricerController(tripPricerServices);

        // When
        String provider = tripPricerController.getProviderName("", 0);

        // Then
        assertNotNull(provider);
    }


}
