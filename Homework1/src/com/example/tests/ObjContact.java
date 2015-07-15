package com.example.tests;

public class ObjContact implements Comparable<ObjContact> {
	private String firstName;
	private String lastName;
	private String address;
	private String home;
	private String mobile;
	private String work;
	private String email1;
	private String email2;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String group;
	private String address2;
	private String phone2;
	
	public ObjContact() {
		
	}
	public ObjContact defaultContact(ObjContact contact) {
		//contact = new ObjContact();
		contact.firstName = "NameDefault";
		contact.lastName = "SonameDefault";
		contact.address = "ContryD CityD AddressD";
		contact.home = "123 45 67";
		contact.mobile = "8 123 456 78 90";
		contact.work = "234657";
		contact.email1 = "default@ya.ru";
		contact.email2 = "default2@ya.ru";
		contact.birthDay = "1";
		contact.birthMonth = "January";
		contact.birthYear = "1900";
		contact.group = null;
		contact.address2 = "ContryD2 CityD2 AddressD2";
		contact.phone2 = "Default 123";
		return contact;
	}
	
	public ObjContact contact1(ObjContact contact) {
		//contact = new ObjContact();
		contact.firstName  = "Name 1";
		contact.lastName   = "Surname 1";
		contact.home       = "1234567";
		contact.email1     = "e@ya.ru";
		contact.group      = "TestGroup1";
		return contact;
	}
	
	public ObjContact contact2(ObjContact contact) {
		//contact = new ObjContact();
		contact.firstName  = "Name 2";
		contact.lastName   = "Surname 2";
		contact.home       = "7654321";
		contact.email1     = "e2@ya.ru";
		contact.group      = "TestGroup2";
		return contact;
	}

	public ObjContact setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public ObjContact setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public ObjContact setAddress(String address) {
		this.address = address;
		return this;
	}
	public ObjContact setHome(String home) {
		this.home = home;
		return this;
	}
	public ObjContact setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}
	public ObjContact setWork(String work) {
		this.work = work;
		return this;
	}
	public ObjContact setEmail1(String email1) {
		this.email1 = email1;
		return this;
	}
	public ObjContact setEmail2(String email2) {
		this.email2 = email2;
		return this;
	}
	public ObjContact setBirthDay(String birthDay) {
		this.birthDay = birthDay;
		return this;
	}
	public ObjContact setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
		return this;
	}
	public ObjContact setBirthYear(String birthYear) {
		this.birthYear = birthYear;
		return this;
	}
	public ObjContact setGroup(String group) {
		this.group = group;
		return this;
	}
	public ObjContact setAddress2(String address2) {
		this.address2 = address2;
		return this;
	}
	public ObjContact setPhone2(String phone2) {
		this.phone2 = phone2;
		return this;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getHome() {
		return home;
	}
	public String getMobile() {
		return mobile;
	}
	public String getWork() {
		return work;
	}
	public String getEmail1() {
		return email1;
	}
	public String getEmail2() {
		return email2;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public String getBirthMonth() {
		return birthMonth;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public String getGroup() {
		return group;
	}
	public String getAddress2() {
		return address2;
	}
	public String getPhone2() {
		return phone2;
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
	
	@Override
	public int hashCode() {
		int result = 1;
		return result;
	}
	
	//TODO Make shure that comparing is correct
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjContact other = (ObjContact) obj;
		if (email1 == null) {
			if (other.email1 != null)
				return false;
		} else if (!email1.equals(other.email1))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (home == null) {
			if (other.home != null)
				return false;
		} else if (!home.equals(other.home))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ObjContact [firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", home=" + home + ", mobile="
				+ mobile + ", work=" + work + ", email1=" + email1
				+ ", email2=" + email2 + ", birthDay=" + birthDay
				+ ", birthMonth=" + birthMonth + ", birthYear=" + birthYear
				+ ", group=" + group + ", address2=" + address2 + ", phone2="
				+ phone2 + "]";
	}
	
	@Override
	public int compareTo(ObjContact other) {
		if (this.lastName.toLowerCase().equals(other.lastName.toLowerCase())) {
			if (this.firstName.toLowerCase().equals(other.firstName.toLowerCase())) {
				if (this.email1.toLowerCase().equals(other.email1.toLowerCase())) {
					return this.home.replace(" ", "").toLowerCase().compareTo(other.home.replace(" ", "").toLowerCase());
				} else {
					return this.email1.toLowerCase().compareTo(other.email1.toLowerCase());
				}
			} else {
				return this.firstName.toLowerCase().compareTo(other.firstName.toLowerCase());
			}
		} else {
			return this.lastName.toLowerCase().compareTo(other.lastName.toLowerCase());
		}
	}
}
