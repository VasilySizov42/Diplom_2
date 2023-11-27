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
import static praktikum.constants.DataForTesting.*;

public class UserDataChangeTest {
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
    @DisplayName("Check change a user email")
    @Description("Attempt to change a user email with authorization for /api/auth/user")
    public void changeUserEmailWithAuthorization() {
        userData.setEmail(MODIFIED_EMAIL);
        var patchEmail = UserRequests.patchComponentWithAuthorization(userData, token);
        Checkings.checkForStatusCode(patchEmail, HTTP_OK);
        Checkings.checkSuccessIsTrue(patchEmail);
        Checkings.checkParamWithValue(patchEmail, EMAIL, MODIFIED_EMAIL);
    }
    @Test
    @DisplayName("Check change a user email to already used")
    @Description("Attempt to change a user email to already used with authorization for /api/auth/user")
    public void changeUserEmailToAlreadyUsedWithAuthorization() {
        userData.setEmail(ALREADY_USED_EMAIL);
        var patchEmail = UserRequests.patchComponentWithAuthorization(userData, token);
        Checkings.checkForStatusCode(patchEmail, HTTP_FORBIDDEN);
        Checkings.checkSuccessIsFalse(patchEmail);
        Checkings.checkMessageValue(patchEmail, EMAIL_ALREADY_EXISTS);
    }
    @Test
    @DisplayName("Check change a user email without authorization")
    @Description("Attempt to change a user email without authorization for /api/auth/user")
    public void changeUserEmailWithoutAuthorization() {
        userData.setEmail(MODIFIED_EMAIL);
        var patchEmail = UserRequests.patchComponentWithoutAuthorization(userData);
        Checkings.checkForStatusCode(patchEmail, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(patchEmail);
        Checkings.checkMessageValue(patchEmail, NOT_AUTHORISED);
    }
    @Test
    @DisplayName("Check change a user password")
    @Description("Attempt to change a user password with authorization for /api/auth/user")
    public void changeUserPasswordWithAuthorization() {
        userData.setPassword(MODIFIED_PASSWORD);
        var patchPassword = UserRequests.patchComponentWithAuthorization(userData, token);
        Checkings.checkForStatusCode(patchPassword, HTTP_OK);
        Checkings.checkSuccessIsTrue(patchPassword);
    }
    @Test
    @DisplayName("Check change a user password without authorization")
    @Description("Attempt to change a user password without authorization for /api/auth/user")
    public void changeUserPasswordWithoutAuthorization() {
        userData.setPassword(MODIFIED_PASSWORD);
        var patchPassword = UserRequests.patchComponentWithoutAuthorization(userData);
        Checkings.checkForStatusCode(patchPassword, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(patchPassword);
        Checkings.checkMessageValue(patchPassword, NOT_AUTHORISED);
    }
    @Test
    @DisplayName("Check change a user name")
    @Description("Attempt to change a user name with authorization for /api/auth/user")
    public void changeUserNameWithAuthorization() {
        userData.setName(MODIFIED_NAME);
        var patchName = UserRequests.patchComponentWithAuthorization(userData, token);
        Checkings.checkForStatusCode(patchName, HTTP_OK);
        Checkings.checkSuccessIsTrue(patchName);
        Checkings.checkParamWithValue(patchName, NAME, MODIFIED_NAME);
    }
    @Test
    @DisplayName("Check change a user name without authorization")
    @Description("Attempt to change a user name without authorization for /api/auth/user")
    public void changeUserNameWithoutAuthorization() {
        userData.setName(MODIFIED_NAME);
        var patchName = UserRequests.patchComponentWithoutAuthorization(userData);
        Checkings.checkForStatusCode(patchName, HTTP_UNAUTHORIZED);
        Checkings.checkSuccessIsFalse(patchName);
        Checkings.checkMessageValue(patchName, NOT_AUTHORISED);
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
