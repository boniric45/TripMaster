package tourguide.proxies.gpsutil;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.user.UserBean;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gpsutil", url = "localhost:8081")
public interface GpsUtilProxy {

    @GetMapping(value = "/getAttractions")
    List<AttractionBean> getAttractions();

    @GetMapping(value = "/getUserLocation")
     VisitedLocationBean getUserLocation(@RequestParam UUID userId);
}

