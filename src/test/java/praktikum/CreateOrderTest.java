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

public class CreateOrderTest {
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
    @DisplayName("Check creating an order with ingredients and authorisation")
    @Description("Attempt to create an order for authorised user with ingredients for /api/orders")
    public void createOrderForAuthUserWithIngredients() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        var order = OrderRequests.createOrderWithAuthorisation(ingredients, token);
        Checkings.checkForStatusCode(order, HTTP_OK);
        Checkings.checkSuccessIsTrue(order);
        Checkings.checkParamIsNotNull(order, ORDER);
    }
    @Test
    @DisplayName("Check creating an order with ingredients and authorisation")
    @Description("Attempt to create an order for authorised user without ingredients for /api/orders")
    public void createOrderForAuthUserWithoutIngredients() {
        var ingredients = new Order();
        var order = OrderRequests.createOrderWithAuthorisation(ingredients, token);
        Checkings.checkForStatusCode(order, HTTP_BAD_REQUEST);
        Checkings.checkSuccessIsFalse(order);
        Checkings.checkMessageValue(order, INSUFFICIENT_ORDER_DATA);
    }
    @Test
    @DisplayName("Check creating an order with wrong ingredients hash and authorisation")
    @Description("Attempt to create an order for authorised user with wrong ingredients hash for /api/orders")
    public void createOrderForAuthUserWithWrongIngredientsHash() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_WRONG);
        var order = OrderRequests.createOrderWithAuthorisation(ingredients, token);
        Checkings.checkForStatusCode(order, HTTP_INTERNAL_ERROR);
    }
    @Test
    @DisplayName("Check creating an order with ingredients and without authorisation")
    @Description("Attempt to create an order for unauthorised user with ingredients for /api/orders")
    public void createOrderWithIngredients() {
        var ingredients = new Order(DataForTesting.INGREDIENTS_1);
        var order = OrderRequests.createOrderWithoutAuthorisation(ingredients);
        Checkings.checkForStatusCode(order, HTTP_OK);
        Checkings.checkSuccessIsTrue(order);
        Checkings.checkParamIsNotNull(order, ORDER);
    }
    @Test
    @DisplayName("Check creating an order without ingredients and without authorisation")
    @Description("Attempt to create an order for unauthorised user without ingredients for /api/orders")
    public void createOrderWithoutIngredients() {
        var ingredients = new Order();
        var order = OrderRequests.createOrderWithoutAuthorisation(ingredients);
        Checkings.checkForStatusCode(order, HTTP_BAD_REQUEST);
        Checkings.checkSuccessIsFalse(order);
        Checkings.checkMessageValue(order, INSUFFICIENT_ORDER_DATA);
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
