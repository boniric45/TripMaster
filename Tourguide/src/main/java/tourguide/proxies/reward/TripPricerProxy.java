package tourguide.proxies.reward;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourguide.beans.reward.ProviderBean;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "tripPricer", url = "localhost:8083")
public interface TripPricerProxy {

    @GetMapping("/getProviderName")
    String getProviderName(@RequestParam String apiKey, @RequestParam int adults);

    @GetMapping("/getPrice")
    List<ProviderBean> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId,
                                @RequestParam int adults, @RequestParam int children,
                                @RequestParam int nightsStay, @RequestParam int rewardsPoints);

}
