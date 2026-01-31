import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Scraper {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.nhl.com/stats/players");

        WebElement seasonButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-testid='season-dropdown']")
            )
        );

        seasonButton.click();

        WebElement seasonOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), '2024-25 Regular Season')]")
            )
        );

        seasonOption.click();

        System.out.println("Season changed successfully");


        driver.quit();
    }
}
