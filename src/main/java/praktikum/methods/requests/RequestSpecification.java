package praktikum.methods.requests;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static praktikum.constants.BaseURLHandlesAndWarningsEtc.STELLAR_BURGERS;

public class RequestSpecification  {
    @Step("connect to resource")
    public static io.restassured.specification.RequestSpecification scope() {
        return  given().log().method()
                .contentType(ContentType.JSON)
                .baseUri(STELLAR_BURGERS)
                ;
    }

    @Step("connect to resource with authorization")
    public static io.restassured.specification.RequestSpecification scopeWithAuthorization(String token) {
        return  given().log().method()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(STELLAR_BURGERS)
                ;
    }
}
