package TourGuide_User.controller;

import TourGuide_User.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/")
    public String home(){
        logger.info("GET /User Home");
        return "Welcome User";
    }

}
