package tourguide.beans.reward;

import java.util.UUID;

public class RewardBean {

    UUID attractionId;
    UUID userid;
    int rewardPoints;

    public RewardBean(UUID attractionId, UUID userid, int rewardPoints) {
        this.attractionId = attractionId;
        this.userid = userid;
        this.rewardPoints = rewardPoints;
    }

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
