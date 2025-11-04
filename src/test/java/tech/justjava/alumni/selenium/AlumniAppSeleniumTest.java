package tech.justjava.alumni.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlumniAppSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testStartProcess() {
        driver.get("http://localhost:8080/alumni/start");

        WebElement documentTypeSelect = driver.findElement(By.id("documentType"));
        Select documentTypeDropdown = new Select(documentTypeSelect);
        documentTypeDropdown.selectByValue("transcript");

        WebElement alumniIdInput = driver.findElement(By.id("alumniId"));
        alumniIdInput.sendKeys("ALU001");

        WebElement paymentMethodSelect = driver.findElement(By.id("paymentMethod"));
        Select paymentMethodDropdown = new Select(paymentMethodSelect);
        paymentMethodDropdown.selectByValue("paystack");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/alumni/process-started"));
        assertTrue(driver.findElement(By.tagName("body")).getText().contains("Process Started Successfully"));
    }

    @Test
    public void testCompletePaymentTask() {
        driver.get("http://localhost:8080/alumni/tasks?userId=ALU001");

        WebElement completeButton = driver.findElement(By.cssSelector("a.btn-primary"));
        completeButton.click();

        WebElement paymentStatusSelect = driver.findElement(By.id("paymentStatus"));
        Select paymentStatusDropdown = new Select(paymentStatusSelect);
        paymentStatusDropdown.selectByValue("successful");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/alumni/tasks?userId=ALU001"));
    }

    @Test
    public void testCompleteApprovalTask() {
        driver.get("http://localhost:8080/alumni/tasks?userId=registrar123");

        WebElement completeButton = driver.findElement(By.cssSelector("a.btn-primary"));
        completeButton.click();

        WebElement approvalStatusSelect = driver.findElement(By.id("approvalStatus"));
        Select approvalStatusDropdown = new Select(approvalStatusSelect);
        approvalStatusDropdown.selectByValue("approved");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/alumni/tasks?userId=registrar123"));
    }

    @Test
    public void testViewProcesses() {
        driver.get("http://localhost:8080/alumni/processes");

        WebElement table = driver.findElement(By.tagName("table"));
        assertTrue(table.isDisplayed());

        WebElement viewDetailsButton = driver.findElement(By.cssSelector("button.btn-info"));
        viewDetailsButton.click();

        WebElement modal = driver.findElement(By.cssSelector("div.modal"));
        assertTrue(modal.isDisplayed());
    }
}
