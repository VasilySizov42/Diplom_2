package praktikum.methods.checking;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class Checkings {
    @Step("checking the Status Code")
    public static void checkForStatusCode(ValidatableResponse response, int status) {
        response.assertThat()
                .statusCode(status)
        ;
    }

    @Step ("checking the parameter in the response has some value")
    public static void checkParamWithValue(ValidatableResponse response, String param, Object value) {
        response.assertThat()
                .body(param, is(value))
        ;
    }
    @Step ("checking the parameter \"success\" in the response is true")
    public static void checkSuccessIsTrue(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
        ;
    }
    @Step ("checking the parameter \"success\" in the response is false")
    public static void checkSuccessIsFalse(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
        ;
    }
    @Step ("checking the parameter in the response has some value")
    public static void checkMessageValue(ValidatableResponse response, Object value) {
        response.assertThat()
                .body("message", is(value))
        ;
    }
    @Step ("checking the parameter in the response is not null")
    public static void checkParamIsNotNull(ValidatableResponse response, String param) {
        response.assertThat()
                .body(param, notNullValue())
        ;
    }
}
