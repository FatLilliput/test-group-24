package com.example.tests;

public class ObjGroup implements Comparable<ObjGroup>{
	public String name;
	public String header;
	public String footer;

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
	
	
}