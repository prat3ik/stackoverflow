package pageobject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.WebDriverManager;

/**
 * This class contains Page Objects and methods for accessing web elements of
 * Stackoverflow search page
 * 
 * @author pratikpat
 *
 */

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

	public void search(String keyword) {
		searchBoxField.sendKeys(keyword + Keys.ENTER);
	}

	public void navigateToFrequentTab() {
		frequentTab.click();
	}

	public void show50ListItems() {
		show50Items.click();
	}

	private void navigateToNextPage() {
		nextPage.click();
	}

	private boolean isNextPageAvailable() {
		return isElementPresent(nextPage) && nextPage.isDisplayed();
	}

	private boolean isElementPresent(WebElement el) {
		boolean isPresent = false;
		try {
			el.isDisplayed();
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}

	List<String> datesAndTimesTextForAllPages = new ArrayList<String>();
	List<String> viewCountsForAllPages = new ArrayList<String>();
	List<String> questionLinkForAllPages = new ArrayList<String>();

	private List<WebElement> questionSummary() {
		final String questionSummaryCss = ".question-summary";
		List<WebElement> dateAndTimeElement = getDriver().findElements(By.cssSelector(questionSummaryCss));
		return dateAndTimeElement;
	}

	private List<String> getDatesAndTimesForSinglePage() {
		List<WebElement> questionSummary = questionSummary();
		List<String> datesAndTimesText = new ArrayList<String>();
		final String dateAndTimeCss = ".user-action-time .relativetime";

		if (questionSummary.size() > 0) {
			for (WebElement el : questionSummary) {
				String dateAndTimeText;
				try {
					dateAndTimeText = el.findElement(By.cssSelector(dateAndTimeCss)).getText();
				} catch (Exception e) {
					dateAndTimeText = 0 + "";
				}
				datesAndTimesText.add(dateAndTimeText);
			}
		}
		return datesAndTimesText;
	}

	private List<String> getViewCountsForSinglePage() {
		List<WebElement> questionSummary = questionSummary();
		List<String> viewCountText = new ArrayList<String>();
		final String viewsCountCss = ".statscontainer .views";

		if (questionSummary.size() > 0) {
			for (WebElement el : questionSummary) {
				viewCountText.add(el.findElement(By.cssSelector(viewsCountCss)).getText());
			}
		}
		return viewCountText;
	}

	private List<String> getQuestionLinksForSinglePage() {
		List<WebElement> questionSummary = questionSummary();
		List<String> questionLinkText = new ArrayList<String>();

		final String questionLinkCss = "h3 .question-hyperlink";
		if (questionSummary.size() > 0) {
			for (WebElement el : questionSummary) {
				questionLinkText.add(el.findElement(By.cssSelector(questionLinkCss)).getText());
			}
		}
		return questionLinkText;
	}

	private void calcaulateDataForAllPage() {
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		datesAndTimesTextForAllPages.addAll(getDatesAndTimesForSinglePage());
		viewCountsForAllPages.addAll(getViewCountsForSinglePage());
		questionLinkForAllPages.addAll(getQuestionLinksForSinglePage());
		while (isNextPageAvailable()) {
			navigateToNextPage();
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			datesAndTimesTextForAllPages.addAll(getDatesAndTimesForSinglePage());
			viewCountsForAllPages.addAll(getViewCountsForSinglePage());
			questionLinkForAllPages.addAll(getQuestionLinksForSinglePage());
		}
	}

	public List<Integer> getCurrentYearQuestionsIndexes(String year) {
		calcaulateDataForAllPage();
		List<Integer> indexsForCurrentYear = new ArrayList<Integer>();
		String calculationYear = year.substring(Math.max(year.length() - 2, 0));
		for (int i = 0; i < datesAndTimesTextForAllPages.size(); i++) {
			// System.out.println(i + " " + datesAndTimesTextForAllPages.get(i)
			// + " count: "
			// + viewCountsForAllPages.get(i) + " quest: " +
			// questionLinkForAllPages.get(i));
			if (datesAndTimesTextForAllPages.get(i).contains("'" + calculationYear + " at ")) {
				indexsForCurrentYear.add(i);
			}
		}
		return indexsForCurrentYear;
	}

	private int getMaxViewsIndex(List<Integer> indexes) {
		int maxViewIndex = 0;
		long maxViewCount = getNoOfViews(indexes.get(maxViewIndex));

		for (int i = 1; i < indexes.size(); i++) {
			if (getNoOfViews(indexes.get(i)) > maxViewCount) {
				maxViewIndex = indexes.get(i);
				maxViewCount = getNoOfViews(indexes.get(i));
			}
		}
		return maxViewIndex;
	}

	public void moveToMaxViewedQuestion(String year) {
		int index = getMaxViewsIndex(getCurrentYearQuestionsIndexes(year));
		System.out.println("Most Asked question for " + year + "year : " + questionLinkForAllPages.get(index));
		System.out.println("Views : " + viewCountsForAllPages.get(index));
	}

	private long getNoOfViews(int index) {
		long totalViews = 0;
		String viewCountString = viewCountsForAllPages.get(index).split("\\s+")[0];
		if (viewCountString.contains("k")) {
			long viewCount = Long.parseLong(viewCountString.split("k")[0]);
			// 'k' means 1000
			totalViews = viewCount * 1000;
		} else {
			totalViews = Long.parseLong(viewCountString);
		}
		return totalViews;
	}

	public void navigateToStackOverflow() {
		getDriver().get("http://stackoverflow.com/");
	}

}
