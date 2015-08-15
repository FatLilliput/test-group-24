package com.example.fw;

import java.util.Collections;
import java.util.List;

import com.example.utilits.SortedListOf;

public class ApplicationModel {

	private SortedListOf<ObjGroup> groups;
	private SortedListOf<ObjContact> contacts;
	
	public SortedListOf<ObjGroup> getGroups() {
		return groups;
	}
	
	public SortedListOf<ObjContact> getContacts() {
		return contacts;
	}
	
	public ApplicationModel setGroups(List<ObjGroup> newGroups) {
		this.groups = new SortedListOf<ObjGroup>(newGroups);
		return this;
	}
	
	public ApplicationModel setContacts(SortedListOf<ObjContact> contacts) {
		this.contacts = new SortedListOf<ObjContact>(contacts);
		return this;
	}
	
	public ApplicationModel addGroup(ObjGroup group) {
		if (!group.toString().contains("'"))
			groups.add(group);
		return this;
	}
	
	public ApplicationModel addContact(ObjContact contact) {
		if (!contact.toString().contains("'"))
			contacts.add(contact);
		return this;
	}
	
	public ApplicationModel removeGroup (ObjGroup group) {
		groups.remove(group);
		return this;
	}
	
	public ApplicationModel removeContact (ObjContact contact) {
		contacts.remove(contact);
		return this;
	}

	public ApplicationModel updateGroup(int id, ObjGroup group) {
		if (!group.toString().contains("'")) {
			for (ObjGroup gr : groups) {
				if (gr.getId() == id) {
					if (group.getName() != null)
						gr.setName(group.getName());
					if (group.getHeader() != null)
						gr.setHeader(group.getHeader());
					if (group.getFooter() != null)
						gr.setFooter(group.getFooter());
					if (group.getId() != 0)
						gr.setId(group.getId());
				}
			}
		}
		Collections.sort(groups);
		return this;
	}
	
	public ApplicationModel updateContact(int id, ObjContact contact) {
		if (!contact.toString().contains("'")) {
			for (ObjContact cont : contacts) {
				if (cont.getId() == id) {
					if (contact.getFirstName() != null)
						cont.setFirstName(contact.getFirstName());
					if (contact.getLastName() != null)
						cont.setLastName(contact.getLastName());
					if (contact.getAddress() != null)
						cont.setAddress(contact.getAddress());
					if (contact.getHome() != null)
						cont.setHome(contact.getHome());
					if (contact.getMobile() != null)
						cont.setMobile(contact.getMobile());
					if (contact.getWork() != null)
						cont.setWork(contact.getWork());
					if (contact.getEmail1() != null)
						cont.setEmail1(contact.getEmail1());
					if (contact.getEmail2() != null)
						cont.setEmail2(contact.getEmail2());
					if (contact.getBirthDay() != null)
						cont.setBirthDay(contact.getBirthDay());
					if (contact.getBirthMonth() != null)
						cont.setBirthMonth(contact.getBirthMonth());
					if (contact.getBirthYear() != null)
						cont.setBirthYear(contact.getBirthYear());
					if (contact.getAddress2() != null)
						cont.setAddress2(contact.getAddress2());
					if (contact.getPhone2() != null)
						cont.setPhone2(contact.getPhone2());
					if (contact.getId() != 0)
						cont.setId(contact.getId());
				}
			}
		}
		Collections.sort(contacts);
		return this;
	}

	public SortedListOf<ObjGroup> getGroupsCopy() {
		SortedListOf<ObjGroup> groupsCopy = new SortedListOf<ObjGroup>();
		ObjGroup gr;
		for (ObjGroup group : groups) {
			gr = new ObjGroup()
				.setName  (group.getName())
				.setHeader(group.getHeader())
				.setFooter(group.getFooter())
				.setId        (group.getId());
			groupsCopy.add(gr);
		}
		return groupsCopy;
	}
	
	public SortedListOf<ObjContact> getContactsCopy() {
		SortedListOf<ObjContact> contactsCopy = new SortedListOf<ObjContact>();
		ObjContact cont;
		for (ObjContact contact : contacts) {
			cont = new ObjContact()
				.setFirstName (contact.getFirstName())
				.setLastName  (contact.getLastName())
				.setAddress   (contact.getAddress())
				.setHome      (contact.getHome())
				.setMobile    (contact.getMobile())
				.setWork      (contact.getWork())
				.setEmail1    (contact.getEmail1())
				.setEmail2    (contact.getEmail2())
				.setBirthDay  (contact.getBirthDay())
				.setBirthMonth(contact.getBirthMonth())
				.setBirthYear (contact.getBirthYear())
				.setAddress2  (contact.getAddress2())
				.setPhone2    (contact.getPhone2())
				.setId        (contact.getId());
			contactsCopy.add(cont);
		}
		return contactsCopy;
	}

}
