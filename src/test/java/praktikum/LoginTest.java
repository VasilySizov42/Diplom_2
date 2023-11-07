package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.checking.Checkings;
import praktikum.methods.requests.UserRequests;

import static java.net.HttpURLConnection.*;
import static praktikum.constants.BaseURLHandlesAndWarnings.*;
import static praktikum.constants.DataForTesting.WRONG_EMAIL;
import static praktikum.constants.DataForTesting.WRONG_PASSWORD;

public class LoginTest {
    User userData;
    Credentials userCredentials;
    String token;
    @Before
    public void createNewUserData() {
        try {
            userData = UserRequests.genericUser();
            var register = UserRequests.registerUser(userData);
            token = UserRequests.getUserAccessToken(register);
            userCredentials = UserRequests.genericUserCredentials(userData);
        }
        catch (Exception e){
            System.out.println(CHECK_DATA);
        }
    }
    @Test
    @DisplayName("Check login a user with random profile data")
    @Description("Attempt to login a user with random profile data for /api/auth/login")
    public void loginCourierWithRandomName() {
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_OK);
        Checkings.checkParamWithValue(login, "success", true);
    }
    @Test
    @DisplayName("Check login a user with wrong email")
    @Description("Attempt to login a user with wrong email for /api/auth/login")
    public void loginCourierWitWrongEmail() {
        userCredentials.setEmail(WRONG_EMAIL);
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_UNAUTHORIZED);
        Checkings.checkParamWithValue(login, "success", false);
        Checkings.checkParamWithValue(login, "message", ACCOUNT_NOT_FOUND);
    }
    @Test
    @DisplayName("Check login a user with wrong password")
    @Description("Attempt to login a user with wrong password for /api/auth/login")
    public void loginCourierWitWrongPassword() {
        userCredentials.setPassword(WRONG_PASSWORD);
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_UNAUTHORIZED);
        Checkings.checkParamWithValue(login, "success", false);
        Checkings.checkParamWithValue(login, "message", ACCOUNT_NOT_FOUND);
    }
    @After
    public void deleteCreatedUser() {
        try {
        var delete = UserRequests.deleteUser(token);
            Checkings.checkForStatusCode(delete, HTTP_ACCEPTED);
            Checkings.checkParamWithValue(delete, "success", true);
            Checkings.checkParamWithValue(delete, "message", DELETE_USER);
        }
        catch (Exception e){
            System.out.println(SOMETHING_WRONG);
        }
    }
}
