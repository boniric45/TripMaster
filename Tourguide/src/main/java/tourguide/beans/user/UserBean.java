package tourguide.beans.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tourguide.beans.gpsutil.LocationBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.reward.ProviderBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserBean {

    private final UUID userId;
    private final String userName;
    List<VisitedLocationBean> visitedLocations = new ArrayList<>();
    private List<UserRewardBean> userRewards = Collections.synchronizedList(new ArrayList<>());
    private LocationBean location;
    private String phoneNumber;
    private String emailAddress;
    private UserPreferencesBean userPreferences = new UserPreferencesBean();
    private List<ProviderBean> tripDeals = new ArrayList<>();

    public UserBean(UUID userId, String userName, String phoneNumber, String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public List<VisitedLocationBean> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(List<VisitedLocationBean> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UUID getUserId() {
        return userId;
    }

    @JsonIgnore
    public VisitedLocationBean getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public List<UserRewardBean> getUserRewards() {
        return userRewards;
    }

    public void addToVisitedLocations(VisitedLocationBean visitedLocationGps) {
        visitedLocations.add(visitedLocationGps);
    }

    public void addUserReward(UserRewardBean userReward) {
        if (userRewards.stream().filter(r
                -> r.attraction.attractionName.equals(userReward.attraction.attractionName)).count() == 0) {
            userRewards.add(userReward);
        }
    }

    public UserPreferencesBean getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(UserPreferencesBean userPreferences) {
        this.userPreferences = userPreferences;
    }

    public void setTripDeals(List<ProviderBean> tripDeals) {
        this.tripDeals = tripDeals;
    }
}
