package praktikum.methods.requests;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Order;

import static praktikum.constants.BaseURLHandlesAndWarningsEtc.ORDERS_HANDLE;

public class OrderRequests {
    @Step("create an order with authorization")
    public static ValidatableResponse createOrderWithAuthorization(Order order, String token) {
        return RequestSpecification.scopeWithAuthorization(token)
                .body(order)
                .when()
                .post(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
    @Step("create an order without authorization")
    public static ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return RequestSpecification.scope()
                .body(order)
                .when()
                .post(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
    @Step("get authorized user orders")
    public static ValidatableResponse getUserOrderWithAuthorization(String token) {
        return RequestSpecification.scopeWithAuthorization(token)
                .when()
                .get(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
    @Step("get unauthorized user orders")
    public static ValidatableResponse getUserOrderWithoutAuthorization() {
        return RequestSpecification.scope()
                .when()
                .get(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
}
