package TourGuide_Rewards.controller;

import TourGuide_Rewards.services.RewardsServices;
import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import java.util.UUID;

@RestController
public class RewardsController {

    @Autowired
    RewardsServices rewardsServices;

    Logger logger = LoggerFactory.getLogger(RewardsController.class);

    /**
     * Return Welcome to Rewards
     * @return String
     * @author Boniface Eric
     * 06/12/2021
     */
    @GetMapping(value = "/")
    public String home(){
        logger.info("GET /home Rewards");
        return "Welcome to Rewards";
    }

    /**
     * Return an attraction reward points as Json
     * @param attractionId
     * @param userId
     * @return String
     * @author Boniface Eric
     * 06/12/2021
     */
    @GetMapping("/getAttractionRewardPoints")
    public String getAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
        logger.info("GET /getAttractionRewardPoints with param; attractionId: {}, userId: {}", attractionId, userId);
        return new Gson().toJson(rewardsServices.getAttractionRewardPoints(attractionId, userId));
    }


}
