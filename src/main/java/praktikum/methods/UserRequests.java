package praktikum.methods;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import praktikum.Credentials;
import praktikum.User;

import static java.lang.String.valueOf;
import static praktikum.constants.BaseURLHandlesAndWarnings.*;
import static io.restassured.RestAssured.given;
public class UserRequests {
    @Step ("getting Response via GET Request")
    public static ValidatableResponse getResponseViaGet(String handle) {
        return praktikum.methods.RequestSpecification.scope()
                .get(handle)
                .then().log().body()
                ;
    }
    @Step("register a new user")
    public static ValidatableResponse registerUser(User user) {
        return praktikum.methods.RequestSpecification.scope()
                .body(user)
                .when()
                .post(USER_REGISTER_HANDLE)
                .then().log().body()
                ;
    }
    @Step("user data generation")
    public static User genericUser() {
        return new User("Bazz" + RandomStringUtils.randomAlphanumeric(3,5) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(6,10), "Bazz");
    }
    @Step("user credentials generation")
    public static Credentials genericUserCredentials(User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
    @Step("get user access token")
    public static String getUserAccessToken(ValidatableResponse response) {
        return response
                .extract()
                .path("accessToken")
                .toString()
                ;
    }

    @Step("get user refresh token")
    public static String getUserRefreshToken(ValidatableResponse response) {
        return response
                .extract()
                .path("refreshToken")
                .toString()
                ;
    }
    @Step("login a user")
    public static ValidatableResponse loginUser(Credentials cred) {
        return praktikum.methods.RequestSpecification.scope()
                .body(cred)
                .when()
                .post(USER_LOGIN_HANDLE)
                .then().log().body()
                ;
    }
    /*@Step("logout a user")
    public static ValidatableResponse logoutUser(String token) {
        return scopeWithAuthorisation(token)
                .body("token": "{{refreshToken}}")
                .when()
                .post(USER_LOGOUT_HANDLE)
                .then().log().body()
                ;
    }*/
    @Step("delete a user")
    public static ValidatableResponse deleteUser(String token) {
         return praktikum.methods.RequestSpecification.scopeWithAuthorisation(token)
                 .when()
                 .delete(USER_CHANGE_DELETE_HANDLE)
                 .then().log().body()
                ;
    }
}
