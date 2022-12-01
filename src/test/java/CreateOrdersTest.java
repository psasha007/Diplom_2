import api.client.CreateOrdersAPI;
import api.client.CreateUserAPI;
import api.ingredients.Ingredients;
import api.ingredients.IngredientsData;
import api.orders.Orders;
import api.user.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

import api.user.User;

@DisplayName("Создание заказа")
public class CreateOrdersTest {
    private User user;
    private CreateUserAPI createUser;
    private String accessToken = "default";
    private Ingredients ingredients;
    private CreateOrdersAPI createOrders;
    private List<String> ingredientList;

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        createUser = new CreateUserAPI();
        ingredients = new Ingredients();
        createOrders = new CreateOrdersAPI();
        ingredients = createOrders.getIngredients();
        ValidatableResponse responseCreateUser = createUser.сreateUser(user);
        accessToken = responseCreateUser.extract().path("accessToken").toString();
        ingredientList = new ArrayList<>();
        for (IngredientsData ord : ingredients.getData()) {
            ingredientList.add(ord.get_id());
        }
    }

    @After
    public void setDown() {
        createUser.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка - Создания заказа с авторизацией с ингредиентами")
    public void сreateOrdersWithAuthorizationTest() {
        ValidatableResponse responseCreateOrders =
                createOrders
                        .сreateOrdersWithAuthorization(accessToken, new Orders(ingredientList))
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = responseCreateOrders.extract().path("success").toString();
        String expectedName = "Антарианский био-марсианский астероидный флюоресцентный альфа-сахаридный" +
                " spicy минеральный экзо-плантаго метеоритный люминесцентный традиционный-галактический " +
                "бессмертный space краторный фалленианский бургер";
        String actualName = responseCreateOrders.extract().path("name").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Проверка - Создания заказа с авторизацией без ингредиентов")
    public void сreateOrdersWithAuthorizationAndNotIngredientsTest() {
        List<String> notIngredients = List.of("");
        createOrders
                .сreateOrdersWithAuthorization(accessToken, new Orders(notIngredients))
                .assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Проверка - Создания заказа с авторизацией с неверным хешем ингредиентов")
    public void сreateOrdersWithAuthorizationAndBadHashTest() {
        List<String> badHash = List.of("01c0c5a71d1f82001bdaaa60");
        createOrders
                .сreateOrdersWithAuthorization(accessToken, new Orders(badHash))
                .assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации с ингредиентами")
    public void сreateOrdersWithOutAuthorizationTest() {
        ValidatableResponse responseCreateOrders =
                createOrders
                        .сreateOrdersWithOutAuthorization(new Orders(ingredientList))
                        .assertThat().statusCode(SC_OK);

        String expectedSuccess = "true";
        String actualSuccess = responseCreateOrders.extract().path("success").toString();
        String expectedName = "Антарианский био-марсианский астероидный флюоресцентный альфа-сахаридный" +
                " spicy минеральный экзо-плантаго метеоритный люминесцентный традиционный-галактический " +
                "бессмертный space краторный фалленианский бургер";
        String actualName = responseCreateOrders.extract().path("name").toString();
        Assert.assertEquals(expectedSuccess, actualSuccess);
        Assert.assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации без ингредиентов")
    public void сreateOrdersWithOutAuthorizationAndNotIngredientsTest() {
        List<String> notIngredients = List.of("");
        createOrders
                .сreateOrdersWithOutAuthorization(new Orders(notIngredients))
                .assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Проверка - Создания заказа без авторизации с неверным хешем ингредиентов")
    public void сreateOrdersWithOutAuthorizationAndBadHashTest() {
        List<String> badHash = List.of("01c0c5a71d1f82001bdaaa60");
        createOrders
                .сreateOrdersWithOutAuthorization(new Orders(badHash))
                .assertThat().statusCode(SC_BAD_REQUEST);
    }
}
