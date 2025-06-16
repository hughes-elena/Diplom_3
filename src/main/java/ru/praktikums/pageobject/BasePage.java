package ru.praktikums.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    private By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    public BasePage(WebDriver driver) {

        this.driver = driver;
    }

    public void waitForModalToDisappear() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
    }
}