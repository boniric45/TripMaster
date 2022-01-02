package TourGuide_Rewards.controller;

import TourGuide_Rewards.services.TripPricerServices;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

@RestController
public class TripPricerController {

    @Autowired
    TripPricerServices tripPricerServices;

    Logger logger = LoggerFactory.getLogger(TripPricerController.class);

    // Constructor
    public TripPricerController(TripPricerServices tripPricerServices) {
        this.tripPricerServices = tripPricerServices;
    }

    /**
     * @return String
     * @author Boniface Eric
     * 06/12/2021
     */
    @GetMapping(value = "/tripPricer")
    public String homeTripPricer() {
        logger.info("GET /home TripPricer");
        return "Welcome to TripPricer";
    }

    /**
     * Return a list of providers prices using tripPricer API
     *
     * @param apiKey
     * @param attractionId
     * @param adults
     * @param children
     * @param nightsStay
     * @param rewardsPoints
     * @return List<Provider>
     * @author Boniface Eric
     * 06/12/2021
     */
    @GetMapping("/getPrice")
    public List<Provider> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId,
                                   @RequestParam int adults, @RequestParam int children,
                                   @RequestParam int nightsStay, @RequestParam int rewardsPoints) {

        logger.info("GET /getPrice with param: apiKey: {}, attractionId: {}, adults: {}, children: {}, nightsStay:{}, rewardsPoints: {}",
                apiKey, attractionId, adults, children, nightsStay, rewardsPoints);

        return tripPricerServices.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    /**
     * Return a random provider name as Json
     *
     * @param apiKey
     * @param adults
     * @return String provider name
     * @author Boniface Eric
     * 06/12/2021
     */
    @GetMapping("/getProviderName")
    public String getProviderName(@RequestParam String apiKey, @RequestParam int adults) {

        logger.info("GET /getPrice with param: apiKey:{}, adults: {}", apiKey, adults);
        String provider = tripPricerServices.getProviderName(apiKey, adults);

        return new Gson().toJson(provider);
    }


}
