package tourguide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tourguide.beans.AttractionBean;

import java.util.List;

@FeignClient(name = "gpsutil", url = "localhost:8081")
public interface GpsUtilProxy {

    @GetMapping(value = "/getAttractions")
    List<AttractionBean> getAttractions();

}

