package constants;

public class Constants {
    // Константа Url страницы Stellar Burgers
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    // Константа ручка - Создание пользователя
    public static final String  REQUEST_POST_CREATE_USER = "/api/auth/register";

    // Константа ручка - Получение данных об ингредиентах
    public static final String  REQUEST_GET_INGREDIENTS = "/api/ingredients";

    // Константа ручка - Создание заказа
    public static final String  REQUEST_POST_CREATE_ORDERS = "/api/orders";

    // Константа ручка - Восстановление и сброс пароля
    public static final String  REQUEST_POST_PASSWORD_RESET = "/api/password-reset";

    // Константа ручка - Авторизация и регистрация
    public static final String  REQUEST_POST_LOGIN_USER = "/api/auth/login";

    // Константа ручка - Выхода из системы
    public static final String  REQUEST_POST_LOGOUT_USER = "/api/auth/logout";

    // Константа ручка - Обновления токена
    public static final String  REQUEST_POST_TOKEN = "/api/auth/token";

    // Константа ручка - Получение и обновление информации о пользователе
    public static final String  REQUEST_GET_USER = "/api/auth/user";

    // Константа ручка - Обновление данных о пользователе
    public static final String  REQUEST_PATCH_USER = "/api/auth/user ";

    // Константа ручка - Удаление пользователя
    public static final String  REQUEST_DELETE_USER = "/api/auth/user";

    // Константа ручка - Получить все заказы
    public static final String  REQUEST_GET_ORDERS_ALL = "/api/orders/all";

    // Константа ручка - Получить заказы конкретного пользователя
    public static final String  REQUEST_GET_ORDERS = "/api/orders";
}
