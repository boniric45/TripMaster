package tourguide.beans.gpsutil;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationBean {

    public UUID userId;
    public LocationBean location;
    public Date timeVisited;

    public VisitedLocationBean(UUID userId, LocationBean location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
    }

    public VisitedLocationBean(VisitedLocationBean visitedLocationBean) {

    }

    @Override
    public String toString() {
        return "VisitedLocationBean{" +
                "userId=" + userId +
                ", location=" + location +
                ", timeVisited=" + timeVisited +
                '}';
    }
}
