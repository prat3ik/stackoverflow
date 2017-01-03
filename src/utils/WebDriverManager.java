package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/***
 * @DefaultDriver: Chrome v55.0.2883.87
 * @SeleniumVesrion: 3.0.1
 * 
 * @author pratikpat
 *
 */
public class WebDriverManager {

	public static WebDriver getDriver() {
		WebDriver driver = WebDriverManager.webDriver.get();
		if (driver == null) {
			driver = new ChromeDriver();
			setThreadLocalWebDriver(driver);
			getDriver().manage().window().maximize();
		}
		return webDriver.get();
	}

	public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public static void setThreadLocalWebDriver(final WebDriver driver) {
		WebDriverManager.webDriver.set(driver);
	}
}
