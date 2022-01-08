package tourguide.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.services.TourGuideServices;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TestTourGuideController {

    @Autowired
    TourGuideServices service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllUserCurrentLocations_shouldReturnUUIDandLocationList() throws Exception {
        service.getUserAllInternalUser(5);
        mockMvc.perform(get("/getAllCurrentLocations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId", notNullValue()))
                .andExpect(jsonPath("$[0].lastLocation", notNullValue())) ;
    }


}
