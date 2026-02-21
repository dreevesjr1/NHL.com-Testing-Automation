package djia_citi;

import java.time.*;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.*;

public class DowTracker {

    public static void main(String[] args) throws InterruptedException {

        // Path to Firefox Driver
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");

        FirefoxOptions options = new FirefoxOptions();
        //direct path below
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        
        WebDriver driver = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();
        driver.get("https://finance.yahoo.com/quote/%5EDJI/");

        System.out.println("Opened Yahoo Finance page");

        while(true) {
	        try {
	            // Wait for the price element to appear
	            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.cssSelector("fin-streamer[data-symbol='^DJI'][data-field='regularMarketPrice']")
	            ));
	
	            String price = priceElement.getText();
	            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	            System.out.println("Dow Jones Price: " + price + " at time " + time);
	
	        } catch (Exception e) {
	            System.out.println("Error extracting price: " + e.getMessage());
	        }
	        
	        Thread.sleep(5000);        
	       }
    }
}

