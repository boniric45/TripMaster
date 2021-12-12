package tourguide.proxies.reward;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tourguide.beans.user.UserBean;

@FeignClient(name = "reward", url = "localhost:8083")
public interface RewardProxy {

    @GetMapping(value = "/")
    String home();




}
