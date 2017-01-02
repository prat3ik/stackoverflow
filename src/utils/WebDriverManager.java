package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/***
 * Default Driver: Firefox
 * 
 * @author pratikpat
 *
 */
public class WebDriverManager {

	static WebDriver driver = null;

	public static WebDriver getDriver() {
		if (driver == null) {
			driver = new FirefoxDriver();
			webDriver.set(driver);
			getDriver().manage().window().maximize();
		}
		return webDriver.get();
	}

	public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

}
