package praktikum.methods;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import praktikum.Credentials;
import praktikum.User;

public class GeneralMethods {
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
}
