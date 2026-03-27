package pck2;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test_webdriver_NHL_stats {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.gecko.driver", "C://geckodriver.exe");
		
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		driver.manage().window().maximize();
		driver.get("https://www.nhl.com/stats/skaters");
		
		List<WebElement> rows =
		        driver.findElements(By.cssSelector("table tbody tr"));

		boolean result = testTableLoads(driver);
		System.out.println("TEST: Table Load → " + (result ? "PASS" : "FAIL"));
		
	}

	
	public static boolean testTableLoads(WebDriver driver) {
	    try {
	        WebElement table = driver.findElement(By.cssSelector("table"));
	        return table.isDisplayed();
	    } catch (Exception e) {
	        System.out.println("ERROR: Table not found");
	        return false;
	    }
	}
}
