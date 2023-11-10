package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.constants.DataForTesting;
import praktikum.methods.GeneralMethods;
import praktikum.methods.checking.Checkings;
import praktikum.methods.requests.OrderRequests;
import praktikum.methods.requests.UserRequests;

import static java.net.HttpURLConnection.*;
import static praktikum.constants.BaseURLHandlesAndWarningsEtc.*;

public class GettingUserOrdersTest {
    User userData;
    Credentials userCredentials;
    String token;
    @Before
    @DisplayName("Create new user")
    @Description("Attempt to create a new user with random profile data")
    public void createNewUser() {
        try {
            userData = GeneralMethods.genericUser();
            var register = UserRequests.registerUser(userData);
            token = GeneralMethods.getUserAccessToken(register);
            userCredentials = GeneralMethods.genericUserCredentials(userData);
        }
        catch (Exception e){
            System.out.println(CHECK_DATA);
        }
    }
    @Test
    @DisplayName("Check getting authorised user orders")
    @Description("Attempt to get orders for authorised user for /api/orders")
    public void getAuthorisedUserOrders() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        OrderRequests.createOrderWithAuthorisation(ingredients, token);
        var gettingOrders = OrderRequests.getUserOrderWithAuthorisation(token);
        Checkings.checkForStatusCode(gettingOrders, HTTP_OK);
        Checkings.checkSuccessIsTrue(gettingOrders);
        Checkings.checkParamIsNotNull(gettingOrders, ORDERS);
    }
    @Test
    @DisplayName("Check getting unauthorised user orders")
    @Description("Attempt to get orders for unauthorised user for /api/orders")
    public void getUnauthorisedUserOrders() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        OrderRequests.createOrderWithoutAuthorisation(ingredients);
        var gettingOrders = OrderRequests.getUserOrderWithoutAuthorisation();
        Checkings.checkForStatusCode(gettingOrders, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(gettingOrders);
        Checkings.checkMessageValue(gettingOrders, NOT_AUTHORISED);
    }
    @After
    @DisplayName("Delete created user")
    @Description("Attempt to delete created user")
    public void deleteCreatedUser() {
        try {
            var delete = UserRequests.deleteUser(token);
            Checkings.checkForStatusCode(delete, HTTP_ACCEPTED);
            Checkings.checkSuccessIsTrue(delete);
            Checkings.checkMessageValue(delete, DELETE_USER);
        }
        catch (Exception e){
            System.out.println(SOMETHING_WRONG);
        }
    }
}
