package TourGuide_User.services;

import TourGuide_User.domain.User;
import TourGuide_User.helper.InternalTestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
public class UserServices {

    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/
    private static final String tripPricerApiKey = "test-server-api-key";
    private final Logger logger = LoggerFactory.getLogger(UserServices.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10000);
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    private final Map<String, User> internalUserMap = new HashMap<>();
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    boolean testMode = true;

    /**
     * For a test
     * <p>
     * Générated User
     *
     * @author Boniface Eric
     * 12/12/2021
     */
    private void initializeInternalUsers() {
        internalUserMap.clear(); // Clear Map
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            internalUserMap.put(userName, user);
          });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    /**
     * For a test
     *
     * @param numberUser Get Number user générated
     * @return List of User générated
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<User> getInternalUser(int numberUser) {
        InternalTestHelper.setInternalUserNumber(numberUser);
        initializeInternalUsers();
        return new ArrayList<>(new ArrayList<>(internalUserMap.values()));
    }

    /**
     * Executor Service Thread
     *
     * @return executor service
     * @author Boniface Eric
     * 12/12/2021
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Get All Users
     *
     * @return List of User
     * @author Boniface Eric
     * 12/12/2021
     */
    public List<User> getAllUsers() {
        initializeInternalUsers(); // generate User
        return new ArrayList<>(new ArrayList<>(internalUserMap.values()));
    }

    /**
     * Get User by user name
     *
     * @param userName Get Number user générated
     * @return User
     * @author Boniface Eric
     * 12/12/2021
     */
    public User getUser(String userName) {
        initializeInternalUsers(); // generate User
        return internalUserMap.get(userName);
    }

}
