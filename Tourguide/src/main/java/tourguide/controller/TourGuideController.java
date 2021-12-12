package tourguide.controller;

import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.beans.user.UserBean;
import tourguide.proxies.gpsutil.GpsUtilProxy;
import tourguide.proxies.reward.RewardProxy;
import tourguide.proxies.user.UserProxy;
import tourguide.services.TourGuideService;

import java.util.List;

@RestController
public class TourGuideController {

    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @Autowired
    UserProxy userProxy;

    @Autowired
    RewardProxy rewardProxy;

    @Autowired
    TourGuideService tourGuideService;

    private Logger logger = LoggerFactory.getLogger(TourGuideController.class);

    @GetMapping(value = "/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    // Gpsutil
    @GetMapping("/getLocation")
    public String getLocation(@RequestParam("userName") String userName) {
        VisitedLocationBean visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return JsonStream.serialize(visitedLocation.location);
    }

    @GetMapping(value = "/getAttractions")
    public List<AttractionBean> home() {
        return gpsUtilProxy.getAttractions();
    }


    // User
    @GetMapping(value = "/getUser")
    public UserBean getUser(@RequestParam("userName") String userName) {
        return tourGuideService.getUser(userName);
    }

    @GetMapping(value = "/userAll")
    public List<UserBean> getUserAll() {
        return tourGuideService.getAllUser();
    }

    @GetMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        return JsonStream.serialize(tourGuideService.getAllUserLastVisitedLocation());
    }

    @GetMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {
        return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }

    //  TODO: Change this method to no longer return a List of Attractions.
//    //  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
//    //  Return a new JSON object that contains:
//    // Name of Tourist attraction,
//    // Tourist attractions lat/long,
//    // The user's location lat/long,
//    // The distance in miles between the user's location and each of the attractions.
//    // The reward points for visiting each Attraction.
//    //    Note: Attraction reward points can be gathered from RewardsCentral
//    @RequestMapping("/getNearbyAttractions")
//    public String getNearbyAttractions(@RequestParam String userName) throws ExecutionException, InterruptedException {
//        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
//        return JsonStream.serialize(tourGuideService.getNearByAttractions(visitedLocation));
//    }
//


//
//    @RequestMapping("/getTripDeals")
//    public String getTripDeals(@RequestParam String userName) {
//        List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
//        return JsonStream.serialize(providers);
//    }
//


}
