package ru.praktikums.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LoginPage extends BasePage {

    // Email
    private By emailField = By.xpath(".//input[@type='text']");
    // Пароль
    private By passwordField = By.xpath(".//input[@type='password']");
    //Кнопка Войти
    private By loginButton = By.xpath(".//button[text()='Войти']");

    public LoginPage(WebDriver driver) {

        super(driver);
    }

    @Step("Вход в аккаунт")
    public void login(String email, String password) {
        waitForModalToDisappear();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    @Step("Проверка отображения кнопки Войти")


    public boolean loginButtonIsDisplayed() {
        waitForModalToDisappear();
         new WebDriverWait(driver, Duration.ofSeconds(10))
                 .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(loginButton))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
