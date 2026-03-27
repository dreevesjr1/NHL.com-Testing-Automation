package pck2;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.*;

public class NHL_stats_players {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.gecko.driver", "C://geckodriver.exe");
		
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		driver.manage().window().maximize();
		driver.get("https://www.nhl.com/stats/skaters");
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(
		        By.cssSelector("table tbody tr")));
		
	    List<WebElement> rows =
	        driver.findElements(By.cssSelector("table tbody tr"));

	    //Goes in for loop to scrape player info
	    boolean allValid = true;

	    for (WebElement row : rows) {

	        List<WebElement> cols = row.findElements(By.tagName("td"));

	        if (cols.size() < 9) {
	            System.out.println("WARN: Skipping row with insufficient columns");
	            continue;
	        }

	        String name = cols.get(1).getText();
	        String team = cols.get(3).getText();
	        String gp = cols.get(6).getText();
	        String g = cols.get(7).getText();
	        String a = cols.get(8).getText();

	        // ✅ Validation checks
	        if (name.isEmpty()) {
	            System.out.println("FAIL: Player name is empty");
	            allValid = false;
	        }

	        if (!gp.matches("\\d+")) {
	            System.out.println("FAIL: GP is not numeric for " + name);
	            allValid = false;
	        }

	        if (!g.matches("\\d+")) {
	            System.out.println("FAIL: Goals not numeric for " + name);
	            allValid = false;
	        }

	        if (!a.matches("\\d+")) {
	            System.out.println("FAIL: Assists not numeric for " + name);
	            allValid = false;
	        }

	        // Optional: still print data
	        System.out.printf("%s | %s | GP: %s | Goals: %s | Assists %s%n",
	                name, team, gp, g, a);
	    

	    }
		
	}
	public static void urlLoop(WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		for(int page=1; page<=18; page++) {

			String url = "https://www.nhl.com/stats/skaters?page=" + page;
		    driver.get(url);

		    wait.until(ExpectedConditions.presenceOfElementLocated(
		    	    By.cssSelector("table tbody tr")));
	}
}}


