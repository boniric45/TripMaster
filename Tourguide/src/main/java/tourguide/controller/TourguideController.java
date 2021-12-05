package tourguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tourguide.beans.AttractionBean;
import tourguide.proxies.GpsUtilProxy;

import java.util.List;

@RestController
public class TourguideController {

    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @GetMapping(value = "/")
    public List<AttractionBean> home() {
        List<AttractionBean> listVisitedLocation = gpsUtilProxy.getAttractions();
        return listVisitedLocation;
    }


}
