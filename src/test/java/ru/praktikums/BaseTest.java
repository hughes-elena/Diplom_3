package ru.praktikums;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    private String browser = System.getProperty("browser", "chrome");
    private By modalOverlay = By.className("Modal_modal_overlay__x2ZCr");

    public void waitForModalToDisappear() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
    }
    @Before
    public void setUp() {
        if (browser.equals("yandex")) {
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Users\\elena\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            WebDriverManager.chromedriver().driverVersion("134.0.6998.17").setup();
            // WebDriverManager.chromedriver().setup(); // Для Chrome
            driver = new ChromeDriver(options);// Для Chrome
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        // Настройки драйвера
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
