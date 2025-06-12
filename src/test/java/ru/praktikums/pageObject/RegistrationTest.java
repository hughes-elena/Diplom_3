package ru.praktikums.pageObject;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.praktikums.Data;
import ru.praktikums.api.UserApiSteps;
import ru.praktikums.api.UserLoginRequest;
import ru.praktikums.BaseTest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import com.github.javafaker.Faker; // для генерации случайных данных

public class RegistrationTest extends BaseTest {

    // Инициализируем шаги для работы с API пользователя
    private final UserApiSteps userSteps = new UserApiSteps();
    private final Faker faker = new Faker(); // Создаём объект Faker

    //Поля для хранения данных пользователя для удаления после теста
    private String email;
    private String password;

    @After
    public void deleteUser() {
        // Удаляем пользователя через API
        if (email != null && password != null) {
            UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
            userSteps.userDeleteAfterLogin(userLoginRequest);
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка возможности регистрации пользователя с валидными данными")
    public void successfulRegistrationTest() {

        // Создаем случайные тестовые данные пользователя
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 10); //от 6 до 10 символов
        String name = faker.name().username();

        driver.get(Data.registerURL);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registration(name, email, password);

        UserLoginRequest loginRequest = new UserLoginRequest(email, password);
        userSteps.loginUser(loginRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);
    }


    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Проверка, что при вводе пароля меньше 6 символов появляется сообщение об ошибке")
    public void registrationWithShortPasswordTest() {

        // Генерируем данные с коротким паролем
        String shortPassword = faker.internet().password(1, 5);
        String emailForShortPassword = faker.internet().emailAddress();
        String nameForShortPassword = faker.name().username();

        driver.get(Data.registerURL);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registration(nameForShortPassword, emailForShortPassword, shortPassword);

        assertTrue("Сообщение о некорректном пароле не появилось",
                registerPage.wrongPasswordTextIsDisplayed());
        email = null;
        password = null;
    }

}