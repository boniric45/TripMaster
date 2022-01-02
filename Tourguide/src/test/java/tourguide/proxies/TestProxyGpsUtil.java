package tourguide.proxies;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.beans.gpsutil.AttractionBean;
import tourguide.beans.gpsutil.VisitedLocationBean;
import tourguide.proxies.gpsutil.GpsUtilProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProxyGpsUtil {

    @Autowired
    GpsUtilProxy gpsUtilProxy;

    @Autowired
    MockMvc mockMvc;

    @Test
    // Can only be used when TourGuide_GpsUtil is UP
    public void getGpsUtilProxyGetAttractionTest() throws Exception {

        // Given
        List<AttractionBean> attractionList = new ArrayList<>();

        // When
        mockMvc.perform(get("http://localhost:8080/getAttractions"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("Disneyland")));

        attractionList = gpsUtilProxy.getAttractions();

        // Then
        assertEquals(26, attractionList.size());
    }

    @Test
    // Can only be used when TourGuide_GpsUtil is UP
    public void getGpsUtilProxyGetLocationTest() {

        // Given
        VisitedLocationBean visitedLocationBean;

        // When
        visitedLocationBean = gpsUtilProxy.getUserLocation(UUID.randomUUID());

        // Then
        assertNotNull(visitedLocationBean.location);
    }

}
