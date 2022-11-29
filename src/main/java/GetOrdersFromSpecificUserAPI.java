import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constants.Constants.REQUEST_GET_ORDERS;
import static io.restassured.RestAssured.given;

//GET https://stellarburgers.nomoreparties.site/api/orders
public class GetOrdersFromSpecificUserAPI extends BaseAPI{

    @Step("Получить список заказов конкретного пользователя с авторизацией")
    public ValidatableResponse getOrdersWithAuthorization (String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(REQUEST_GET_ORDERS)
                .then();
    }

    @Step("Получить список заказов конкретного пользователя без авторизации")
    public ValidatableResponse getOrdersWithOutAuthorization (){
        return given()
                .spec(requestSpecification())
                .when()
                .get(REQUEST_GET_ORDERS)
                .then();
    }
}
