package TourGuide_GpsUtil.services;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class GpsUtilServices {

    private GpsUtil gpsUtil = new GpsUtil();

    /**
     * Get all attractions registered
     *
     * @return List<Attraction>
     * @author Boniface Eric
     * 01/12/2021
     */
    public List<Attraction> getAttractions() {
        return gpsUtil.getAttractions();
    }

    /**
     * Get an user last visited location
     *
     * @return Object VisitedLocation
     * @author Boniface Eric
     * 01/12/2021
     */
    public VisitedLocation getUserLocation(UUID userId) {
        Locale.setDefault(Locale.US);
        return gpsUtil.getUserLocation(userId);
    }

}
