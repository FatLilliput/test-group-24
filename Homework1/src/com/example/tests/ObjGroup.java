package com.example.tests;

public class ObjGroup implements Comparable<ObjGroup>{
	private String name;
	private String header;
	private String footer;

	public ObjGroup() {
		
	}
	
	public ObjGroup(String name, String header, String footer) {
		this.name = name;
		this.header = header;
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "ObjGroup [name=" + name + "]";
	}
	
//	@Override
//	public String toString() {
//		return "ObjGroup [name=" + name + ", header=" + header + ", footer="
//				+ footer + "]";
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ObjGroup other = (ObjGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(ObjGroup other) {
		return this.name.toLowerCase().compareTo(other.name.toLowerCase());
	}

	public ObjGroup withName(String name) {
		this.name  = name;
		return this;
	}

	public ObjGroup withHeader(String header) {
		this.header = header;
		return this;
	}

	public ObjGroup withFooter(String footer) {
		this.footer = footer;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getHeader() {
		return header;
	}

	public String getFooter() {
		return footer;
	}
	
}