package pck1.tests;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public static void main(String[] args) {
        new SeleniumTest().start();
    }

    public void start() {
        setupDriver();
        scrapeSeasons();
        teardown();
    }

    private void setupDriver() {
    	System.setProperty(
    		    "webdriver.chrome.driver",
    		    "C:\\chromedriver.exe"
    		);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        driver.get("https://www.nhl.com/stats/player");
    }

    private void scrapeSeasons() {
        String[] seasons = {
                "2025-26 Regular Season",
                "2024-25 Regular Season",
                "2023-24 Regular Season",
                "2022-23 Regular Season",
                "2021-22 Regular Season",
                "2020-21 Regular Season"
        };

        WebElement seasonDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                		By.xpath("//button[contains(text(), '2024-2025 Regular Seaon')]")
                		//By.cssSelector("button[data-testid = 'season-dropdown']")
                		)
    
                		//By.tagName("select"))
        );

        Select select = new Select(seasonDropdown);

        for (String season : seasons) {
            System.out.println("\n=================================================");
            System.out.println(" SCRAPING SEASON: " + season);
            System.out.println("=================================================");

            select.selectByVisibleText(season);
            waitForTableRefresh();

            int rowsScraped = scrapeTable(season);

            System.out.println(">>> Total players scraped for " + season + ": " + rowsScraped);
        }
    }

    private void waitForTableRefresh() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("tbody tr")
        ));
    }

    private int scrapeTable(String season) {
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        int count = 0;

        // Print column header once per season
        System.out.printf(
                "%-25s %-3s %-3s %-3s %-3s %-3s%n",
                "PLAYER", "POS", "GP", "G", "A", "PTS"
        );
        System.out.println("-------------------------------------------------");

        for (WebElement row : rows) {
            try {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() < 7) continue;

                String name     = cells.get(1).getText();
                String position = cells.get(2).getText();
                int gp          = parseInt(cells.get(3).getText());
                int goals       = parseInt(cells.get(4).getText());
                int assists     = parseInt(cells.get(5).getText());
                int points      = parseInt(cells.get(6).getText());

                System.out.printf(
                        "%-25s %-3s %-3d %-3d %-3d %-3d%n",
                        name, position, gp, goals, assists, points
                );

                count++;

                // Optional: limit output to avoid console spam
                if (count == 15) {
                    System.out.println("... (output truncated)");
                    break;
                }

            } catch (StaleElementReferenceException e) {
                // Table updated mid-loop — skip row
            }
        }
        return count;
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
