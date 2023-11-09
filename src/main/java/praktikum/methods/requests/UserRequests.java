package praktikum.methods.requests;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Credentials;
import praktikum.User;

import static java.lang.String.valueOf;
import static praktikum.constants.BaseURLHandlesAndWarningsEtc.*;
import static io.restassured.RestAssured.given;
public class UserRequests {
    @Step ("getting Response via GET Request")
    public static ValidatableResponse getResponseViaGet(String handle) {
        return RequestSpecification.scope()
                .get(handle)
                .then().log().body()
                ;
    }
    @Step("register a new user")
    public static ValidatableResponse registerUser(User user) {
        return RequestSpecification.scope()
                .body(user)
                .when()
                .post(USER_REGISTER_HANDLE)
                .then().log().body()
                ;
    }

    @Step("login a user")
    public static ValidatableResponse loginUser(Credentials cred) {
        return RequestSpecification.scope()
                .body(cred)
                .when()
                .post(USER_LOGIN_HANDLE)
                .then().log().body()
                ;
    }
    @Step("change a user profile component with authorisation")
    public static ValidatableResponse patchComponentWithAuthorisation(User user, String token) {
        return RequestSpecification.scopeWithAuthorisation(token)
                .body(user)
                .when()
                .patch(USER_CHANGE_DELETE_HANDLE)
                .then().log().body()
                ;
    }
    @Step("patch a user profile component without authorisation")
    public static ValidatableResponse patchComponentWithoutAuthorisation(User user) {
        return RequestSpecification.scope()
                .body(user)
                .when()
                .patch(USER_CHANGE_DELETE_HANDLE)
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
         return RequestSpecification.scopeWithAuthorisation(token)
                 .when()
                 .delete(USER_CHANGE_DELETE_HANDLE)
                 .then().log().body()
                ;
    }
}
