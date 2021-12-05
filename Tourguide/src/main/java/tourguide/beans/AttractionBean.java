package tourguide.beans;

import java.util.UUID;

public class AttractionBean extends LocationBean {
    public final String attractionName;
    public final String city;
    public final String state;
    public final UUID attractionId;

    public AttractionBean(String attractionName, String city, String state, double latitude, double longitude) {
        super(latitude, longitude);
        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = UUID.randomUUID();
    }
}
