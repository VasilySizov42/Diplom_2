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

public class RegistrationTest {
    User userData;
    Credentials userCredentials;
    @Before
    @DisplayName("Create profile data")
    @Description("Attempt to create random profile data for a new user")
    public void createNewUserData() {
        userData = GeneralMethods.genericUser();
    }
    @Test
    @DisplayName("Check creating a user with random profile data")
    @Description("Basic test for /api/auth/register")
    public void createUserWithRandomProfileData() {
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_OK);
        Checkings.checkSuccessIsTrue(register);
    }
    @Test
    @DisplayName("Check creating a user with a duplicate profile data")
    @Description("Attempt to create a user of a previously used profile data for /api/auth/register")
    public void createCourierWithDuplicateProfileData() {
        var registerUser = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(registerUser, HTTP_OK);
        Checkings.checkSuccessIsTrue(registerUser);
        var registerOtherUser = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(registerOtherUser, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(registerOtherUser);
        Checkings.checkMessageValue(registerOtherUser, ALREADY_IN_USE);
    }
    @Test
    @DisplayName("Check creating a user without email")
    @Description("Attempt to create a user with null email for /api/auth/register")
    public void createUserWithoutEmail() {
        userData.setEmail(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without email")
    @Description("Attempt to create a user with blank email for /api/auth/register")
    public void createUserWithBlankEmail() {
        userData.setEmail("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without password")
    @Description("Attempt to create a user with null password for /api/auth/register")
    public void createUserWithoutPassword() {
        userData.setPassword(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without password")
    @Description("Attempt to create a user with blank password for /api/auth/register")
    public void createUserWithBlankPassword() {
        userData.setPassword("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without name")
    @Description("Attempt to create a user with null name for /api/auth/register")
    public void createUserWithoutName() {
        userData.setName(null);
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a user without name")
    @Description("Attempt to create a user with blank name for /api/auth/register")
    public void createUserWithBlankName() {
        userData.setName("");
        var register = UserRequests.registerUser(userData);
        Checkings.checkForStatusCode(register, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(register);
        Checkings.checkMessageValue(register, NOT_ENOUGH_DATA);
    }
    @After
    @DisplayName("Delete created user")
    @Description("Attempt to delete created user")
    public void deleteCreatedUser() {
        userCredentials = GeneralMethods.genericUserCredentials(userData);
        try {
        var login = UserRequests.loginUser(userCredentials);
        var token = GeneralMethods.getUserAccessToken(login);
        var delete = UserRequests.deleteUser(token);
        Checkings.checkForStatusCode(delete, HTTP_ACCEPTED);
            Checkings.checkSuccessIsTrue(delete);
            Checkings.checkMessageValue(delete, DELETE_USER);
        }
        catch (NullPointerException e){
            System.out.println(AUTHORIZATION_NOT_POSSIBLE);
        }
    }
}
