package tourguide.proxies.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourguide.beans.user.UserBean;

import java.util.List;

@FeignClient(name = "user", url = "localhost:8082")
public interface UserProxy {

    @RequestMapping(value = "/getUser")
    UserBean getUser(@RequestParam("userName") String userName);

    @GetMapping(value = "/getAllUser")
    List<UserBean> userAll();

   @RequestMapping(value = "/getInternalUser")
    List<UserBean> getUserAllInternalUser(@RequestParam("number") int numberUser);

}
