package tourguide.proxies;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.beans.reward.ProviderBean;
import tourguide.proxies.reward.TripPricerProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProxyTripPricer {

    @Autowired
    TripPricerProxy tripPricerProxy;

    @Autowired
    MockMvc mockMvc;

    @Test
    // Can only be used when TourGuide_Reward is UP
    public void getTripPricerProxyProviderTest() throws Exception {

        // Given
        String apiKey = "5";
        String provider = "";
        int adults = 2;

        // When
        provider = tripPricerProxy.getProviderName(apiKey, adults);

        mockMvc.perform(get("http://localhost:8083/getProviderName?apiKey=" + apiKey + "&adults=" + adults))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        Assertions.assertNotNull(provider);
    }


    @Test
    // Can only be used when TourGuide_Reward is UP
    public void getTripPricerProxyGetPrice() throws Exception {

        // Given
        String apiKey = "";
        UUID attractionId = UUID.randomUUID();
        int adults = 2;
        int children = 3;
        int nightsStay = 9;
        int rewardsPoints = 12;
        List<ProviderBean> providerList = new ArrayList<>();

        // When
        providerList = tripPricerProxy.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);

        mockMvc.perform(get("http://localhost:8080/getPrice?apiKey=" + apiKey + "&attractionId=" + attractionId + "&adults=" + adults + "&children=" + children + "&nightsStay=" + nightsStay + "&rewardsPoints=" + rewardsPoints))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        Assertions.assertEquals(5, providerList.size());
    }
}
