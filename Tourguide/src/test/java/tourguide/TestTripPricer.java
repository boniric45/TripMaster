package tourguide;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourguide.beans.reward.ProviderBean;
import tourguide.beans.user.UserBean;
import tourguide.beans.user.UserPreferencesBean;
import tourguide.proxies.reward.TripPricerProxy;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestTripPricer {

    @Autowired
    TripPricerProxy tripPricerProxy;

    @Test
    public void userPreferenceGetSetTestShouldCorreclySetAndGetAttrsTest() {

        // Given
        UserPreferencesBean prefs = new UserPreferencesBean();

        // When
        prefs.setAttractionProximity(10);
        prefs.setHighPricePoint(100.00);
        prefs.setLowerPricePoint(20.05);
        prefs.setNumberOfAdults(2);
        prefs.setNumberOfChildren(3);
        prefs.setTicketQuantity(5);
        prefs.setTripDuration(14);

        // Then
        assertEquals(10, prefs.getAttractionProximity());
        assertEquals(100.00, prefs.getHighPricePoint());
        assertEquals(20.05, prefs.getLowerPricePoint());
        assertEquals(2, prefs.getNumberOfAdults());
        assertEquals(3, prefs.getNumberOfChildren());
        assertEquals(5, prefs.getTicketQuantity());
        assertEquals(14, prefs.getTripDuration());

    }

    @Test
    public void tripPricerTestShouldReturnSomeOffersTest() {

        // Given
        UserPreferencesBean prefs = new UserPreferencesBean();
        UserBean user = new UserBean(UUID.randomUUID(), "test", "0000", "test@test.com");

        // When
        prefs.setAttractionProximity(10);
        prefs.setHighPricePoint(100.00);
        prefs.setLowerPricePoint(20.05);
        prefs.setNumberOfAdults(2);
        prefs.setNumberOfChildren(3);
        prefs.setTicketQuantity(5);
        prefs.setTripDuration(14);
        user.setUserPreferences(prefs);

        List<ProviderBean> providers = tripPricerProxy.getPrice("", user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
         user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), 10);

        // Then
        assertEquals(5, providers.size());

    }
}
