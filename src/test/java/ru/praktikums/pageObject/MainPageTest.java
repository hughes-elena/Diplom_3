package ru.praktikums.pageObject;

import static org.junit.Assert.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikums.pageObject.MainPage;
import ru.praktikums.BaseTest;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class MainPageTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу Начинки")
    @Description("Проверка возможности перехода к разделу Начинки на главной странице")
    public void switchingToSectionTopping() {
        driver.get(MainPage.URL_PAGE);
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSectionTopping();
        assertTrue("Раздел Начинки не стал активным", mainPage.isSectionActive("Начинки"));
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    @Description("Проверка возможности перехода к разделу Соусы на главной странице")
    public void switchingToSectionSauce() {
        driver.get(MainPage.URL_PAGE);
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSectionTopping(); // Прокрутка вниз
        mainPage.clickSectionSauce();

        assertTrue("Раздел Соусы не стал активным", mainPage.isSectionActive("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    @Description("Проверка возможности перехода к разделу Булки на главной странице")
    public void switchingToSectionBun() {
        driver.get(MainPage.URL_PAGE);
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSectionTopping(); // Прокрутка вниз
        mainPage.clickSectionBun();

        mainPage.clickSectionBun();
        assertTrue("Раздел Булки не стал активным", mainPage.isSectionActive("Булки"));
    }
}