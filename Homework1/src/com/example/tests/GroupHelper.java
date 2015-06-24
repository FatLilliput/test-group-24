/*
 * This class contains methods needed for groups testing. 
 * Right now there are only groups creation tests are implemented. 
 * All the other group test classes should extend GroupHelper
 */

package com.example.tests;

public class GroupHelper extends TestBase {
	/*
	 * Fill add new group form
	 */
	  protected void fillForm(ObjGroup group) {
		  fillElement("group_name", group.name);
	      fillElement("group_header", group.header);
	      fillElement("group_footer", group.footer);
	  }

	  protected void clickSubmitGroup() {
	      clickButton("submit");
	  }

	  protected void clickNewGroup() {
		  clickButton("new");
	  }

}
