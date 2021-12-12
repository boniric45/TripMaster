package TourGuide_GpsUtil.controller;

import TourGuide_GpsUtil.services.GpsUtilServices;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GpsUtilController {

    @Autowired
    GpsUtilServices gpsUtilServices;

    private Logger logger = LoggerFactory.getLogger(GpsUtilController.class);

    /**
     * Return String
     * @author Boniface Eric
     * 01/12/2021
     */
    @GetMapping(value = "/")
    public String home(){
        logger.info("GET /home");
        return "Welcome to GpsUtil";
    }

    /**
     * Return an user location as Json
     * @return VistedLocation
     * @author Boniface Eric
     * 01/12/2021
     */
    @GetMapping(value = "/getUserLocation")
    public VisitedLocation getUserLocation(@RequestParam UUID userId) {
        logger.info("GET /getUserLocation with param: userId: {}", userId);
        return gpsUtilServices.getUserLocation(userId);
    }

    /**
     * Return all attractions registered
     * @return List<Attraction>
     * @author Boniface Eric
     * 01/12/2021
     */
    @GetMapping(value="/getAttractions")
    public List<Attraction> getAttractions(){
        logger.info("GET /getAttractions");
        return gpsUtilServices.getAttractions();
    }


}
