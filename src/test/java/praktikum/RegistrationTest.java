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

public class RegistrationTest {
    User userData;
    Credentials userCredentials;
    @Before
    public void createNewUserData() {
        try {
        userData = UserRequests.genericUser();
        }
        catch (Exception e){
            System.out.println(CHECK_DATA);
        }
    }
    @Test
    @DisplayName("Check creating a user with random profile data")
    @Description("Basic test for /api/auth/register")
    public void createUserWithRandomProfileData() {
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_OK);
        Checkings.checkParamWithValue(register, "success", true);
    }
    @Test
    @DisplayName("Check creating a user with a duplicate profile data")
    @Description("Attempt to create a user of a previously used profile data for /api/auth/register")
    public void creatingCourierWithDuplicateProfileData() {
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_OK);
        Checkings.checkParamWithValue(register, "success", true);
        var register2 = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register2, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register2, "success", false);
        Checkings.checkParamWithValue(register2, "message", ALREADY_IN_USE);
    }
    @Test
    @DisplayName("Check creating a user without email")
    @Description("Attempt to create a user with null email for /api/auth/register")
    public void creatingUserWithoutEmail() {
        userData.setEmail(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without email")
    @Description("Attempt to create a user with blank email for /api/auth/register")
    public void creatingUserWithBlankEmail() {
        userData.setEmail("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without password")
    @Description("Attempt to create a user with null password for /api/auth/register")
    public void creatingUserWithoutPassword() {
        userData.setPassword(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without password")
    @Description("Attempt to create a user with blank password for /api/auth/register")
    public void creatingUserWithBlankPassword() {
        userData.setPassword("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without name")
    @Description("Attempt to create a user with null name for /api/auth/register")
    public void creatingUserWithoutName() {
        userData.setName(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without name")
    @Description("Attempt to create a user with blank name for /api/auth/register")
    public void creatingUserWithBlankName() {
        userData.setName("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkParamWithValue(register, "success", false);
        Checkings.checkParamWithValue(register, "message", NOT_ENOUGH_DATA);
    }
    @After
    public void deleteCreatedUser() {
        userCredentials = UserRequests.genericUserCredentials(userData);
        try {
        var login = UserRequests.loginUser(userCredentials);
        var token = UserRequests.getUserAccessToken(login);
        var delete = UserRequests.deleteUser(token);
        Checkings.checkForStatusCode(delete, HTTP_ACCEPTED);
        Checkings.checkParamWithValue(delete, "success", true);
        Checkings.checkParamWithValue(delete, "message", DELETE_USER);
        }
        catch (NullPointerException e){
            System.out.println(AUTHORIZATION_NOT_POSSIBLE);
        }
    }
}
