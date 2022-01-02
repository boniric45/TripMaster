package tourguide.controller;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.services.TourGuideServices;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestGpsUtilController {

    @Mock
    TourGuideServices tourGuideServices;

    @Autowired
    TourGuideController tourGuideController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    // Can only be used when TourGuide_GpsUtil
    public void getAttractionsTest() throws Exception {
        mockMvc.perform(get("/getAttractions"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Disneyland")));
    }

    @Test
    // Can only be used when TourGuide_GpsUtil and TourGuide_User is UP
    public void getLocationTest() throws Exception {
        tourGuideServices.getUserAllInternalUser(3);
        mockMvc.perform(get("/getLocation?userName=internalUser2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("latitude")))
                .andExpect(content().string(containsString("longitude")));
    }

    @Test
    public void getHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


}
