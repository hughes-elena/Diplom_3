package ru.praktikums.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage {
    public static final String URL_PAGE = "https://stellarburgers.nomoreparties.site/";

    public MainPage(WebDriver driver) {

        super(driver);
    }

    // ЛОКАТОРЫ
    //Кнопка Войти в аккаунт
    private By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    // Кнопка Личный кабинет
    private By profileButton = By.xpath(".//a[@href='/account']");
    // Кнопка Оформить заказ
    private By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    // раздел Булки
    private By sectionBun = By.xpath(".//span[text()='Булки']");
    // раздел Соусы
    private By sectionSauce = By.xpath(".//span[text()='Соусы']");
    // раздел Начинки
    private By sectionTopping = By.xpath(".//span[text()='Начинки']");

    //Метод ожидания выносим отдельно для уменьшения кода
    private WebElement waitForElement(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(URL_PAGE);
    }

    @Step("Клик по кнопке Войти в аккаунт")
    public void clickLoginButton() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(loginButton).click();
    }

    @Step("Клик по кнопке Личный кабинет")
    public void clickProfileButton() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(profileButton).click();
    }

    @Step("Клик по кнопке Оформить заказ")
    public void clickCreateOrderButton() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(createOrderButton).click();
    }

    @Step("Клик по разделу Булки")
    public void clickSectionBun() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(sectionBun).click();
    }

    @Step("Клик по разделу Соусы")
    public void clickSectionSauce() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(sectionSauce).click();
    }

    @Step("Клик по разделу Начинки")
    public void clickSectionTopping() {
        waitForModalToDisappear();  // вызов метода из BasePage
        waitForElement(sectionTopping).click();
    }

    // Проверка отображения кнопки перехода в Личный кабинет
    @Step("Проверка отображения кнопки Личный кабинет")
    public boolean profileButtonIsDisplayed() {
        waitForModalToDisappear();  // вызов метода из BasePage
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(profileButton))
                .isDisplayed();
    }

    // Проверка отображения кнопки Оформить заказ
    @Step("Проверка отображения кнопки Оформить заказ")
    public boolean createOrderButtonIsDisplayed() {
        waitForModalToDisappear();  // вызов метода из BasePage
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(createOrderButton))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Проверка активного раздела: {sectionName}")
    public boolean isSectionActive(String sectionName) {
        waitForModalToDisappear();
        By activeSectionLocator = By.xpath(".//div[contains(@class,'current')]/span[text()='" + sectionName + "']");
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(activeSectionLocator))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}