package TourGuide_User.controller;

import TourGuide_User.domain.User;
import TourGuide_User.services.UserServices;
import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping(value = "/")
    public String home() {
        logger.info("GET /User Home");
        return "Welcome User";
    }

    @GetMapping(value = "/userAll")
    public List<User> getUserAll() {
        return userServices.getAllUsers();
    }

    @GetMapping(value = "/getUser")
    public User getUser (@RequestParam("userName") String userName) {
        return userServices.getUser(userName);
    }

    @GetMapping(value = "/getAllCurrentLocations")
    public String getAllCurrentLocations(){return JsonStream.serialize(userServices.getAllUserLastVisitedLocation());
    }


}
