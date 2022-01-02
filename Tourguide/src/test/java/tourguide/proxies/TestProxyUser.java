package tourguide.proxies;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tourguide.beans.user.UserBean;
import tourguide.proxies.user.UserProxy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProxyUser {

    @Autowired
    UserProxy userProxy;

    @Autowired
    MockMvc mockMvc;

    @Test     // Can only be used when TourGuide_User is UP
    public void getUserProxyTest() throws Exception {

        // Given
        userProxy.getUserAllInternalUser(1);
        String userName = "internalUser0";
        String emailUserName = "internalUser0@tourGuide.com";

        // When
        UserBean userBean = userProxy.getUser(userName);

        mockMvc.perform(get("http://localhost:8080/getUser?userName=" + userName))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        assertEquals(userName, userBean.getUserName());
        assertEquals(emailUserName, userBean.getEmailAddress());
    }

    @Test     // Can only be used when TourGuide_User is UP
    public void getAllUserProxyTest() throws Exception {

        // Given
        userProxy.getUserAllInternalUser(10);
        List<UserBean> userList;

        // When
        userList = userProxy.userAll();
        mockMvc.perform(get("http://localhost:8080/getAllUser"))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        assertEquals(10, userList.size());
    }

}
