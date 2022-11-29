import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

@DisplayName("Получение заказов конкретного пользователя")
public class GetOrdersFromSpecificUserTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private GetOrdersFromSpecificUserAPI getOrdersList;

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        createUser = new CreateUserAPI();
        getOrdersList = new GetOrdersFromSpecificUserAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown(){
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка - Получения списка заказов конкретного пользователя с авторизацией")
    public void getUserListOrdersWithAuthorizationTest() {
        ValidatableResponse responseCreateUser =
                getOrdersList.getOrdersWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);
        String expectedSuccess = "true";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
    }

    @Test
    @DisplayName("Проверка - Получения списка заказов конкретного пользователя без авторизации")
    public void getUserListOrdersWithOutAuthorizationTest() {
        ValidatableResponse responseCreateUser =
                getOrdersList.getOrdersWithOutAuthorization()
                        .assertThat().statusCode(SC_UNAUTHORIZED);

        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "You should be authorised";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
