package testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobject.StackOveflowPO;

public class StackOveflow {
	public static void main(String[] args) {
		System.out.println("hey");
	}
	
	@Test
	public void mtest(){
		StackOveflowPO po = new StackOveflowPO();
		po.navigateToStackOverflow();
		po.search("[qa]");
		po.navigateToFrequentTab();
		po.show50ListItems();
		System.out.println(po.getDatesAndTimes());
	}
	
//
//	@BeforeMethod
//	public void beforeMethod() {
//	}


}
