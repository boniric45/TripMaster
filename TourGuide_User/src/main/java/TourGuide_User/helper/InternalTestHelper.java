package TourGuide_User.helper;

public class InternalTestHelper {

    private static int internalUserNumber=0;

    /**
     * For a test
     *
     *  Get user générated
     * @return List of User générate
     * @author Boniface Eric
     * 12/12/2021
     */
    public static int getInternalUserNumber() {
        return internalUserNumber;
    }

    /**
     * For a test
     *
     *  Set user wish to générate
     * @author Boniface Eric
     * 12/12/2021
     */
    public static void setInternalUserNumber(int UserNumber) {
        internalUserNumber = UserNumber;
    }
}
