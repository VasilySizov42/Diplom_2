package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.methods.GeneralMethods;
import praktikum.methods.checking.Checkings;
import praktikum.methods.requests.UserRequests;

import static java.net.HttpURLConnection.*;
import static praktikum.constants.BaseURLHandlesAndWarningsEtc.*;
import static praktikum.constants.DataForTesting.WRONG_EMAIL;
import static praktikum.constants.DataForTesting.WRONG_PASSWORD;

public class LoginTest {
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
    @DisplayName("Check login a user with random profile data")
    @Description("Attempt to login a user with random profile data for /api/auth/login")
    public void loginCourierWithRandomName() {
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_OK);
        Checkings.checkSuccessIsTrue(login);
    }
    @Test
    @DisplayName("Check login a user with wrong email")
    @Description("Attempt to login a user with wrong email for /api/auth/login")
    public void loginCourierWitWrongEmail() {
        userCredentials.setEmail(WRONG_EMAIL);
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(login);
        Checkings.checkMessageValue(login, ACCOUNT_NOT_FOUND);
    }
    @Test
    @DisplayName("Check login a user with wrong password")
    @Description("Attempt to login a user with wrong password for /api/auth/login")
    public void loginCourierWitWrongPassword() {
        userCredentials.setPassword(WRONG_PASSWORD);
        var login = UserRequests.loginUser(userCredentials);
        Checkings.checkForStatusCode(login, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(login);
        Checkings.checkMessageValue(login, ACCOUNT_NOT_FOUND);
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
