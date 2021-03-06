package tourguide.controller;

import com.google.gson.Gson;
import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.reward.ProviderBean;
import tourguide.beans.user.UserBean;
import tourguide.services.TourGuideServices;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class TourGuideController {

    @Autowired
    TourGuideServices tourGuideServices;

    private Logger logger = LoggerFactory.getLogger(TourGuideController.class);

    /**
     * Get Home
     *
     * @return List of User générated
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    // Tourguide Gpsutil

    /**
     * Get Location User by Tourguide_Gpsutil
     *
     * @return Location visited
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getLocation")
    public String getLocation(@RequestParam("userName") String userName) {
        UserBean user = tourGuideServices.getUser(userName);
        VisitedLocationBean visitedLocation = tourGuideServices.getUserLocation(user);
        return JsonStream.serialize(visitedLocation.location);
    }

    /**
     * Get list of attraction by Tourguide_Gpsutil
     *
     * @return List of attraction
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getAttractions")
    public List<AttractionBean> getAttractions() {
        return tourGuideServices.getAttractions();
    }

    // Tourguide User

    /**
     * Get  User by Tourguide_User
     *
     * @return User
     * @Param userName
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getUser")
    public UserBean getUser(@RequestParam("userName") String userName) {
        return tourGuideServices.getUser(userName);
    }

    /**
     * Get  List All User by Tourguide_User
     *
     * @return List of User générated
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getAllUser")
    public List<UserBean> getUserAll() {
        return tourGuideServices.getAllUser();
    }

    /**
     * Get  User Generated by Tourguide_User
     *
     * @return List User Generated
     * @Param numberUser
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getInternalUser")
    public List<UserBean> getUserAllInternalUser(@RequestParam("number") int numberUser) {
        return tourGuideServices.getUserAllInternalUser(numberUser);
    }

    /**
     * Get  All User Current Location
     *
     * @return List<UserLastLocationDto>
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() throws ExecutionException, InterruptedException {
        return JsonStream.serialize(tourGuideServices.getAllUserLastVisitedLocation());
    }

    /**
     * Get  History User Location
     *
     * @return List<VisitedLocationBean>
     * @Param userName and numberLocation
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getHistoryUserLocation")
    public List<VisitedLocationBean> getUserHistory(@RequestParam("userName") String userName, @RequestParam("number") int numberLocation) {
        UserBean userBean = getUser(userName);
        return tourGuideServices.generateUserLocationHistory(userBean, numberLocation); }

    /**
     * Get  Full History User
     *
     * @return List<VisitedLocationBean>
     * @Param userName and numberLocation
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getUserFullHistory")
    public String getUserFull(@RequestParam("userName") String userName, @RequestParam("number") int numberLocation) {
        UserBean userBean = getUser(userName);
        List<VisitedLocationBean> visitedLocationBeanList = tourGuideServices.generateUserLocationHistory(userBean, numberLocation);
        List<String> result = new ArrayList<>();
        result.add(userBean.getUserName() + ", " + userBean.getEmailAddress() + ", " + visitedLocationBeanList + ", ");
        return JsonStream.serialize(result);
    }

    /**
     * Get  Rewards by userName
     *
     * @return List<UserRewardBean>
     * @Param userName
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping(value = "/getRewards")
    public String getRewards(@RequestParam String userName) {
        return JsonStream.serialize(tourGuideServices.getUserRewards(getUser(userName)));
    }

    /**
     * Get  Near by Attraction by userName
     *
     * @return List<AttractionsDto>
     * @Param userName
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) {
        UserBean userBean = getUser(userName);
        return JsonStream.serialize(tourGuideServices.mapClosestFiveTouristAttractionsToTheUser(userBean, 5));
    }

    /**
     * Get  Attraction Rewards Points
     *
     * @return int (Number Points)
     * @Param attractionId and userId
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getAttractionRewardPoints")
    public String getAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
        return new Gson().toJson(tourGuideServices.getAttractionRewardPoints(attractionId, userId));
    }


    // Tourguide_Rewards (TripPricer)

    /**
     * Get  Provider Name
     *
     * @return String
     * @Param apiKey and adults
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getProviderName")
    public String getProviderName(@RequestParam String apiKey, @RequestParam int adults) {
        return tourGuideServices.getProviderName(apiKey, adults);
    }

    /**
     * Get  Price
     *
     * @return List<ProviderBean>
     * @Param apiKey, attractionId, adults, children, nightsStay, rewardsPoints
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getPrice")
    public List<ProviderBean> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId,
                                       @RequestParam int adults, @RequestParam int children,
                                       @RequestParam int nightsStay, @RequestParam int rewardsPoints) {

        return tourGuideServices.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    /**
     * Get Trip Deals
     *
     * @return List<ProviderBean>
     * @Param apiKey, attractionId, adults, children, nightsStay, rewardsPoints
     * @author Boniface Eric
     * 12/12/2021
     */
    @GetMapping("/getTripDeals")
    public List<ProviderBean> getTripDeals(@RequestParam String userName, @RequestParam int numberOfAdults, @RequestParam int numberOfChildren, @RequestParam int tripDuration) {
        return tourGuideServices.getTripDeals(userName, numberOfAdults, numberOfChildren, tripDuration);
    }


}
