package ru.praktikums.pageobject;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage {
    // Имя
    private By nameField = By.xpath("//label[text()='Имя']/following-sibling::input[@name='name']");
    // Email
    private By emailField = By.xpath("//label[text()='Email']/following-sibling::input");;
    // Пароль
    private By passwordField = By.xpath(".//input[@type='password']");
    // Кнопка Зарегистрироваться
    private By registrButton = By.xpath(".//button[text()='Зарегистрироваться']");
    // Ошибка Некорректный пароль
    private By wrongPasswordText = By.xpath(".//p[text()='Некорректный пароль']");
    //Кнопка Войти
    private By loginButtonRegisterPage = By.xpath(".//a[@href='/login']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Регистрация пользователя")
    public void registration(String name, String email, String password) {
        waitForModalToDisappear();
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(registrButton));
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registrButton).click();
    }

    @Step("Проверка отображения ошибки о неправильном пароле")
    public boolean wrongPasswordTextIsDisplayed() {
        waitForModalToDisappear();
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(wrongPasswordText))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Нажатие на кнопку Войти
    @Step("Клик по кнопке Войти")
    public void loginButtonClickRegisterPage() {
        waitForModalToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonRegisterPage));

        try {
            driver.findElement(loginButtonRegisterPage).click();
        } catch (ElementClickInterceptedException e) {
            // Если клик блокируется, используем JS клик
            WebElement element = driver.findElement(loginButtonRegisterPage);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}