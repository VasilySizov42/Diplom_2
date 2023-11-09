package praktikum.methods.requests;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Order;

import static praktikum.constants.BaseURLHandlesAndWarningsEtc.ORDERS_HANDLE;

public class OrderRequests {
    @Step("create an order with authorisation")
    public static ValidatableResponse createOrderWithAuthorisation(Order order, String token) {
        return RequestSpecification.scopeWithAuthorisation(token)
                .body(order)
                .when()
                .post(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
    @Step("create an order without authorisation")
    public static ValidatableResponse createOrderWithoutAuthorisation(Order order) {
        return RequestSpecification.scope()
                .body(order)
                .when()
                .post(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
}
