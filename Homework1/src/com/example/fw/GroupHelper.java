/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.fw;
//import org.openqa.selenium.By;
import com.example.tests.ObjGroup;

public class GroupHelper extends BaseHelper {
	
	public GroupHelper (ApplicationManager manager) {
		super(manager);
	}
	/*
	 * Fill add new group form
	 */
	  public void fillForm(ObjGroup group) {
		  fillElement("group_name", group.name);
		  fillElement("group_header", group.header);
		  fillElement("group_footer", group.footer);
	  }

	  public void clickSubmitGroup() {
		  clickButton("submit");
	  }

	  public void clickNewGroup() {
		  clickButton("new");
	  }
//	public void selectGroup(int ) {
//		matchCheckbox();
//		
//	}

}
