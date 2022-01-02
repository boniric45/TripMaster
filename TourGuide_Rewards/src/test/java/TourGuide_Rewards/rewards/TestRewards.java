package TourGuide_Rewards.rewards;

import TourGuide_Rewards.controller.RewardsController;
import TourGuide_Rewards.services.RewardsServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRewards {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHomeTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to Rewards")));
    }

    @Test
    public void getAttractionRewardPointsTest() {

        // Given
        RewardsServices rewardsServices = new RewardsServices();
        RewardsController rewardsController = new RewardsController(rewardsServices);
        UUID attractationId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String rewardPoint = "";

        // When
        rewardPoint = rewardsController.getAttractionRewardPoints(attractationId, userId);

        // Then
        assertNotNull(rewardPoint);
    }
}
