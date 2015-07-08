package com.example.tests;

public class ObjContact implements Comparable<ObjContact> {
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
	public String group;
	public String address2;
	public String phone2;
	
	public ObjContact() {
		
	}
	public ObjContact DefaultContact(ObjContact contact) {
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
//		final int prime = 31;
		int result = 1;
//		result = prime * result + ((email1 == null) ? 0 : email1.hashCode());
//		result = prime * result
//				+ ((firstName == null) ? 0 : firstName.hashCode());
//		result = prime * result + ((home == null) ? 0 : home.hashCode());
//		result = prime * result
//				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	
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
