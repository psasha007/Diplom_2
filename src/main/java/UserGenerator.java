import org.apache.commons.lang3.RandomStringUtils;
public class UserGenerator {
    public static User getDefault(){
        return new User("1010@yandex.ru", "123456789", "Hi_Mame");
    }
    public static User getRandom(int lEmail, int lpassword, int lName){
        return new User(
                RandomStringUtils.randomAlphanumeric(lEmail) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(lpassword),
                RandomStringUtils.randomAlphanumeric(lName)
        );
    }
}
