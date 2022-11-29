import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;

// Получение и обновление информации о пользователе
public class GettingAndUpdatingUserInfoAPI extends BaseAPI{
    @Step("Информация о данных пользователя с авторизацией")
    public ValidatableResponse infoUserDataWithAuthorization (String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(REQUEST_GET_USER)
                .then();
    }
    @Step("Изменение данных пользователя с авторизацией")
    public ValidatableResponse changingUserDataWithAuthorization (String accessToken){
        return given()
                .spec(requestSpecification())
                .header("Authorization", accessToken)
                .when()
                .patch(REQUEST_PATCH_USER)
                .then();
    }
    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse changingUserDataWithOutAuthorization (){
        return given()
                .spec(requestSpecification())
                .when()
                .patch(REQUEST_PATCH_USER)
                .then();
    }
}
