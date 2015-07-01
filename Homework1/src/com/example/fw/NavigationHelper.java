package com.example.fw;

//import org.openqa.selenium.By;

public class NavigationHelper extends BaseHelper {

	public NavigationHelper(ApplicationManager manager) {
		super(manager);
	}

	public void openMainPage() {
		openPage(manager.baseUrl + "index.php");
	}
	
	public void openGroupsPage() {
		openPage(manager.baseUrl + "group.php");
	}

	public void clickMainPage() {
	    clickLinkText("home");
	  }

	public void clickGroupsList() {
		clickLinkText("groups");
	  }

}