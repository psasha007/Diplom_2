import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

@DisplayName("Создание пользователя")
public class CreateUserTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        createUser = new CreateUserAPI();
    }

    @After
    public void setDown(){
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка - создания уникального пользователя")
    public void userCanBeCreated() {
        ValidatableResponse responseCreateUser =
                createUser.сreateUser(user)
                        .assertThat().statusCode(SC_OK);
        accessToken = responseCreateUser.extract().path("accessToken").toString();

        String expectedSuccess = "true";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedUser = "{email=1010@yandex.ru, name=Hi_Mame}";
        String actualUser = responseCreateUser.extract().path("user").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedUser, actualUser);

//        ValidatableResponse responseLoginUser = createUser.loginUser(Credentials.from(user));
//        accessToken = responseLoginUser.extract().path("accessToken").toString();
    }

    @Test
    @DisplayName("Проверка - создания пользователя, который уже зарегистрирован")
    public void userCanBeCreatedIsAlreadyRegistered(){
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();

        responseCreateUser =
                createUser.сreateUser(user)
                .assertThat().statusCode(SC_FORBIDDEN);

        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "User already exists";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Проверка - создание пользователя и не заполнить одно из обязательных полей например пароль")
    public void userCanBeCreatedLeaveOneRequiredFields(){
        user.setPassword("");
        ValidatableResponse responseCreateUser =
                createUser.сreateUser(user)
                        .assertThat().statusCode(SC_FORBIDDEN);;

        String expectedSuccess = "false";
        String actualSuccess = responseCreateUser.extract().path("success").toString();
        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = responseCreateUser.extract().path("message").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
