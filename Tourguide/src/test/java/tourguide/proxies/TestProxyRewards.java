package tourguide.proxies;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.proxies.reward.RewardProxy;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProxyRewards {

    @Autowired
    RewardProxy rewardProxy;

    @Autowired
    MockMvc mockMvc;

    @Test
    // Can only be used when TourGuide_Reward is UP
    public void getAttractionRewardPointsTest() throws Exception {

        // Given
        UUID attractionId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        int rewardPoints = 0;

        // When
        rewardPoints = rewardProxy.getAttractionRewardPoints(attractionId, userId);
        mockMvc.perform(get("http://localhost:8080/getAttractionRewardPoints?attractionId=" + attractionId + "&userId=" + userId))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        MatcherAssert.assertThat(rewardPoints, allOf(greaterThan(0), lessThan(5000)));

    }

}
