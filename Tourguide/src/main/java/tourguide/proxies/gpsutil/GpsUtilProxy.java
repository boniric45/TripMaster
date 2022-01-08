package tourguide.proxies.gpsutil;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gpsutil", url = "localhost:8081")
public interface GpsUtilProxy {

    @RequestMapping(value = "/getAttractions")
    List<AttractionBean> getAttractions();

    @RequestMapping(value = "/getUserLocation")
    VisitedLocationBean getUserLocation(@RequestParam UUID userId);
}

