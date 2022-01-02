package tourguide.dto;

import tourguide.beans.gpsutil.LocationBean;

public class UserLastLocationDto {

    private String userId;
    private LocationBean lastLocation;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocationBean getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(LocationBean lastLocation) {
        this.lastLocation = lastLocation;
    }

}
