package tourguide.beans.user;

import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.gpsutil.AttractionBean;

public class UserRewardBean {
    public final VisitedLocationBean visitedLocation;
    public final AttractionBean attraction;
    private int rewardPoints;

    public UserRewardBean(VisitedLocationBean visitedLocation, AttractionBean attraction, int rewardPoints) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
        this.rewardPoints = rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

}
