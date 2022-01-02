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
public class TestGuideController {

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
                .andExpect(jsonPath("$[0].lastLocation", notNullValue()))
        ;
    }

//    @Test
//    public void testGetUserPreferences_shouldReturnUserPreferencesAsJson() throws Exception {
//        mockMvc.perform(get("/preferences/internalUser0"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userName", is("internalUser0")))
//                .andExpect(jsonPath("$.attractionProximity", is(Integer.MAX_VALUE)))
//                .andExpect(jsonPath("$.currency", is("USD")))
//                .andExpect(jsonPath("$.lowerPricePoint", is(0.0)))
//                .andExpect(jsonPath("$.highPricePoint", is(Double.valueOf(Integer.MAX_VALUE))))
//                .andExpect(jsonPath("$.tripDuration", is(1)))
//                .andExpect(jsonPath("$.ticketQuantity", is(1)))
//                .andExpect(jsonPath("$.numberOfAdults", is(1)))
//                .andExpect(jsonPath("$.numberOfChildren", is(0)));
//    }
//
//    @Test
//    public void testPutUserPreferences_shouldCorreclyUpdateChanges() throws Exception {
//
//        UserPreferenceDto userPrefDTO = new UserPreferenceDto();
//        userPrefDTO.setUserName("internalUser0");
//        userPrefDTO.setCurrency("EUR");
//        userPrefDTO.setAttractionProximity(20);
//        userPrefDTO.setHighPricePoint(200.);
//        // Willingly let this attribute unchanged to check if it remain unchanged after putting other changes
//        userPrefDTO.setLowerPricePoint(10.);
//        userPrefDTO.setNumberOfAdults(2);
//        userPrefDTO.setNumberOfChildren(3);
//        userPrefDTO.setTicketQuantity(5);
//        userPrefDTO.setTripDuration(6);
//
//        String serialized = new ObjectMapper().writeValueAsString(userPrefDTO);
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/preferences")
//                        .content(serialized)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userName", is("internalUser0")))
//                .andExpect(jsonPath("$.attractionProximity", is(20)))
//                .andExpect(jsonPath("$.currency", is("EUR")))
//                .andExpect(jsonPath("$.lowerPricePoint", is(10.0)))
//                .andExpect(jsonPath("$.highPricePoint", is(Double.valueOf(Integer.MAX_VALUE))))
//                .andExpect(jsonPath("$.tripDuration", is(6)))
//                .andExpect(jsonPath("$.ticketQuantity", is(5)))
//                .andExpect(jsonPath("$.numberOfAdults", is(2)))
//                .andExpect(jsonPath("$.numberOfChildren", is(3)));
//    }


}
