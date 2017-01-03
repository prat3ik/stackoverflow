package testcase;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import pageobject.StackOveflowPO;
import utils.WebDriverManager;
/**
 * This class contains test method which calculate the most asked question for tag:'qa' in current year
 * 
 * @author pratikpat
 *
 */
public class StackOveflow {

	//For current year(2017)there are no questions for tag:[qa], so the previous year (2016) is selected
	@Test
	public void verifyMostFreqeuntQuestionAskedInThisYearForPaticularTag() {
		final String tagName = "[qa]";
		final String calcaulationYear = "2016";
		StackOveflowPO po = new StackOveflowPO();
		po.navigateToStackOverflow();
		po.search(tagName);
		po.navigateToFrequentTab();
		po.show50ListItems();
		po.moveToMaxViewedQuestion(calcaulationYear);
	}

	@AfterMethod
	public void quitWebDriver(ITestResult testResult) {
		if (testResult.FAILURE == testResult.getStatus()) {
			File src = ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				// now copy the screenshot to desired location using copyFile method
				FileUtils.copyFile(src, new File("C:/selenium/error.png"));
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		WebDriverManager.getDriver().quit();
	}

}
