import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class CreateOrdersAPI extends BaseAPI{
    @Step("Получить список ингредиентов")
    public Ingredients getIngredients (){
        return given()
                .spec(requestSpecification())
                .when()
                .get(REQUEST_GET_INGREDIENTS)
                .body().as(Ingredients.class);
    }
    @Step("Создание заказа с авторизацией")
    public ValidatableResponse сreateOrdersWithAuthorization (String accessToken, Orders orders){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .body(orders)
                .when()
                .post(REQUEST_POST_CREATE_ORDERS)
                .then();
    }
    @Step("Создание заказа без авторизации")
    public ValidatableResponse сreateOrdersWithOutAuthorization (Orders orders){
        return given()
                .spec(requestSpecification())
                .body(orders)
                .when()
                .post(REQUEST_POST_CREATE_ORDERS)
                .then();
    }
}
