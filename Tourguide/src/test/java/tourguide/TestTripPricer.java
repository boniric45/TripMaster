package tourguide;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.reward.ProviderBean;
import tourguide.beans.user.UserBean;
import tourguide.beans.user.UserPreferencesBean;
import tourguide.proxies.reward.TripPricerProxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestTripPricer {

    @Autowired
    TripPricerProxy tripPricerProxy;

    @Test
    public void userPreferenceGetSetTest_shouldCorreclySetAndGetAttrs() {
        UserPreferencesBean prefs = new UserPreferencesBean();
        prefs.setAttractionProximity(10);
        prefs.setHighPricePoint(BigDecimal.valueOf(100d), "EUR");
        prefs.setLowerPricePoint(BigDecimal.valueOf(20.05), "EUR");
        prefs.setNumberOfAdults(2);
        prefs.setNumberOfChildren(3);
        prefs.setTicketQuantity(5);
        prefs.setTripDuration(14);

        assertEquals(10, prefs.getAttractionProximity());
//        assertEquals(100d,"EUR",prefs.setHighPricePoint());
//        assertEquals(20.05, "EUR", String.valueOf(prefs.getLowerPricePoint()));
        assertEquals(2, prefs.getNumberOfAdults());
        assertEquals(3, prefs.getNumberOfChildren());
        assertEquals(5, prefs.getTicketQuantity());
        assertEquals(14, prefs.getTripDuration());

    }

    @Test
    public void tripPricerTest_shouldReturnSomeOffers() {
//        final TripPricerBean tripPricer = Feign.builder().decoder(new GsonDecoder()).target(tourguide.beans.reward.TripPricerBean.class, TestPropertiesSocket.tripPricerSocket);

        UserPreferencesBean prefs = new UserPreferencesBean();
        prefs.setAttractionProximity(10);
//        prefs.setHighPricePoint(of(BigDecimal.valueOf(100d), "EUR"));
//        prefs.setLowerPricePoint(of(BigDecimal.valueOf(20.05), "EUR"));
        prefs.setNumberOfAdults(2);
        prefs.setNumberOfChildren(3);
        prefs.setTicketQuantity(5);
        prefs.setTripDuration(14);

        UserBean user = new UserBean(UUID.randomUUID(), "test", "0000", "test@test.com");
        user.setUserPreferences(prefs);

        List<ProviderBean> providers = tripPricerProxy.getPrice("", user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), 10);

        assertEquals(5, providers.size());
    }
}
