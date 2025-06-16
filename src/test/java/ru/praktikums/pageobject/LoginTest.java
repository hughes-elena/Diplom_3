package ru.praktikums.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikums.Data;
import ru.praktikums.api.UserApiSteps;
import ru.praktikums.api.UserCreateAndEditRequest;
import ru.praktikums.api.UserLoginRequest;
import ru.praktikums.BaseTest;

import static org.junit.Assert.assertTrue;

import com.github.javafaker.Faker; // для генерации случайных данных


public class LoginTest extends BaseTest {

    // Инициализируем шаги для работы с API пользователя
    private final UserApiSteps userSteps = new UserApiSteps();
    private final Faker faker = new Faker(); // Создаём объект Faker

    // Создаем случайные тестовые данные пользователя
    private String name;
    private String email;
    private String password;

    @Before // <-- Добавляем Before метод
    public void setupUser() {
        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 10); // Пароль от 6 до 10 символов

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        userSteps.createUser(userCreateRequest)
                .statusCode(200) // Ожидаем 200 или 201 при успешном создании
                .extract().response(); // Извлекаем полный ответ
        UserLoginRequest loginRequest = new UserLoginRequest(email, password);
        userSteps.loginUser(loginRequest)
                .statusCode(200); // Проверяем, что логин тоже успешен
    }

    @After
    public void cleanUp() {
        // Удаляем пользователя через API
        if (email != null && password != null) { // Проверяем, что email и password не null
            UserLoginRequest loginRequest = new UserLoginRequest(email, password);
            userSteps.userDeleteAfterLogin(loginRequest); // Этот метод должен уметь логиниться и затем удалять
        }
    }

    @Test
    @DisplayName("Вход на главной странице по кнопке Войти в аккаунт")
    @Description("Проверка возможности входа в аккаунт после нажатия на кнопку Войти в аккаунт на главной странице")
    public void loginWithCreatedUser() {
        driver.get(Data.MAIN_URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Страница логина не отображается", loginPage.loginButtonIsDisplayed());

        loginPage.login(email, password);

        assertTrue("Кнопка 'Оформить заказ' не отображается после логина",
                mainPage.createOrderButtonIsDisplayed());
    }

    @Test
    @DisplayName("Вход на главной странице по кнопке Личный кабинет")
    @Description("Проверка возможности входа в аккаунт после нажатия на кнопку Личный кабинет на главной странице")
    public void loginViaProfileButton() {
        driver.get(Data.MAIN_URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("После нажатия на кнопку не произошел редирект на страницу Входа",
                loginPage.loginButtonIsDisplayed());

        loginPage.login(email, password);

        assertTrue("Авторизация не произошла", mainPage.createOrderButtonIsDisplayed());
    }

    @Test
    @DisplayName("Вход со страницы регистрации")
    @Description("Проверка возможности входа в аккаунт после нажатия на кнопку Войти на странице регистрации")
    public void loginFromRegisterPage() {
        driver.get(Data.REGISTER_URL);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.loginButtonClickRegisterPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Редирект на страницу входа не произошел", loginPage.loginButtonIsDisplayed());

        loginPage.login(email, password);

        MainPage mainPage = new MainPage(driver);
        assertTrue("Авторизация не произошла", mainPage.createOrderButtonIsDisplayed());
    }

    @Test
    @DisplayName("Вход со страницы восстановления пароля")
    @Description("Проверка возможности входа в аккаунт после нажатия на кнопку Войти на странице восстановления пароля")
    public void loginFromForgotPasswordPage() {
        driver.get(Data.FORGOT_PASSWORD_URL); // или Data.forgotPasswordURL

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.loginButtonClick();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Редирект на страницу входа не произошел", loginPage.loginButtonIsDisplayed());

        loginPage.login(email, password);

        MainPage mainPage = new MainPage(driver);
        assertTrue("Авторизация не произошла", mainPage.createOrderButtonIsDisplayed());
    }
}