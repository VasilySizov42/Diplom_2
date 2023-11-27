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
    String token;
    @Before
    @DisplayName("Create new user")
    @Description("Attempt to create a new user with random profile data")
    public void createNewUser() {
        try {
            userData = GeneralMethods.genericUser();
            var register = UserRequests.registerUser(userData);
            token = GeneralMethods.getUserAccessToken(register);
        }
        catch (Exception e){
            System.out.println(CHECK_DATA);
        }
    }
    @Test
    @DisplayName("Check getting authorized user orders")
    @Description("Attempt to get orders for authorized user for /api/orders")
    public void getAuthorizedUserOrders() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        OrderRequests.createOrderWithAuthorization(ingredients, token);
        var gettingOrders = OrderRequests.getUserOrderWithAuthorization(token);
        Checkings.checkForStatusCode(gettingOrders, HTTP_OK);
        Checkings.checkSuccessIsTrue(gettingOrders);
        Checkings.checkParamIsNotNull(gettingOrders, ORDERS);
    }
    @Test
    @DisplayName("Check getting unauthorized user orders")
    @Description("Attempt to get orders for unauthorized user for /api/orders")
    public void getUnauthorizedUserOrders() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        OrderRequests.createOrderWithoutAuthorization(ingredients);
        var gettingOrders = OrderRequests.getUserOrderWithoutAuthorization();
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
