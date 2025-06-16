package ru.praktikums.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage extends BasePage {

    private final By loginButton = By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик по кнопке 'Войти' на странице восстановления пароля")
    public void loginButtonClick() {
        waitForModalToDisappear();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }
}
