package praktikum.constants;

public class BaseURLHandlesAndWarningsEtc {
    //Handles
    public static final String STELLAR_BURGERS = "https://stellarburgers.nomoreparties.site";
    public static final String USER_REGISTER_HANDLE = "/api/auth/register";
    public static final String USER_LOGIN_HANDLE = "/api/auth/login";
    public static final String USER_LOGOUT_HANDLE = "/api/auth/logout";
    public static final String USER_CHANGE_DELETE_HANDLE = "/api/auth/user";
    public static final String PASSWORD_RESET_HANDLE = "/api/password-reset";
    public static final String PASSWORD_RESET_SAVE_HANDLE = "/api/password-reset/reset";
    public static final String INGREDIENTS_HANDLE = "/api/ingredients";
    public static final String ORDERS_HANDLE = "/api/orders";

    //Messages
    public static final String ALREADY_IN_USE = "User already exists";
    public static final String NOT_ENOUGH_DATA = "Email, password and name are required fields";
    public static final String ACCOUNT_NOT_FOUND = "email or password are incorrect";
    public static final String NOT_AUTHORISED = "You should be authorised";
    public static final String EMAIL_ALREADY_EXISTS = "User with such email already exists";
    public static final String INSUFFICIENT_ORDER_DATA = "Ingredient ids must be provided";
    public static final String DELETE_USER = "User successfully removed";
    public static final String INCORRECT_HASH = "One or more ids provided are incorrect";

    //Alerts
    public static final String CHECK_DATA = "Пользователь не был создан. Проверьте данные.";
    public static final String AUTHORIZATION_NOT_POSSIBLE = "Пользователь не был создан. Авторизация невозможна.";
    public static final String SOMETHING_WRONG = "Что-то пошло не так. Проверьте токен.";
    //Paths
    public static final String EMAIL = "user.email";
    public static final String NAME = "user.name";
    public static final String ORDER = "order";
}
