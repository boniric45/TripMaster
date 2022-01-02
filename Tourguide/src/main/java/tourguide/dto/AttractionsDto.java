package tourguide.dto;

import tourguide.beans.gpsutil.LocationBean;

public class AttractionsDto {

    private String name;
    private LocationBean userLocation;
    private LocationBean location;
    private Double distance;
    private int rewardPoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationBean getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LocationBean userLocation) {
        this.userLocation = userLocation;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

}
