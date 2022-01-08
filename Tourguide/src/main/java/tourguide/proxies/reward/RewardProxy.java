package tourguide.proxies.reward;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "reward", url = "localhost:8083")
public interface RewardProxy {

    // consumes and produces for decoder response
    @RequestMapping(value = "/getAttractionRewardPoints", consumes = "application/json", produces = "application/json")
    int getAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId);

}
