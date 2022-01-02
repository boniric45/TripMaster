package TourGuide_Rewards.services;

import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;

@Service
public class TripPricerServices {
    private TripPricer tripPricer = new TripPricer();

    // Constructor
    public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) {
        return tripPricer.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
    }

    /**
     * Return a random provider name using tripPricer API
     *
     * @param apiKey
     * @param adults
     * @return String
     * @author Boniface Eric
     * 06/12/2021
     */
    public String getProviderName(String apiKey, int adults) {
        return tripPricer.getProviderName(apiKey, adults);
    }

}
