package pageobject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.WebDriverManager;

public class StackOveflowPO {

	public StackOveflowPO() {
		getDriver();
		PageFactory.initElements(this.getDriver(), this);
	}

	public WebDriver getDriver() {
		return WebDriverManager.getDriver();
	}

	@FindBy(css = "[id='search']>input[type='text']")
	WebElement searchBoxField;

	@FindBy(css = "a[data-value='frequent']")
	WebElement frequentTab;

	@FindBy(css = "a[title='show 50 items per page']")
	WebElement show50Items;

	@FindBy(css = "a[rel='next']")
	WebElement nextPage;

	@FindBy(css = ".user-action-time .relativetime")
	List<WebElement> datesAndTimes;

	public void search(String keyword) {
		searchBoxField.sendKeys(keyword + Keys.ENTER);
	}

	public void navigateToFrequentTab() {
		Thread.sleep(5000);
		frequentTab.click();
	}

	public void show50ListItems() {
		show50Items.click();
	}

	public void navigateToNextPage() {
		if (nextPage.isDisplayed())
			nextPage.click();
	}

	public List<String> getDatesAndTimes() {
		List<String> datesAndTimesText = new ArrayList<String>();
		for (WebElement el : datesAndTimes) {
			datesAndTimesText.add(el.getText());
		}
		return datesAndTimesText;
	}

	public void navigateToStackOverflow() {
		getDriver().get("http://stackoverflow.com/");
	}
}
