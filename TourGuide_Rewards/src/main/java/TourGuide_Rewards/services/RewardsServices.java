package TourGuide_Rewards.services;

import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardsServices {

    private RewardCentral rewardCentral = new RewardCentral();

    /**
     * Return an attraction reward points using RewardCentral library
     *
     * @param attractionId
     * @param userId
     * @return int
     * @author Boniface Eric
     * 06/12/2021
     */
    public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
        return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }

}
