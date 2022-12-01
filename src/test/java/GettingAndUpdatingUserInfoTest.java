import api.client.CreateUserAPI;
import api.client.GettingAndUpdatingUserInfoAPI;
import api.user.User;
import api.user.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@DisplayName("Получение и обновление информации о пользователе")
public class GettingAndUpdatingUserInfoTest {

    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private GettingAndUpdatingUserInfoAPI userDataWithAuthorization;

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        createUser = new CreateUserAPI();
        userDataWithAuthorization = new GettingAndUpdatingUserInfoAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка - Получение информации о данных пользователя с авторизацией")
    public void user() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.infoUserDataWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedUser = "{email=1010@yandex.ru, name=Hi_Mame}";
        String actualUser = infoUserData.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Проверка - Изменения данных пользователя с авторизацией")
    public void userDataСhangingWithAuthorization() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.changingUserDataWithAuthorization(accessToken)
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedUser = "{email=1010@yandex.ru, name=Hi_Mame}";
        String actualUser = infoUserData.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Проверка - Изменение данных пользователя без авторизации")
    public void userDataСhangingWithOutAuthorization() {
        ValidatableResponse infoUserData =
                userDataWithAuthorization.changingUserDataWithOutAuthorization()
                        .assertThat().statusCode(SC_UNAUTHORIZED);

        String expectedSuccess = "false";
        String actualSuccess = infoUserData.extract().path("success").toString();
        String expectedMessage = "You should be authorised";
        String actualMessage = infoUserData.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
