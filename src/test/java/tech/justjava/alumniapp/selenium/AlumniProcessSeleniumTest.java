package tech.justjava.alumniapp.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlumniProcessSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testStartProcess() {
        driver.get("http://localhost:" + port + "/alumni/request-form");

        WebElement alumniId = driver.findElement(By.id("alumniId"));
        alumniId.sendKeys("123");

        WebElement documentType = driver.findElement(By.id("documentType"));
        documentType.sendKeys("transcript");

        WebElement paymentMethod = driver.findElement(By.id("paymentMethod"));
        paymentMethod.sendKeys("remita");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Verify the process started successfully
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assertEquals("Document request submitted successfully", successMessage.getText());
    }

    @Test
    public void testCompleteTask() {
        driver.get("http://localhost:" + port + "/alumni/tasks");

        WebElement taskLink = driver.findElement(By.linkText("Complete Task"));
        taskLink.click();

        // Verify the task was completed successfully
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assertEquals("Task completed successfully", successMessage.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
