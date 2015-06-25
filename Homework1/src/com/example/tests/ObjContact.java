package com.example.tests;

public class ObjContact {
	public String firstName;
	public String lastName;
	public String address;
	public String home;
	public String mobile;
	public String work;
	public String email1;
	public String email2;
	public String birthDay;
	public String birthMonth;
	public String birthYear;
	public String address2;
	public String phone2;
	
	public ObjContact() {
		
	}
	
	public ObjContact (
			String firstName,
			String lastName,
			String address,
			String home,
			String mobile,
			String work,
			String email1,
			String email2,
			String birthDay,
			String birthMonth,
			String birthYear,
			String address2,
			String phone2
	) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.home = home;
		this.mobile = mobile;
		this.work = work;
		this.email1 = email1;
		this.email2 = email2;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.address2 = address2;
		this.phone2 = phone2;
	}
}
