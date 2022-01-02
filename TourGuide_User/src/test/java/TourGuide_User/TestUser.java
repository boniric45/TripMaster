package TourGuide_User;

import TourGuide_User.controller.UserController;
import TourGuide_User.domain.User;
import TourGuide_User.helper.InternalTestHelper;
import TourGuide_User.services.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUser {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomeTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome User")));
    }

    @Test
    public void getAllUserTest() {

        // Given
        UserServices userServices = new UserServices();
        InternalTestHelper.setInternalUserNumber(100);
        UserController userController = new UserController(userServices);

        // When
        List<User> userList = userController.getUserAll();

        // Then
        assertEquals(100, userList.size());
    }

    @Test
    public void getReturnUserByUserNameTest() {

        // Given
        InternalTestHelper.setInternalUserNumber(4);
        UserServices userServices = new UserServices();
        UserController userController = new UserController(userServices);

        // When
        User user = userController.getUser("internalUser3");

        // Then
        assertEquals("internalUser3",user.getUserName());
    }

    @Test
    public void getReturnUserByUserNameIsNullTest() {

        // Given
        UserServices userServices = new UserServices();
        UserController userController = new UserController(userServices);

        // When
        User user = userController.getUser("internalUser103");

        // Then
        assertNull(user);

    }
}
