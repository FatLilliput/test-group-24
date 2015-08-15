package com.example.fw;

//import org.openqa.selenium.By;

public class NavigationHelper extends WebDriverBaseHelper {

	public NavigationHelper(ApplicationManager manager) {
		super(manager);
	}

	public void openMainPage() {
		openPage("index.php");
	}
	
	public void openGroupsPage() {
		openPage("group.php");
	}

	public void clickMainPage() {
	    clickLinkText("home");
	  }

	public void clickGroupsList() {
		clickLinkText("groups");
	  }

	public void clickBirthList() {
		clickLinkText("next birthdays");
		
	}

	public void clickPrintAll() {
		clickLinkText("print all");
		
	}

	public void openPrintPhones() {
		openPage("view.php?all&print&phones");
		
	}

}