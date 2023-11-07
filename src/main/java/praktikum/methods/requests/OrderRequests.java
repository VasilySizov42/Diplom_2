package praktikum.methods.requests;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Order;

import static praktikum.constants.BaseURLHandlesAndWarnings.ORDERS_HANDLE;

public class OrderRequests {
    @Step("create an order")
    public static ValidatableResponse createOrder(Order order, String token) {
        return RequestSpecification.scopeWithAuthorisation(token)
                .body(order)
                .when()
                .post(ORDERS_HANDLE)
                .then().log().body()
                ;
    }
}
