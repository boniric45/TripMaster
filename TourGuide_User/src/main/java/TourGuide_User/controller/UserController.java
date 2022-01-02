package TourGuide_User.controller;

import TourGuide_User.domain.User;
import TourGuide_User.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    /**
     * Get Home
     *
     * @return String
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/")
    public String home() {
        logger.info("GET /User Home");
        return "Welcome User";
    }

    /**
     * Get All User from MicroService User
     *
     * @return List all user
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getAllUser")
    public List<User> getUserAll() {
        return userServices.getAllUsers();
    }

    /**
     * Get User from MicroService User with userName
     *
     * @param userName actual user name
     * @return User
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam("userName") String userName) {
        return userServices.getUser(userName);
    }

    /**
     * For a test
     *
     * @param numberUser Number wish user  to générate by Micro Service User
     * @return List of User générate
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getInternalUser")
    public List<User> getInternalUser(@RequestParam("number") int numberUser) {
        return userServices.getInternalUser(numberUser);
    }

}
