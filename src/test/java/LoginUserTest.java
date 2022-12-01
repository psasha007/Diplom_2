import api.client.CreateUserAPI;
import api.user.Credentials;
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

@DisplayName("Авторизация пользователя")
public class LoginUserTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        createUser = new CreateUserAPI();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка - авторизации под существующим пользователем")
    public void userCanBeLogin() {

        ValidatableResponse responseLoginUser =
                createUser.loginUser(Credentials.from(user))
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = responseLoginUser.extract().path("success").toString();
        String expectedUser = "{email=1010@yandex.ru, name=Hi_Mame}";
        String actualUser = responseLoginUser.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("Проверка - авторизации с неверным логином")
    public void userCanBeCreatedWithNeverLogin() {
        user.setEmail("1010@yandex.ru.ru.ru");
        user.setPassword("123456789");
        createUser.loginUser(Credentials.from(user))
                .assertThat().statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка - авторизации с неверным паролем")
    public void userCanBeCreatedWithOutLoginAndPassword() {
        user.setEmail("1010@yandex.ru");
        user.setPassword("987654321");
        createUser.loginUser(Credentials.from(user))
                .assertThat().statusCode(SC_UNAUTHORIZED);

    }
}
