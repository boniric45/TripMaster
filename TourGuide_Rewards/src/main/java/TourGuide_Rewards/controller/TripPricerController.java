package TourGuide_Rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripPricerController {

    Logger logger = LoggerFactory.getLogger(TripPricerController.class);

    @GetMapping(value = "/tripPricer")
    public String homeTripPricer(){
        logger.info("GET /home TripPricer");
        return "Welcome to TripPricer";
    }
}
