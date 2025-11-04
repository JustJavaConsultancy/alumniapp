package tech.justjava.alumniApp.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlumniAppSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testStartProcess() {
        driver.get("http://localhost:8080/alumniApp/startProcess");

        WebElement documentTypeSelect = driver.findElement(By.id("documentType"));
        Select documentType = new Select(documentTypeSelect);
        documentType.selectByValue("transcript");

        WebElement alumniIdInput = driver.findElement(By.id("alumniId"));
        alumniIdInput.sendKeys("alumni123");

        WebElement paymentMethodSelect = driver.findElement(By.id("paymentMethod"));
        Select paymentMethod = new Select(paymentMethodSelect);
        paymentMethod.selectByValue("remita");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("Process started successfully", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    public void testCompleteTask() {
        driver.get("http://localhost:8080/alumniApp/tasks");

        WebElement taskLink = driver.findElement(By.linkText("Complete Task"));
        taskLink.click();

        assertEquals("Task completed successfully", driver.findElement(By.tagName("h1")).getText());
    }
}
